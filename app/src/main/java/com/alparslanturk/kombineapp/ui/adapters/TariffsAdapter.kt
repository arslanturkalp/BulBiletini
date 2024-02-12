package com.alparslanturk.kombineapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.data.entities.models.Tariff
import com.alparslanturk.kombineapp.data.entities.models.TariffCategoryList
import com.alparslanturk.kombineapp.databinding.RowLayoutTariffBinding
import com.alparslanturk.kombineapp.databinding.RowLayoutTariffItemBinding

class TariffsAdapter(private val onItemClick: (Tariff) -> Unit) : RecyclerView.Adapter<TariffsAdapter.TicketsViewHolder>() {

    private val dataList: MutableList<Tariff> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder = TicketsViewHolder(RowLayoutTariffItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Tariff>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(private val binding: RowLayoutTariffItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Tariff) {
            with(binding) {
                tvTariffName.text = item.name
                tvPrice.text = item.price
            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}