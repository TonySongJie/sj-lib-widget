package com.xuan.widget.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.text.TextUtils
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import java.util.*

@SuppressLint("all")
class H5WebViewClient : WebViewClient() {

    /**
     * 记录URL的栈
     */
    private val mUrls = Stack<String>()

    /**
     * 判断页面是否加载完成
     */
    private var mIsLoading = false
    private var mUrlBeforeRedirect: String? = ""

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (mIsLoading && mUrls.size > 0) {
            mUrlBeforeRedirect = mUrls.pop()
        }
        recordUrl(url ?: "")
        this.mIsLoading = true
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val scheme = request?.url?.scheme
        return false
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        if (this.mIsLoading) this.mIsLoading = false
    }

    fun popLastPageUrl(): String? {
        if (mUrls.size >= 2) {
            mUrls.pop() //当前url
            return mUrls.pop()
        }
        return null
    }

    private fun recordUrl(url: String) {
        //这里还可以根据自身业务来屏蔽一些链接被放入URL栈
        if (!TextUtils.isEmpty(url) && url != getLastPageUrl()) {
            mUrls.push(url)
        } else if (!TextUtils.isEmpty(mUrlBeforeRedirect)) {
            mUrls.push(mUrlBeforeRedirect)
            mUrlBeforeRedirect = null
        }
    }

    /**
     * 获取上一页链接
     */
    @Synchronized
    private fun getLastPageUrl(): String? {
        return if (mUrls.size > 0) mUrls.peek() else null
    }
}