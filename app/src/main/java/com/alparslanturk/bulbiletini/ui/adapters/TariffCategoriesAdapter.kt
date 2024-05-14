package com.alparslanturk.bulbiletini.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.bulbiletini.data.entities.models.Tariff
import com.alparslanturk.bulbiletini.data.entities.models.TariffCategoryList
import com.alparslanturk.bulbiletini.databinding.RowLayoutTariffBinding

class TariffCategoriesAdapter(private val onItemClick: (Tariff) -> Unit) : RecyclerView.Adapter<TariffCategoriesAdapter.TicketsViewHolder>() {

    private val dataList: MutableList<TariffCategoryList> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder = TicketsViewHolder(RowLayoutTariffBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<TariffCategoryList>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(private val binding: RowLayoutTariffBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: TariffCategoryList) {
            val tariffsAdapter by lazy { TariffsAdapter { onItemClick.invoke(it) } }

            with(binding) {
                tvCategoryTitle.text = item.name
                rvTariffs.apply {
                    adapter = tariffsAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }

            tariffsAdapter.updateAdapter(item.tariffList.sortedBy { it.price.toDouble() })
        }
    }
}