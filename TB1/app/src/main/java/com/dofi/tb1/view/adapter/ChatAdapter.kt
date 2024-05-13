package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.Comment
import com.dofi.tb1.databinding.ItemChatBinding

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private lateinit var binding: ItemChatBinding
    private var listItem: ArrayList<Comment> = arrayListOf()
    private var onClickListener: ((Comment, Int) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    class ChatViewHolder(private val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: Comment,
            position: Int,
            onClickListener: ((Comment, Int) -> Unit)? = null
        ) = binding.apply {
            if (position % 2 == 0) {
                llLeft.visibility = android.view.View.VISIBLE
                llRight.visibility = android.view.View.GONE
                ivLeftProfileImage.load(data.owner?.picture)
                tvLeftContent.text = data.message
            } else {
                llLeft.visibility = android.view.View.GONE
                llRight.visibility = android.view.View.VISIBLE
                ivRightProfileImage.load(data.owner?.picture)
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
        holder.setData(listItem[position], position, onClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listItem: List<Comment>, onClickListener: ((Comment, Int) -> Unit)? = null) {
        this.listItem.clear()
        this.listItem.addAll(listItem)
        this.onClickListener = onClickListener
        notifyDataSetChanged()
    }

}