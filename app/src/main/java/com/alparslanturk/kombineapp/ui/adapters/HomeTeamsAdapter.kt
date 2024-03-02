package com.alparslanturk.kombineapp.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.application.Constants.BASE_URL
import com.alparslanturk.kombineapp.data.entities.models.Club
import com.alparslanturk.kombineapp.databinding.RowLayoutHomeTeamBinding
import com.alparslanturk.kombineapp.utils.setGone
import com.alparslanturk.kombineapp.utils.setVisible
import com.bumptech.glide.Glide

class HomeTeamsAdapter(private val onItemClick: (Club) -> Unit) : RecyclerView.Adapter<HomeTeamsAdapter.ClubsViewHolder>() {

    private val dataList: MutableList<Club> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubsViewHolder = ClubsViewHolder(RowLayoutHomeTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ClubsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Club>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ClubsViewHolder(private val binding: RowLayoutHomeTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Club) {
            with(binding) {
                val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Color.parseColor(item.firstColor), Color.parseColor(item.secondColor)))
                ivClubLogo.apply {
                    Glide.with(context).load("$BASE_URL${item.logo}").into(this)
                }
                ivBg.apply {
                    gradientDrawable.cornerRadius = 0f
                    background = gradientDrawable
                }
                tvCount.apply {
                    if (item.totalTicketCount == 0) setGone() else setVisible()
                    text = item.totalTicketCount.toString()
                }
                flCount.apply {
                    if (item.totalTicketCount == 0) setGone() else setVisible()
                }
            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}