package com.dofi.tb1.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dofi.tb1.data.model.user.User
import com.dofi.tb1.data.model.user.getFullNames
import com.dofi.tb1.databinding.ItemGroupsBinding

class GroupsAdapter : RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder>() {

    private lateinit var binding: ItemGroupsBinding
    private var listItem: ArrayList<User> = arrayListOf()
    private var onClickListener: ((User, Int) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    class GroupsViewHolder(private val binding: ItemGroupsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(
            data: User,
            onClickListener: ((User, Int) -> Unit)? = null,
        ) = binding.apply {
            ivImageGroup.load(data.picture)
            tvGroupName.text = data.getFullNames()
            tvMembers.text = (1000..10000).random().toString() + " Members"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        binding = ItemGroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
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