package com.alparslanturk.biletdevret.ui.adapters

import android.annotation.SuppressLint
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.data.entities.enums.DateFormatType
import com.alparslanturk.biletdevret.data.entities.models.Comment
import com.alparslanturk.biletdevret.databinding.RowLayoutCommentBinding
import com.alparslanturk.biletdevret.utils.toDate
import com.alparslanturk.biletdevret.utils.toString
import com.bumptech.glide.Glide

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
                ivProfile.apply {
                    Glide.with(context)
                        .asBitmap()
                        .load(Base64.decode(item.ownerUser.profilePhoto.orEmpty(), Base64.DEFAULT))
                        .error(R.color.black)
                        .into(this)
                }
                tvComment.text = item.comment
                tvCommentOwner.text = "${item.ownerUser.name} ${item.ownerUser.surname}"
                tvCommentDate.text = item.createdDate.toDate(dateFormatType = DateFormatType.DATE_TIME)?.toString(dateFormatType = DateFormatType.DATE_TIME_WITH_DOT)
            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}