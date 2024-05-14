package com.alparslanturk.bulbiletini.ui.adapters

import android.annotation.SuppressLint
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserName
import com.alparslanturk.bulbiletini.data.entities.enums.DateFormatType
import com.alparslanturk.bulbiletini.data.entities.models.Comment
import com.alparslanturk.bulbiletini.databinding.RowLayoutCommentBinding
import com.alparslanturk.bulbiletini.utils.setGone
import com.alparslanturk.bulbiletini.utils.setVisible
import com.alparslanturk.bulbiletini.utils.toDate
import com.alparslanturk.bulbiletini.utils.toString
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
                if (item.ownerUser != null) {
                    ivProfile.apply {
                        Glide.with(context)
                            .asBitmap()
                            .load(Base64.decode(item.ownerUser.profilePhoto.orEmpty(), Base64.DEFAULT))
                            .error(R.color.black)
                            .into(this)
                        setVisible()
                    }
                    ivCommentDot.setGone()
                    tvCommentOwner.text = getUserName()
                    tvCommentDate.text = item.createdDate.toDate(dateFormatType = DateFormatType.DATE_TIME)?.toString(dateFormatType = DateFormatType.DATE_TIME_WITH_DOT)
                } else {
                    ivProfile.setGone()
                    ivCommentDot.setVisible()
                    tvCommentOwner.text = item.createdDate.toDate(dateFormatType = DateFormatType.DATE_TIME)?.toString(dateFormatType = DateFormatType.DATE_TIME_WITH_DOT)
                    tvCommentDate.setGone()
                }
                tvComment.text = item.comment
            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}