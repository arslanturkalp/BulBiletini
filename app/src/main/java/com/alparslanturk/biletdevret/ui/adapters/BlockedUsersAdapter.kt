package com.alparslanturk.biletdevret.ui.adapters

import android.annotation.SuppressLint
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.data.entities.models.TicketUser
import com.alparslanturk.biletdevret.databinding.RowLayoutBlockedUserBinding
import com.bumptech.glide.Glide

class BlockedUsersAdapter(private val onItemClick: (TicketUser) -> Unit) : RecyclerView.Adapter<BlockedUsersAdapter.TicketsViewHolder>() {

    private val dataList: MutableList<TicketUser> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder = TicketsViewHolder(RowLayoutBlockedUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<TicketUser>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(private val binding: RowLayoutBlockedUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: TicketUser) {
            with(binding) {
                ivProfilePhoto.apply {
                    Glide.with(context)
                        .asBitmap()
                        .load(Base64.decode(item.profilePhoto.orEmpty(), Base64.DEFAULT))
                        .error(R.color.black)
                        .into(this)
                }
                tvUserName.text = item.username
            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}