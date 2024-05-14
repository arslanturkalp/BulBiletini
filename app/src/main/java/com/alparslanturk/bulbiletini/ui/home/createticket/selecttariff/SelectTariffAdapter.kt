package com.alparslanturk.bulbiletini.ui.home.createticket.selecttariff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.bulbiletini.data.entities.models.TariffCategoryList
import com.alparslanturk.bulbiletini.databinding.RowLayoutSelectTariffBinding
import com.alparslanturk.bulbiletini.utils.setGone

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