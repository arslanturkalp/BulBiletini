package com.alparslanturk.kombineapp.ui.home.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.data.entities.models.Team
import com.alparslanturk.kombineapp.databinding.RowLayoutHomeTeamBinding
import com.bumptech.glide.Glide

class HomeTeamsAdapter(private val onItemClick: (Team) -> Unit) : RecyclerView.Adapter<HomeTeamsAdapter.TeamsViewHolder>() {

    private val dataList: MutableList<Team> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder = TeamsViewHolder(RowLayoutHomeTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: HomeTeamsAdapter.TeamsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Team>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TeamsViewHolder(private val binding: RowLayoutHomeTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Team) {
            with(binding) {
                val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Color.parseColor(item.firstColor), Color.parseColor(item.secondColor)))
                ivClubLogo.apply {
                    Glide.with(context).load(item.logo).into(this)
                }
                ivBg.apply {
                    gradientDrawable.cornerRadius = 0f
                    background = gradientDrawable
                }
            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}