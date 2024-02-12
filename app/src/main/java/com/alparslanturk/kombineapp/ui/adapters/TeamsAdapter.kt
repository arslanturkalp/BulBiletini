package com.alparslanturk.kombineapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.application.Constants
import com.alparslanturk.kombineapp.data.entities.models.Club
import com.alparslanturk.kombineapp.databinding.RowLayoutTeamBinding
import com.bumptech.glide.Glide

class TeamsAdapter(private val onItemClick: (Club) -> Unit) : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    private val dataList: MutableList<Club> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder = TeamsViewHolder(RowLayoutTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Club>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TeamsViewHolder(private val binding: RowLayoutTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Club) {
            with(binding) {
                ivClubLogo.apply {
                    Glide.with(context).load("${Constants.BASE_URL}${item.logo}").into(this)
                }
                tvClubName.text = item.name
                tvTotalTicket.text = item.totalTicketCount.toString()

                itemView.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}