package com.dofi.tb1.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.databinding.FragmentPostBinding
import com.dofi.tb1.extension.getStringPref
import com.dofi.tb1.view.adapter.PostAdapter
import com.dofi.tb1.view.dialog.CommentDialog
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()
    private val postAdapter by lazy { PostAdapter() }
    private val commentDialog by lazy { CommentDialog() }
    private val gson by inject<Gson>()
    private var userLogin: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLogin = context?.getStringPref("userLogin")?.let { gson.fromJson(it, User::class.java) }
        onViewListener()
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        rvPost.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = postAdapter
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            getPostByUserResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Loading -> {
                        pbPostLoading.visibility = View.VISIBLE
                    }

                    is NetworkResultState.Success -> {
                        pbPostLoading.visibility = View.GONE
                        it.data?.data?.let { listPost ->
                            postAdapter.setData(
                                listItem = listPost,
                                onClickListener = { post, _ ->
                                    viewModel.getCommentByPost(post.id.orEmpty(), listOf(50, 0))
                                    commentDialog.setCommentData(
                                        post.id.orEmpty(),
                                        userLogin?.id.orEmpty()
                                    )
                                    commentDialog.show(childFragmentManager, "CommentDialog")
                                }
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

}