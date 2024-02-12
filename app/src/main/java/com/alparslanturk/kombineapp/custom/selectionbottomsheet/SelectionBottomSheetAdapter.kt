package com.alparslanturk.kombineapp.custom.selectionbottomsheet

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.SelectionDialogItem
import com.alparslanturk.kombineapp.databinding.RowLayoutSelectionBinding

class SelectionBottomSheetAdapter(private val onItemClick: (SelectionDialogItem) -> Unit) : RecyclerView.Adapter<SelectionViewHolder>() {

    private val selectionList: MutableList<SelectionDialogItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder = SelectionViewHolder(RowLayoutSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) = holder.bind(selectionList[position], onItemClick)

    override fun getItemCount(): Int = selectionList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<SelectionDialogItem>) {
        selectionList.clear()
        selectionList.addAll(list)
        notifyDataSetChanged()
    }

}

class SelectionViewHolder(val binding: RowLayoutSelectionBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SelectionDialogItem, onClick: (SelectionDialogItem) -> Unit) {
        with(binding) {
            tvSelectionName.apply {
                text = if (item.resId != 0) this.context.getString(item.resId) else item.name
                item.textColor?.let { setTextColor(Color.parseColor(it)) } ?: setTextColor(this.context.getColorStateList(R.color.text_selector_default_green))
            }

            row.setOnClickListener { onClick.invoke(item) }
        }
    }
}