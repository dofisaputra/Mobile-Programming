package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.databinding.ItemLikePersonBinding

class LikeAdapter : RecyclerView.Adapter<LikeAdapter.LikeViewHolder>() {

    private lateinit var binding: ItemLikePersonBinding
    private var listItem: ArrayList<User> = arrayListOf()
    private var onClickListener: ((User, Int) -> Unit)? = null

    class LikeViewHolder(private val binding: ItemLikePersonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: User,
            onClickListener: ((User, Int) -> Unit)? = null
        ) = binding.apply {
            ivProfileImage.load(data.picture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        binding = ItemLikePersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
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