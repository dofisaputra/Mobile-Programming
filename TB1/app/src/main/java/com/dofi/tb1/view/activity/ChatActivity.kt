package com.dofi.tb1.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.dofi.tb1.data.model.Owner
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.databinding.ActivityChatBinding
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
    private var owner : Owner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCommentByPost("669a438e2841f937d8ee805e", listOf(50, 0))
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

        owner = gson.fromJson(intent.getStringExtra("owner"), Owner::class.java)
        ivProfileImage.load(owner?.picture)
        tvUsername.text = owner?.getFullNames()
    }

    private fun observeViewModel() = with(binding) {
//        viewModel.apply {
//            loadingState.observe(this@ChatActivity) { state ->
//                state[FetchType.COMMENTS]?.let {
//                    pbChat.visibility = if (it) View.VISIBLE else View.GONE
//                }
//            }
//
//            comments.observe(this@ChatActivity) { response ->
//                response.data?.let { comment ->
//                    chatAdapter.setData(comment)
//                }
//            }
//        }
    }

}