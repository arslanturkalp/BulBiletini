package com.alparslanturk.kombineapp.ui.home.createticket.selecttariff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.application.Constants
import com.alparslanturk.kombineapp.data.entities.enums.DateFormatType
import com.alparslanturk.kombineapp.data.entities.models.TariffCategoryList
import com.alparslanturk.kombineapp.data.entities.models.TicketMatch
import com.alparslanturk.kombineapp.databinding.RowLayoutSelectTariffBinding
import com.alparslanturk.kombineapp.databinding.RowLayoutUpcomingMatchBinding
import com.alparslanturk.kombineapp.utils.setGone
import com.alparslanturk.kombineapp.utils.setVisible
import com.alparslanturk.kombineapp.utils.toDate
import com.alparslanturk.kombineapp.utils.toString
import com.bumptech.glide.Glide

class SelectTariffAdapter(private val onItemClick: (TariffCategoryList) -> Unit) : RecyclerView.Adapter<SelectTariffAdapter.MatchViewHolder>() {

    private val dataList: MutableList<TariffCategoryList> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder = MatchViewHolder(RowLayoutSelectTariffBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SelectTariffAdapter.MatchViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<TariffCategoryList>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class MatchViewHolder(private val binding: RowLayoutSelectTariffBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: TariffCategoryList) {
            with(binding) {
                tvTariffName.text = item.name
                tvPrice.apply {
                    setGone()
                }
                itemView.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}