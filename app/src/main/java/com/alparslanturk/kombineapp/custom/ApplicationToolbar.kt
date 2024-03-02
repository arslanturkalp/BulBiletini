package com.alparslanturk.kombineapp.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.databinding.LayoutAppToolbarBinding
import com.alparslanturk.kombineapp.utils.setVisible

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
            setVisible()
            setOnClickListener { onClick.invoke() }
        }
    }

    fun setDownButton(onClick: () -> Unit) {
        binding.ivBack.apply {
            setVisible()
            setImageResource(R.drawable.ic_down)
            setOnClickListener { onClick.invoke() }
        }
    }

    fun setFavouriteButton(isFavourite: Boolean, onClick: () -> Unit) {
        binding.ivFavourite.apply {
            if (isFavourite) setImageResource(R.drawable.ic_favourite) else setImageResource(R.drawable.ic_not_favourite)
            setVisible()
            setOnClickListener {
                if (isFavourite) setImageResource(R.drawable.ic_not_favourite) else setImageResource(R.drawable.ic_favourite)
                onClick.invoke()
            }
        }
    }
}