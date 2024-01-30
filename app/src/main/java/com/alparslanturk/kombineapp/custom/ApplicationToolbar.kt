package com.alparslanturk.kombineapp.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.alparslanturk.kombineapp.databinding.LayoutAppToolbarBinding

class ApplicationToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding by lazy { LayoutAppToolbarBinding.inflate(LayoutInflater.from(context), this, true) }

    init {
        LayoutAppToolbarBinding.bind(binding.root)
        requestLayout()
    }

    fun setTitle(title: String = "") {
        with(binding) {
            tvTitle.text = title
        }
    }

    fun setBackButton(onClick: () -> Unit) {
        binding.ivBack.apply {
            visibility = View.VISIBLE
            setOnClickListener { onClick.invoke() }
        }
    }
}