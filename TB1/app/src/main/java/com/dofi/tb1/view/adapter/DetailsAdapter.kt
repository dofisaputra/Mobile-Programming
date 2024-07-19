//package com.dofi.tb1.view.adapter
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.dofi.tb1.databinding.ItemDetailProfileBinding
//
//class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {
//
//    private lateinit var binding: ItemDetailProfileBinding
//    private var listItem: ArrayList<DetailProfile> = arrayListOf()
//    private var onClickListener: ((DetailProfile, Int) -> Unit)? = null
//
//    @SuppressLint("SetTextI18n")
//    class DetailsViewHolder(private val binding: ItemDetailProfileBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun setData(
//            data: DetailProfile,
//            onClickListener: ((DetailProfile, Int) -> Unit)? = null
//        ) = binding.apply {
//            tvTitle.text = data.title
//            tvDescription.text = data.description
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
//        binding = ItemDetailProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return DetailsViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return listItem.size
//    }
//
//    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
//        holder.setData(listItem[position], onClickListener)
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(listItem: List<DetailProfile>, onClickListener: ((DetailProfile, Int) -> Unit)? = null) {
//        this.listItem.clear()
//        this.listItem.addAll(listItem)
//        this.onClickListener = onClickListener
//        notifyDataSetChanged()
//    }
//
//}