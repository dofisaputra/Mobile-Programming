package com.dofi.tb1.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dofi.tb1.data.model.Comment
import com.dofi.tb1.databinding.ActivityInboxBinding
import com.dofi.tb1.extension.getStringPref
import com.dofi.tb1.view.adapter.InboxAdapter
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class InboxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInboxBinding
    private val inboxAdapter by lazy { InboxAdapter() }
    private val gson by inject<Gson>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() = with(binding) {
        getStringPref("comments").also {
            gson.fromJson(it, Array<Comment>::class.java).let { comments ->
                inboxAdapter.setData(
                    listItem = comments.toList(),
                    onClickListener = { dataComment, _ ->
                        val intent = Intent(this@InboxActivity, ChatActivity::class.java)
                        intent.putExtra("dataComment", gson.toJson(dataComment))
                        startActivity(intent)
                    }
                )
            }
        }

        rvInbox.also {
            it.layoutManager = LinearLayoutManager(this@InboxActivity)
            it.adapter = inboxAdapter
        }
    }

}