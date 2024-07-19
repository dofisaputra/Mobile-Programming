package com.dofi.tb1.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.Owner
import com.dofi.tb1.data.model.comment.CommentCreate
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.databinding.ActivityChatBinding
import com.dofi.tb1.extension.changeImageUrl
import com.dofi.tb1.extension.getStringPref
import com.dofi.tb1.view.adapter.ChatAdapter
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val viewModel by viewModel<DummyApiViewModel>()
    private val chatAdapter by lazy { ChatAdapter() }
    private val gson by inject<Gson>()
    private var owner: Owner? = null
    private var userLogin: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userLogin = gson.fromJson(getStringPref("userLogin"), User::class.java)
        owner = gson.fromJson(intent.getStringExtra("owner"), Owner::class.java)

        viewModel.getCommentByPost("669a78d32841f96f74eea921", listOf(50, 0))
        setupView()
        onViewListener()
        observeViewModel()
    }

    private fun onViewListener() = with(binding) {
        btnSendChat.setOnClickListener {
            val commentText = tieComment.text.toString()
            viewModel.createComment(
                CommentCreate(
                    message = commentText,
                    owner = userLogin?.id,
                    post = "669a78d32841f96f74eea921"
                )
            )
        }
    }

    private fun setupView() = with(binding) {
        rvChat.also {
            it.layoutManager = LinearLayoutManager(this@ChatActivity)
            it.adapter = chatAdapter
        }

        ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        ivProfileImage.load(owner?.picture?.changeImageUrl())
        tvUsername.text = owner?.getFullNames()
    }

    private fun observeViewModel() = with(binding) {
        viewModel.apply {
            getCommentByPostResponse.observe(this@ChatActivity) { response ->
                when (response) {
                    is NetworkResultState.Loading -> {
                        println("Loading")
                    }

                    is NetworkResultState.Success -> {
                        response.data?.data?.let { comments ->
                            chatAdapter.setUserLoginId(userLogin?.id.orEmpty())
                            chatAdapter.setData(comments.reversed())
                        }
                    }

                    is NetworkResultState.Error -> {
                        println("Error")
                    }
                }
            }

            createCommentResponse.observe(this@ChatActivity) {
                when (it) {
                    is NetworkResultState.Success -> {
                        viewModel.getCommentByPost("669a78d32841f96f74eea921", listOf(10, 0))
                        tieComment.text?.clear()
                    }

                    else -> {}
                }
            }
        }
    }

}