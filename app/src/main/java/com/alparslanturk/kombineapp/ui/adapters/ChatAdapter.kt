package com.alparslanturk.kombineapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.data.entities.enums.ChatMessageType
import com.alparslanturk.kombineapp.data.entities.enums.ChatMessageType.*
import com.alparslanturk.kombineapp.data.entities.models.Message
import com.alparslanturk.kombineapp.databinding.RowLayoutReceivedMessageBinding
import com.alparslanturk.kombineapp.databinding.RowLayoutSentMessageBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList: MutableList<Message> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ChatMessageType.fromInt(viewType)) {
            RECEIVED -> ReceivedMessageViewHolder(RowLayoutReceivedMessageBinding.inflate(inflater, parent, false))
            SENT -> SentMessageViewHolder(RowLayoutSentMessageBinding.inflate(inflater, parent, false))
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is ChatAdapter.ReceivedMessageViewHolder -> holder.bind(item)
            is ChatAdapter.SentMessageViewHolder -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int = dataList[position].viewType.value

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<Message>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ReceivedMessageViewHolder(val binding: RowLayoutReceivedMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.tvMessage.text = message.message
        }
    }

    inner class SentMessageViewHolder(val binding: RowLayoutSentMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.tvMessage.text = message.message

        }
    }
}