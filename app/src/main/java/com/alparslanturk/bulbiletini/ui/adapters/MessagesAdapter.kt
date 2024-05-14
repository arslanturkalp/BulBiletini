package com.alparslanturk.bulbiletini.ui.adapters

import android.annotation.SuppressLint
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.data.entities.models.ContactList
import com.alparslanturk.bulbiletini.databinding.RowLayoutMessageBinding
import com.alparslanturk.bulbiletini.utils.setGone
import com.alparslanturk.bulbiletini.utils.setVisible
import com.bumptech.glide.Glide

class MessagesAdapter (private val onItemClick: (ContactList) -> Unit) : RecyclerView.Adapter<MessagesAdapter.ContactsViewHolder>() {

    private val dataList: MutableList<ContactList> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder = ContactsViewHolder(RowLayoutMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) = holder.bind(dataList[position])

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<ContactList>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ContactsViewHolder(private val binding: RowLayoutMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ContactList) {
            with(binding) {
                ivUserPhoto.apply {
                    Glide.with(context)
                        .asBitmap()
                        .load(Base64.decode(item.user.profilePhoto.orEmpty(), Base64.DEFAULT))
                        .error(R.color.black)
                        .into(this)
                }
                tvUserName.text = "${item.user.name} ${item.user.surname}"
                tvTotalTicket.text = item.wasNotSeenMessageCount.toString()
                tvLastMessage.text = item.lastMessage

                if (item.wasNotSeenMessageCount == 0) flCount.setGone() else flCount.setVisible()

                llCard.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}