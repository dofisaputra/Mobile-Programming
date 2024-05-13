package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.Story
import com.dofi.tb1.databinding.ItemStoryBinding

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private lateinit var binding: ItemStoryBinding
    private var listItem: ArrayList<Story> = arrayListOf()
    private var onClickListener: ((Story, Int) -> Unit)? = null

    class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: Story,
            onClickListener: ((Story, Int) -> Unit)? = null
        ) = binding.apply {
            ivProfileStory.load(data.imageProfile)
            tvUsernameStory.text = data.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.setData(listItem[position], onClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listItem: List<Story>, onClickListener: ((Story, Int) -> Unit)? = null) {
        this.listItem.clear()
        this.listItem.addAll(listItem)
        this.onClickListener = onClickListener
        notifyDataSetChanged()
    }

}