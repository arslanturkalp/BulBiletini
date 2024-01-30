package com.alparslanturk.kombineapp.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.databinding.RowLayoutTicketBinding
import com.bumptech.glide.Glide

class TicketsAdapter(private val onItemClick: (Ticket) -> Unit) : RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder>() {

    private val dataList: MutableList<Ticket> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder = TicketsViewHolder(RowLayoutTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TicketsAdapter.TicketsViewHolder, position: Int) = holder.bind(dataList[position])

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
                    Glide.with(context).load(item.homeLogo).into(this)
                }
                ivAway.apply {
                    Glide.with(context).load(item.awayLogo).into(this)
                }
                tvMatchName.text = "${item.homeTeam} - ${item.awayTeam}"
                tvPrice.text = item.price
                tvComment.text = item.location

                itemView.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}