package com.dofi.tb1.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.data.model.NetworkResultState
import com.dofi.tb1.data.model.comment.Comment
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.databinding.ActivityInboxBinding
import com.dofi.tb1.extension.getStringPref
import com.dofi.tb1.view.adapter.InboxAdapter
import com.dofi.tb1.view.model.DummyApiViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class InboxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInboxBinding
    private val inboxAdapter by lazy { InboxAdapter() }
    private val gson by inject<Gson>()
    private var userLogin: User? = null
    private val viewModel by viewModel<DummyApiViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userLogin = getStringPref("userLogin").let { gson.fromJson(it, User::class.java) }
        viewModel.getCommentByPost("669a78d32841f96f74eea921", listOf(50, 0))
        setupView()
        onObserverListener()
    }

    private fun onObserverListener() {
        viewModel.apply {
            getCommentByPostResponse.observe(this@InboxActivity) {
                when (it) {
                    is NetworkResultState.Loading -> {
                        println("Loading")
                    }

                    is NetworkResultState.Success -> {
                        println("Success")
                        it.data?.data?.let { comments ->
                            val groupByOwner = comments.groupBy { comment -> comment.owner?.id }
                            println("groupByOwner: $groupByOwner")
                            // remove the comment from userLogin
                            groupByOwner.filter { f -> f.key != userLogin?.id }
                                .let { gbo ->
                                    val listInbox = ArrayList<Comment>()
                                    gbo.map { g ->
                                        listInbox.add(g.value.first())
                                    }

                                    inboxAdapter.setData(
                                        listItem = listInbox,
                                        onClickListener = { dataComment, _ ->
                                            val intent = Intent(this@InboxActivity, ChatActivity::class.java)
                                            intent.putExtra("owner", gson.toJson(dataComment.owner))
                                            startActivity(intent)
                                        }
                                    )
                                }
                        }
//                        inboxAdapter.setData(
//                            listItem = it.data?.data?.toList() ?: emptyList(),
//                            onClickListener = { dataComment, _ ->
//                                val intent = Intent(this@InboxActivity, ChatActivity::class.java)
//                                intent.putExtra("dataComment", gson.toJson(dataComment))
//                                startActivity(intent)
//                            }
//                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setupView() = with(binding) {
//        getStringPref("comments").also {
//            gson.fromJson(it, Array<Comment>::class.java).let { comments ->
//                inboxAdapter.setData(
//                    listItem = comments.toList(),
//                    onClickListener = { dataComment, _ ->
//                        val intent = Intent(this@InboxActivity, ChatActivity::class.java)
//                        intent.putExtra("dataComment", gson.toJson(dataComment))
//                        startActivity(intent)
//                    }
//                )
//            }
//        }

        rvInbox.also {
            it.layoutManager = LinearLayoutManager(this@InboxActivity)
            it.adapter = inboxAdapter
        }
    }

}