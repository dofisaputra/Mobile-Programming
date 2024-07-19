package com.dofi.tb1.view.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.data.model.user.getFullNames
import com.dofi.tb1.databinding.FragmentProfileBinding
import com.dofi.tb1.extension.changeImageUrl
import com.dofi.tb1.extension.getStringPref
import com.dofi.tb1.extension.putStringPref
import com.dofi.tb1.view.activity.SignInActivity
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.io.File

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()
    private val postFragment by lazy { PostFragment() }
    private val detailsFragment by lazy { DetailsFragment() }
    private val gson by inject<Gson>()
    private var userLogin: User? = null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    private var uploadedImageUserUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLogin = context?.getStringPref("userLogin")?.let { gson.fromJson(it, User::class.java) }
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { imageUri ->
                        binding.apply {
                            ivProfileImage.load(imageUri)
                            ivProfileBackground.load(imageUri)
                        }
                        context?.contentResolver?.query(imageUri, null, null, null, null)
                            ?.use { cursor ->
                                if (cursor.moveToFirst()) {
                                    val picturePath =
                                        cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))

                                    val requestBody =
                                        File(picturePath).asRequestBody("image/*".toMediaTypeOrNull())

                                    val body = MultipartBody.Part.createFormData(
                                        "image",
                                        "image-${System.currentTimeMillis()}.jpg",
                                        requestBody
                                    )

                                    viewModel.uploadImageUser(body)
                                }
                            }
                    }
                }
            }

        userLogin?.let {
            binding.apply {
                ivProfileBackground.load(it.picture?.changeImageUrl())
                ivProfileImage.load(it.picture?.changeImageUrl())
                tvUsername.text = it.getFullNames()
                tvAccount.text = it.email
                tvJob.text = it.location?.street
                tvPost.text = (0..100).random().toString()
                tvPhotos.text = (0..100).random().toString()
                tvFollowers.text = (0..100).random().toString()
                tvFollowing.text = (0..100).random().toString()
            }
        }

        viewModel.getPostByUser(userLogin?.id.orEmpty(), 50, 0)
        onViewListener()
        onObserverListener()
    }

    private fun onObserverListener() {
        viewModel.apply {
            uploadImageUserResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Loading -> {
                        println("Loading")
                    }

                    is NetworkResultState.Success -> {
                        println("it: $it")
                        uploadedImageUserUrl = it.data?.data?.url.orEmpty()
                        viewModel.updateUser(
                            User(
                                id = userLogin?.id.orEmpty(),
                                picture = uploadedImageUserUrl
                            )
                        )
                    }

                    else -> {}
                }
            }

            updateUserResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Loading -> {
                        println("Loading")
                    }

                    is NetworkResultState.Success -> {
                        println("it: $it")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun onViewListener() = with(binding) {
        commitFragment(postFragment)
        tlPostDetails.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> commitFragment(postFragment)
                    1 -> commitFragment(detailsFragment)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        ivProfileImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher?.launch(intent)
        }

        btnLogout.setOnClickListener {
            context?.putStringPref("userLogin", "")
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            activity?.finishAffinity()
        }
    }

    private fun commitFragment(fragment: Fragment) = with(binding) {
        childFragmentManager.beginTransaction()
            .replace(flContent.id, fragment)
            .commit()
    }

}