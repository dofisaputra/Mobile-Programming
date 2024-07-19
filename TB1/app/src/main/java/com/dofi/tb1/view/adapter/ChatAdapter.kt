package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.comment.Comment
import com.dofi.tb1.databinding.ItemChatBinding
import com.dofi.tb1.extension.changeImageUrl

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private lateinit var binding: ItemChatBinding
    private var listItem: ArrayList<Comment> = arrayListOf()
    private var onClickListener: ((Comment, Int) -> Unit)? = null
    private var userLoginId: String? = null

    @SuppressLint("SetTextI18n")
    class ChatViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: Comment,
            position: Int,
            onClickListener: ((Comment, Int) -> Unit)? = null,
            userLoginId: String
        ) = binding.apply {
            if (data.owner?.id != userLoginId) {
                llLeft.visibility = android.view.View.VISIBLE
                llRight.visibility = android.view.View.GONE
                ivLeftProfileImage.load(data.owner?.picture?.changeImageUrl())
                tvLeftContent.text = data.message
            } else {
                llLeft.visibility = android.view.View.GONE
                llRight.visibility = android.view.View.VISIBLE
                ivRightProfileImage.load(data.owner.picture?.changeImageUrl())
                tvRightContent.text = data.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.setData(listItem[position], position, onClickListener, userLoginId.orEmpty())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listItem: List<Comment>, onClickListener: ((Comment, Int) -> Unit)? = null) {
        this.listItem.clear()
        this.listItem.addAll(listItem)
        this.onClickListener = onClickListener
        notifyDataSetChanged()
    }

    fun setUserLoginId(userLoginId: String) {
        this.userLoginId = userLoginId
    }

}