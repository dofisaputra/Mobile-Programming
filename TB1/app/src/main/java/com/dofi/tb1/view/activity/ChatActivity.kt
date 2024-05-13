package com.dofi.tb1.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.dofi.tb1.data.model.Comment
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.databinding.ActivityChatBinding
import com.dofi.tb1.view.adapter.ChatAdapter
import com.dofi.tb1.view.model.DummyApiViewModel
import com.dofi.tb1.view.model.FetchType
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val viewModel by viewModel<DummyApiViewModel>()
    private val chatAdapter by lazy { ChatAdapter() }
    private val gson by inject<Gson>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchComments()
        setupView()
        observeViewModel()
    }

    private fun setupView() = with(binding) {
        rvChat.also {
            it.layoutManager = LinearLayoutManager(this@ChatActivity)
            it.adapter = chatAdapter
        }

        ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        gson.fromJson(intent.getStringExtra("dataComment"), Comment::class.java).let { comment ->
            ivProfileImage.load(comment.owner?.picture)
            tvUsername.text = comment.owner?.getFullNames()
        }
    }

    private fun observeViewModel() = with(binding) {
        viewModel.apply {
            loadingState.observe(this@ChatActivity) { state ->
                state[FetchType.COMMENTS]?.let {
                    pbChat.visibility = if (it) View.VISIBLE else View.GONE
                }
            }

            comments.observe(this@ChatActivity) { response ->
                response.data?.let { comment ->
                    chatAdapter.setData(comment)
                }
            }
        }
    }

}