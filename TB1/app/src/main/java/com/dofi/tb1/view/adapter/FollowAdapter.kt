package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.User
import com.dofi.tb1.data.model.getFullNames
import com.dofi.tb1.databinding.ItemFollowBinding

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    private lateinit var binding: ItemFollowBinding
    private var listItem: ArrayList<User> = arrayListOf()
    private var onClickListener: ((User, Int) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    class FollowViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: User,
            onClickListener: ((User, Int) -> Unit)? = null,
        ) = binding.apply {
            ivImageProfile.load(data.picture)
            tvUsername.text = data.getFullNames()
            tvFollowers.text = (1000..10000).random().toString() + " followers"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        binding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.setData(listItem[position], onClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listItem: List<User>, onClickListener: ((User, Int) -> Unit)? = null) {
        this.listItem.clear()
        this.listItem.addAll(listItem)
        this.onClickListener = onClickListener
        notifyDataSetChanged()
    }

}