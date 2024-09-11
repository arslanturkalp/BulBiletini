package com.alparslanturk.bulbiletini.ui.generic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alparslanturk.bulbiletini.databinding.ActivityImageDetailBinding
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.utils.getDataExtra
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GenericImageDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityImageDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupPhoto()
    }

    private fun setupToolbar() {
        val stadiumName = intent.getDataExtra<String>(EXTRAS_DATA_STADIUM_NAME)

        binding.toolbar.apply {
            setTitle(stadiumName)
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupPhoto() {
        binding.apply {
            photoView.apply {
                Glide.with(this.context)
                    .load(intent.getDataExtra<String>(EXTRAS_DATA_PHOTO_URL))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(this)
            }
        }
    }

    companion object {

        private const val EXTRAS_DATA_PHOTO_URL = "EXTRAS_DATA_PHOTO_URL"
        private const val EXTRAS_DATA_STADIUM_NAME = "EXTRAS_DATA_STADIUM_NAME"

        fun createIntent(context: Context, photoUrl: String, stadiumName: String): Intent {
            return Intent(context, GenericImageDetailActivity::class.java).apply {
                putExtra(EXTRAS_DATA_PHOTO_URL, photoUrl)
                putExtra(EXTRAS_DATA_STADIUM_NAME, stadiumName)
            }
        }
    }
}