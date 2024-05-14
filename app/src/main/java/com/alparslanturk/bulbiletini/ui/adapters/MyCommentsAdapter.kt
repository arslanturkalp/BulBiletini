package com.alparslanturk.bulbiletini.ui.adapters

import android.annotation.SuppressLint
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.data.entities.models.MyComment
import com.alparslanturk.bulbiletini.databinding.RowLayoutMyCommentBinding
import com.bumptech.glide.Glide

class MyCommentsAdapter(private val onItemClick: (MyComment) -> Unit) : RecyclerView.Adapter<MyCommentsAdapter.TicketsViewHolder>() {

    private val dataList: MutableList<MyComment> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder = TicketsViewHolder(RowLayoutMyCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<MyComment>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(private val binding: RowLayoutMyCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: MyComment) {

            val commentsAdapter by lazy { CommentsAdapter {} }
            with(binding) {
                ivProfile.apply {
                    Glide.with(context)
                        .asBitmap()
                        .load(Base64.decode(item.commentedUser.profilePhoto.orEmpty(), Base64.DEFAULT))
                        .error(R.color.black)
                        .into(this)
                }
                tvCommentedUser.apply {
                    text = String.format(context.getString(R.string.commented_user), item.commentedUser.username)
                }
                rvComments.apply {
                    adapter = commentsAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                commentsAdapter.updateAdapter(item.commentList)

            }
            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }
}