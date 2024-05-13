package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.Comment
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.data.model.getPostDate
import com.dofi.tb1.databinding.ItemCommentBinding

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private lateinit var binding: ItemCommentBinding
    private var listItem: ArrayList<Comment> = arrayListOf()
    private var onClickListener: ((Comment, Int) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: Comment,
            onClickListener: ((Comment, Int) -> Unit)? = null
        ) = binding.apply {
            ivProfileImage.load(data.owner?.picture)
            tvCommentUsername.text = data.owner?.getFullNames()
            tvCommentDate.text = data.getPostDate()
            tvCommentContent.text = data.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.setData(listItem[position], onClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listItem: List<Comment>, onClickListener: ((Comment, Int) -> Unit)? = null) {
        this.listItem.clear()
        this.listItem.addAll(listItem)
        this.onClickListener = onClickListener
        notifyDataSetChanged()
    }

}