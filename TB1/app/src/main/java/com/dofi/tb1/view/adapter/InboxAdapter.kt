package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.Comment
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.data.model.getPostDate
import com.dofi.tb1.databinding.ItemInboxBinding

class InboxAdapter : RecyclerView.Adapter<InboxAdapter.InboxViewHolder>() {

    private lateinit var binding: ItemInboxBinding
    private var listItem: ArrayList<Comment> = arrayListOf()
    private var onClickListener: ((Comment, Int) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    class InboxViewHolder(private val binding: ItemInboxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: Comment,
            onClickListener: ((Comment, Int) -> Unit)? = null
        ) = binding.apply {
            ivProfileImage.load(data.owner?.picture)
            tvInboxUsername.text = data.owner?.getFullNames()
            tvInboxContent.text = data.message
            tvInboxDate.text = data.getPostDate()

            cvInboxItem.setOnClickListener {
                onClickListener?.invoke(data, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        binding = ItemInboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InboxViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int) {
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