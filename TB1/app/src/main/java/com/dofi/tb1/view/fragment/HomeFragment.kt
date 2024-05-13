package com.dofi.tb1.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.data.model.Story
import com.dofi.tb1.databinding.FragmentHomeBinding
import com.dofi.tb1.extension.putStringPref
import com.dofi.tb1.view.activity.InboxActivity
import com.dofi.tb1.view.adapter.PostAdapter
import com.dofi.tb1.view.adapter.StoryAdapter
import com.dofi.tb1.view.dialog.CommentDialog
import com.dofi.tb1.view.model.DummyApiViewModel
import com.dofi.tb1.view.model.FetchType
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()
    private val storyAdapter by lazy { StoryAdapter() }
    private val postAdapter by lazy { PostAdapter() }
    private val commentDialog by lazy { CommentDialog() }
    private val gson by inject<Gson>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUsers()
        viewModel.fetchPosts()
        viewModel.fetchComments()

        onViewListener()
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        rvStory.also {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            it.adapter = storyAdapter
        }

        rvPost.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = postAdapter
        }

        ivInbox.setOnClickListener {
            val intent = Intent(requireContext(), InboxActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            loadingState.observe(viewLifecycleOwner) { state ->
                state[FetchType.USERS]?.let {
                    pbStoryLoading.visibility = if (it) View.VISIBLE else View.GONE
                }
                state[FetchType.POSTS]?.let {
                    pbPostLoading.visibility = if (it) View.VISIBLE else View.GONE
                }
            }

            users.observe(viewLifecycleOwner) { response ->
                response.data?.let { user ->
                    storyAdapter.setData(user.map {
                        Story(
                            imageProfile = it.picture,
                            username = "${it.firstName} ${it.lastName}"
                        )
                    })
                }
            }

            posts.observe(viewLifecycleOwner) { response ->
                response.data?.let {
                    postAdapter.setData(
                        listItem = it,
                        onClickListener = { post, _ ->
                            viewModel.fetchCommentByPost(post.id.orEmpty())
                            commentDialog.show(childFragmentManager, "CommentDialog")
                        }
                    )
                }
            }

            comments.observe(viewLifecycleOwner) { response ->
                response.data?.let {
                    context?.putStringPref("comments", gson.toJson(it))
                }
            }
        }
    }

}