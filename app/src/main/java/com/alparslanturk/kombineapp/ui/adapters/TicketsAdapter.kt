package com.alparslanturk.kombineapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.Constants
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.databinding.RowLayoutTicketBinding
import com.bumptech.glide.Glide

class TicketsAdapter(private val onItemClick: (Ticket) -> Unit) : RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>() {

    private val dataList: MutableList<Ticket> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder = TicketsViewHolder(RowLayoutTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Ticket>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(private val binding: RowLayoutTicketBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Ticket) {
            with(binding) {
                ivHome.apply {
                    Glide.with(context).load("${Constants.BASE_URL}${item.homeTeamLogo}").into(this)
                }
                ivAway.apply {
                    Glide.with(context).load("${Constants.BASE_URL}${item.awayTeamLogo}").into(this)
                }
                tvMatchName.text = "${item.homeTeamName} - ${item.awayTeamName}"
                tvPrice.apply {
                    text = String.format(context.getString(R.string.tl_format), item.price.toString())
                }
                tvComment.text = item.location

                itemView.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}