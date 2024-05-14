package com.alparslanturk.bulbiletini.ui.home.createticket.selectmatch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.bulbiletini.application.Constants
import com.alparslanturk.bulbiletini.data.entities.enums.DateFormatType
import com.alparslanturk.bulbiletini.data.entities.models.TicketMatch
import com.alparslanturk.bulbiletini.databinding.RowLayoutUpcomingMatchBinding
import com.alparslanturk.bulbiletini.utils.setGone
import com.alparslanturk.bulbiletini.utils.setVisible
import com.alparslanturk.bulbiletini.utils.toDate
import com.alparslanturk.bulbiletini.utils.toString
import com.bumptech.glide.Glide

class SelectMatchAdapter(private val onItemClick: (TicketMatch) -> Unit) : RecyclerView.Adapter<SelectMatchAdapter.MatchViewHolder>() {

    private val dataList: MutableList<TicketMatch> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder = MatchViewHolder(RowLayoutUpcomingMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SelectMatchAdapter.MatchViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<TicketMatch>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class MatchViewHolder(private val binding: RowLayoutUpcomingMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: TicketMatch) {
            with(binding) {
                ivHome.apply {
                    Glide.with(context).load("${Constants.BASE_URL}${item.homeLogo}").into(this)
                }
                ivAway.apply {
                    Glide.with(context).load("${Constants.BASE_URL}${item.awayLogo}").into(this)
                }
                tvMatchDate.apply {
                    setVisible()
                    text = item.matchDate.toDate(dateFormatType = DateFormatType.DATE_TIME)?.toString(dateFormatType = DateFormatType.DATE_WITH_DOT)
                }
                tvMatchName.text = "${item.homeName} - ${item.awayName}"
                tvTime.apply {
                    setVisible()
                    text = item.time
                }
                flCount.setGone()
                itemView.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}