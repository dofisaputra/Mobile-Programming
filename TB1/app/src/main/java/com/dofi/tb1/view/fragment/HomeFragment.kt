package com.dofi.tb1.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.post.Post
import com.dofi.tb1.data.model.post.PostCreate
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.databinding.FragmentHomeBinding
import com.dofi.tb1.extension.changeImageUrl
import com.dofi.tb1.extension.getStringPref
import com.dofi.tb1.view.activity.InboxActivity
import com.dofi.tb1.view.adapter.PostAdapter
import com.dofi.tb1.view.dialog.CommentDialog
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.io.File


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()

    //    private val storyAdapter by lazy { StoryAdapter() }
    private val postAdapter by lazy { PostAdapter() }
    private val commentDialog by lazy { CommentDialog() }
    private val gson by inject<Gson>()
    private var userLogin: User? = null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null
    private var uploadedImagePostUrl: String? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            println("Permission granted")
        } else {
            println("Permission denied")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLogin = context?.getStringPref("userLogin")?.let { gson.fromJson(it, User::class.java) }
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.data?.let { imageUri ->
                        binding.apply {
                            ivUploadImage.load(imageUri)
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

                                    viewModel.uploadImage(body)
                                }
                            }
                    }
                }
            }

        binding.apply {
            ivImageProfile.load(userLogin?.picture?.changeImageUrl())
        }
        viewModel.getListOfUsers(listOf(50, 0))
        viewModel.getListOfPosts(listOf(50, 0))

        onViewListener()
        onObserverListener()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun onViewListener() = with(binding) {
//        rvStory.also {
//            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            it.adapter = storyAdapter
//        }

        rvPost.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = postAdapter
        }

        ivInbox.setOnClickListener {
            val intent = Intent(requireContext(), InboxActivity::class.java)
            startActivity(intent)
        }

        llAddPhoto.setOnClickListener {
            requestPermission()
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher?.launch(intent)
        }

        btnPost.setOnClickListener {
            val bodyPost = PostCreate(
                text = etCaption.text.toString(),
                image = uploadedImagePostUrl,
                owner = userLogin?.id.orEmpty(),
                likes = 0,
                tags = emptyList()
            )

            viewModel.createPost(bodyPost)
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
//            loadingState.observe(viewLifecycleOwner) { state ->
//                state[FetchType.USERS]?.let {
//
//                }
//                state[FetchType.POSTS]?.let {
//                    pbPostLoading.visibility = if (it) View.VISIBLE else View.GONE
//                }
//            }

//            listOfUsers.observe(viewLifecycleOwner) {
//                when (it) {
//                    is NetworkResultState.Loading -> {
//                        pbStoryLoading.visibility = View.VISIBLE
//                    }
//
//                    is NetworkResultState.Success -> {
//                        pbPostLoading.visibility = View.GONE
//                        storyAdapter.setData(it.data?.data?.map { user ->
//                            Story(
//                                imageProfile = user.picture,
//                                username = "${user.firstName} ${user.lastName}"
//                            )
//                        })
//                    }
//                    else -> {}
//                }
//                response.data?.let { user ->
//                    storyAdapter.setData(user.map {
//                        Story(
//                            imageProfile = it.picture,
//                            username = "${it.firstName} ${it.lastName}"
//                        )
//                    })
//                }
//            }

            listOfPosts.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Loading -> {
                        pbPostLoading.visibility = View.VISIBLE
                    }

                    is NetworkResultState.Success -> {
                        pbPostLoading.visibility = View.GONE
                        postAdapter.setData(
                            listItem = it.data?.data ?: emptyList(),
                            onClickListener = { post, _ ->
                                viewModel.getCommentByPost(
                                    post.id.orEmpty(),
                                    listOf(10, 0)
                                )

                                commentDialog.setCommentData(
                                    post.id.orEmpty(),
                                    userLogin?.id.orEmpty()
                                )
                                commentDialog.show(
                                    childFragmentManager,
                                    "CommentDialog"
                                )
                            }
                        )
                    }

                    else -> {}
                }
            }

            uploadImageResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Loading -> {
                        println("Loading")
                    }

                    is NetworkResultState.Success -> {
                        println("it: $it")
                        uploadedImagePostUrl = it.data?.data?.url.orEmpty()
                    }

                    else -> {}
                }
            }

            createPostResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Loading -> {
                        println("Loading")
                    }

                    is NetworkResultState.Success -> {
                        viewModel.getListOfPosts(listOf(50, 0))
                        binding.apply {
                            etCaption.text.clear()
                            ivUploadImage.setImageDrawable(null)
                        }
                    }

                    else -> {}
                }
            }

//            comments.observe(viewLifecycleOwner) { response ->
//                response.data?.let {
//                    context?.putStringPref("comments", gson.toJson(it))
//                }
//            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                println("Permission granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }

}