package com.github.xiaogegechen.module_left.view

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.github.xiaogegechen.common.base.BaseActivity
import com.github.xiaogegechen.design.TitleBar
import com.github.xiaogegechen.module_left.R

class BlogActivity : BaseActivity() {

    private var mWebView: WebView? = null
    private var mProgressBar: ProgressBar? = null
    private var mTitleBar: TitleBar? = null

    override fun initData() {
        mProgressBar = findViewById(R.id.progressBar)
        mWebView = findViewById(R.id.webView)
        mTitleBar = findViewById(R.id.titleBar)

        mWebView?.isVerticalScrollBarEnabled = false
        mWebView?.isHorizontalScrollBarEnabled = false
        mWebView?.settings?.javaScriptEnabled = true
        mWebView?.webViewClient = MyWebViewClient()
        mWebView?.webChromeClient = MyWebChromeClient()
        mWebView?.loadUrl("https://me.csdn.net/qq_40909351")
        mTitleBar?.setListener(object : TitleBar.OnArrowClickListener{
            override fun onLeftClick() {
                finish()
            }

            override fun onRightClick() {
            }

        })
        mTitleBar?.setText("加载中...")
    }

    override fun getLayoutId(): Int {
        return R.layout.module_left_activity_blog
    }

    override fun isSupportSwipeBack(): Boolean {
        return true
    }

    private inner class MyWebViewClient: WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            mProgressBar?.progress = 0
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            mProgressBar?.progress = 100
        }
    }

    private inner class MyWebChromeClient: WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            mProgressBar?.progress = newProgress
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            mTitleBar?.setText(title)
        }
    }
}
