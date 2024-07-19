package com.dofi.tb1.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.comment.Comment
import com.dofi.tb1.data.model.comment.CommentCreate
import com.dofi.tb1.databinding.DialogCommentBinding
import com.dofi.tb1.view.adapter.CommentAdapter
import com.dofi.tb1.view.adapter.LikeAdapter
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CommentDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogCommentBinding
    private val viewModel by activityViewModel<DummyApiViewModel>()
    private val likeAdapter by lazy { LikeAdapter() }
    private val commentAdapter by lazy { CommentAdapter() }

    private var postId: String = ""
    private var ownerId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewListener()
        onObserverListener()
    }

    private fun onViewListener() = with(binding) {
        rvLike.also {
            it.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            it.adapter = likeAdapter
        }

        rvComment.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = commentAdapter
        }

        ibSendComment.setOnClickListener {
            val commentText = tieComment.text.toString()
            viewModel.createComment(
                CommentCreate(
                    message = commentText,
                    owner = ownerId,
                    post = postId
                )
            )
        }
    }

    private fun onObserverListener() = with(binding) {
        viewModel.apply {
            listOfUsers.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Success -> {
                        likeAdapter.setData(it.data?.data ?: emptyList())
                    }

                    else -> {}
                }
            }

            getCommentByPostResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Success -> {
                        println("it: $it")
                        val comment = it.data?.data ?: emptyList()
                        println("comment: $comment")
                        commentAdapter.setData(comment)

                        if (comment.isEmpty()) {
                            llNoComment.visibility = View.VISIBLE
                        } else {
                            llNoComment.visibility = View.GONE
                        }
                    }

                    else -> {}
                }
            }

            createCommentResponse.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResultState.Success -> {
                        viewModel.getCommentByPost(postId, listOf(10, 0))
                        tieComment.text?.clear()
                    }

                    else -> {}
                }
            }
        }
    }

    fun setCommentData(postId: String, ownerId: String) {
        this.postId = postId
        this.ownerId = ownerId
    }

}