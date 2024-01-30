package com.alparslanturk.kombineapp.ui.teams

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.data.entities.models.Team
import com.alparslanturk.kombineapp.databinding.RowLayoutTeamBinding
import com.bumptech.glide.Glide

class TeamsAdapter(private val onItemClick: (Team) -> Unit) : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    private val dataList: MutableList<Team> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder = TeamsViewHolder(RowLayoutTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TeamsAdapter.TeamsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Team>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TeamsViewHolder(private val binding: RowLayoutTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Team) {
            with(binding) {
                ivClubLogo.apply {
                    Glide.with(context).load(item.logo).into(this)
                }
                tvClubName.text = item.name
                tvTotalTicket.text = item.totalTicket

                itemView.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}