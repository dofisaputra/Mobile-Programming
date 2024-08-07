package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.post.Post
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.data.model.post.getPostDate
import com.dofi.tb1.databinding.ItemPostBinding
import com.dofi.tb1.extension.changeImageUrl

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private lateinit var binding: ItemPostBinding
    private var listItem: ArrayList<Post> = arrayListOf()
    private var onClickListener: ((Post, Int) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: Post,
            onClickListener: ((Post, Int) -> Unit)? = null
        ) = binding.apply {
            ivImageProfile.load(data.owner?.picture?.changeImageUrl())
            tvUsername.text = data.owner?.getFullNames()
            tvDate.text = data.getPostDate()
            tvPostContent.text = data.text
            ivImagePost.load(data.image?.changeImageUrl())
            tvLikeCount.text = data.likes.toString()
            tvCommentCount.text = ""

            ivComment.setOnClickListener {
                onClickListener?.invoke(data, adapterPosition)
            }

            ivLike.setOnClickListener {
                tvLikeCount.text = (tvLikeCount.text.toString().toInt() + 1).toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.setData(listItem[position], onClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listItem: List<Post>, onClickListener: ((Post, Int) -> Unit)? = null) {
        this.listItem.clear()
        this.listItem.addAll(listItem.filter { it.text != "||+for_inbox_purpose+||" })
        this.onClickListener = onClickListener
        notifyDataSetChanged()
    }

}