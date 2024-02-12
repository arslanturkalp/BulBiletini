package com.alparslanturk.kombineapp.custom.selectionbottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.SelectionDialogItem
import com.alparslanturk.kombineapp.databinding.DialogSelectionBinding
import com.alparslanturk.kombineapp.ui.base.BaseBottomSheetDialog

class SelectionBottomSheetDialog(val title: String? = null, val list: List<SelectionDialogItem>, val onItemSelected: (SelectionDialogItem) -> Unit) : BaseBottomSheetDialog() {

    private val binding by lazy { DialogSelectionBinding.inflate(layoutInflater) }

    private val adapter by lazy {
        SelectionBottomSheetAdapter { selectedItem ->
            onItemSelected.invoke(selectedItem)
            dismiss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvTitle.text = title ?: getString(R.string.select)

            rvDialog.apply {
                adapter = this@SelectionBottomSheetDialog.adapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            tvCancel.setOnClickListener { dismiss() }
        }

        adapter.updateAdapter(list)
    }
}