package com.github.xiaogegechen.common.base

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.github.xiaogegechen.common.Constants
import com.github.xiaogegechen.common.R
import com.github.xiaogegechen.design.TitleBar

class WebActivity : BaseActivity() {

    private var mWebView: WebView? = null
    private var mProgressBar: ProgressBar? = null
    private var mTitleBar: TitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 拿到url
        mWebView?.loadUrl(intent.getStringExtra(Constants.INTENT_PARAM_NAME))
    }

    override fun initData() {
        mProgressBar = findViewById(R.id.progressBar)
        mWebView = findViewById(R.id.webView)
        mTitleBar = findViewById(R.id.titleBar)

        mWebView?.isVerticalScrollBarEnabled = false
        mWebView?.isHorizontalScrollBarEnabled = false
        mWebView?.settings?.javaScriptEnabled = true
        mWebView?.webViewClient = MyWebViewClient()
        mWebView?.webChromeClient = MyWebChromeClient()
        mTitleBar?.setListener(object : TitleBar.OnArrowClickListener{
            override fun onLeftClick() {
                finish()
            }

            override fun onRightClick() {
            }

        })
        mTitleBar?.setText("加载中...")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 拦截返回按钮点击事件
        if(mWebView?.canGoBack()!! && keyCode == KeyEvent.KEYCODE_BACK){
            mWebView?.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getLayoutId(): Int {
        return R.layout.common_activity_web
    }

    override fun isSupportSwipeBack(): Boolean {
        return true
    }

    private inner class MyWebViewClient: WebViewClient(){
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            mProgressBar?.visibility = View.VISIBLE
            mProgressBar?.progress = 0
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            mProgressBar?.progress = 100
            mProgressBar?.visibility = View.GONE
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
