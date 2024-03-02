package com.alparslanturk.kombineapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.data.entities.models.Comment
import com.alparslanturk.kombineapp.databinding.RowLayoutCommentBinding

class CommentsAdapter(private val onItemClick: (Comment) -> Unit) : RecyclerView.Adapter<CommentsAdapter.TicketsViewHolder>() {

    private val dataList: MutableList<Comment> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder = TicketsViewHolder(RowLayoutCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Comment>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(private val binding: RowLayoutCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Comment) {
            with(binding) {
                tvComment.text = item.comment
                tvCommentOwner.text = ""
            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}