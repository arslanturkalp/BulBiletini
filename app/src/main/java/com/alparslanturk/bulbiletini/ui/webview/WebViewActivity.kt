package com.alparslanturk.bulbiletini.ui.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.databinding.ActivityWebViewBinding
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.utils.addOnBackPressedListener
import com.alparslanturk.bulbiletini.utils.setGone

class WebViewActivity : BaseActivity() {

    private val binding by lazy { ActivityWebViewBinding.inflate(layoutInflater) }

    private fun goBack() {
        when (binding.webView.canGoBack()) {
            true -> binding.webView.goBack()
            false -> finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addOnBackPressedListener { goBack() }

        setupToolbar()
        setupWebView()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(intent.getStringExtra(EXTRAS_DATA_TITLE) ?: "")
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        with(binding) {
            webView.apply {
                settings.apply {
                    javaScriptEnabled = true
                    defaultTextEncodingName = "utf-8"
                    cacheMode = WebSettings.LOAD_NO_CACHE
                }

                webViewClient = object : WebViewClient() {

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        progressBar.setGone()
                    }

                    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                        val url = request.url.toString()
                        return when {
                            url.startsWith("tel:") -> {
                                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                                true
                            }
                            url.contains("mailto:") -> {
                                view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                true
                            }
                            else -> {
                                view.loadUrl(url)
                                true
                            }

                        }
                    }
                }

                intent.getStringExtra(EXTRAS_DATA_URL).apply { if (this != null) loadUrl(this) }
            }
        }
    }

    companion object {

        private const val EXTRAS_DATA_TITLE = "EXTRAS_DATA_TITLE"
        private const val EXTRAS_DATA_URL = "EXTRAS_DATA_URL"

        fun createIntent(context: Context, title: String, url: String? = null): Intent {
            return Intent(context, WebViewActivity::class.java).apply {
                putExtra(EXTRAS_DATA_TITLE, title)
                putExtra(EXTRAS_DATA_URL, url)
            }
        }
    }
}