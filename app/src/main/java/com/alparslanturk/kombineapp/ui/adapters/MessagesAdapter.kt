package com.alparslanturk.kombineapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.data.entities.models.ContactList
import com.alparslanturk.kombineapp.databinding.RowLayoutMessageBinding

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
        fun bind(item: ContactList) {
            with(binding) {
                tvUserName.text = item.user.name
                tvTotalTicket.text = item.wasNotSeenMessageCount.toString()
                tvLastMessage.text = item.lastMessage

                llCard.setOnClickListener { onItemClick.invoke(item) }
            }
        }
    }
}