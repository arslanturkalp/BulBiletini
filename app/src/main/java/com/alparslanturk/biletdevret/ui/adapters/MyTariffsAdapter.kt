package com.alparslanturk.biletdevret.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.data.entities.models.MyTariff
import com.alparslanturk.biletdevret.databinding.RowLayoutTariffItemBinding

class MyTariffsAdapter(private val onItemClick: (MyTariff) -> Unit) : RecyclerView.Adapter<MyTariffsAdapter.TicketsViewHolder>() {

    private val dataList: MutableList<MyTariff> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder = TicketsViewHolder(RowLayoutTariffItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<MyTariff>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(private val binding: RowLayoutTariffItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: MyTariff) {
            with(binding) {
                tvTariffName.text = item.tariffCategoryName
                tvPrice.apply {
                    text = String.format(context.getString(R.string.count_format), item.quantity.toString())
                }
            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}