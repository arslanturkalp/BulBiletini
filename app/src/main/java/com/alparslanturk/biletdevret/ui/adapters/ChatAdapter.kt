package com.alparslanturk.biletdevret.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.biletdevret.data.entities.enums.ChatMessageType
import com.alparslanturk.biletdevret.data.entities.enums.ChatMessageType.RECEIVED
import com.alparslanturk.biletdevret.data.entities.enums.ChatMessageType.SENT
import com.alparslanturk.biletdevret.data.entities.enums.DateFormatType
import com.alparslanturk.biletdevret.data.entities.models.Message
import com.alparslanturk.biletdevret.databinding.RowLayoutReceivedMessageBinding
import com.alparslanturk.biletdevret.databinding.RowLayoutSentMessageBinding
import com.alparslanturk.biletdevret.utils.toDate
import com.alparslanturk.biletdevret.utils.toString

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
            binding.apply {
                tvMessage.text = message.message
                tvHour.text = message.createdDate.toDate(dateFormatType = DateFormatType.DATE_TIME)?.toString(dateFormatType = DateFormatType.TIME_WITH_DOT)
            }
        }
    }

    inner class SentMessageViewHolder(val binding: RowLayoutSentMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.apply {
                tvMessage.text = message.message
                tvHour.text = message.createdDate.toDate(dateFormatType = DateFormatType.DATE_TIME)?.toString(dateFormatType = DateFormatType.TIME_WITH_DOT)
            }
        }
    }
}