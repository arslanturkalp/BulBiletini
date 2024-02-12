package com.alparslanturk.kombineapp.ui.teamdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.application.Constants
import com.alparslanturk.kombineapp.data.entities.models.Match
import com.alparslanturk.kombineapp.databinding.RowLayoutUpcomingMatchBinding
import com.bumptech.glide.Glide

class UpcomingMatchesAdapter(private val onItemClick: (Match) -> Unit) : RecyclerView.Adapter<UpcomingMatchesAdapter.MatchViewHolder>() {

    private val dataList: MutableList<Match> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder = MatchViewHolder(RowLayoutUpcomingMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UpcomingMatchesAdapter.MatchViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Match>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class MatchViewHolder(private val binding: RowLayoutUpcomingMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Match) {
            with(binding) {
                ivHome.apply {
                    Glide.with(context).load("${Constants.BASE_URL}${item.homeTeamLogo}").into(this)
                }
                ivAway.apply {
                    Glide.with(context).load("${Constants.BASE_URL}${item.awayTeamLogo}").into(this)
                }
                tvMatchName.text = "${item.homeTeamName} - ${item.awayTeamName}"
                tvTotalTicket.text = item.ticketCount.toString()

                itemView.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}