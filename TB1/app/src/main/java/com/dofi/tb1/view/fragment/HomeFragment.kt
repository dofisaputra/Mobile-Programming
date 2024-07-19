package com.dofi.tb1.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.databinding.FragmentHomeBinding
import com.dofi.tb1.extension.getStringPref
import com.dofi.tb1.extension.putStringPref
import com.dofi.tb1.view.activity.InboxActivity
import com.dofi.tb1.view.adapter.PostAdapter
import com.dofi.tb1.view.dialog.CommentDialog
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()
//    private val storyAdapter by lazy { StoryAdapter() }
    private val postAdapter by lazy { PostAdapter() }
    private val commentDialog by lazy { CommentDialog() }
    private val gson by inject<Gson>()
    private var userLogin: User? = null
    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLogin = context?.getStringPref("userLogin")?.let { gson.fromJson(it, User::class.java) }
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data
                // Handle the Intent
                println("data: $data")
            }
        }

        binding.apply {
            ivImageProfile.load(userLogin?.picture)
        }
        viewModel.getListOfUsers(listOf(50, 0))
        viewModel.getListOfPosts(listOf(50, 0))

        onViewListener()
        onObserverListener()
    }

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
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher?.launch(intent)
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
                                viewModel.getCommentByPost(post.id.orEmpty(), listOf(10, 0))
                                commentDialog.show(childFragmentManager, "CommentDialog")
                            }
                        )
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

}