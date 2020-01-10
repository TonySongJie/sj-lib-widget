package com.xuan.widget.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.webkit.*
import java.io.File


/**
 *
 * @author: created by Songjie
 * @dateTime: 2019/9/20 0020 17:31
 * @email: songjie_xuan@126.com
 * @desc: sj-component
 */
@SuppressLint("all")
class HtmlWebView : WebView {

    private val mContext: Context

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(
        context,
        attrs,
        Resources.getSystem().getIdentifier("webViewStyle", "attr", "android")
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context!!
        initWebView()
    }

    private fun initWebView() {
        settings.run {
            databaseEnabled = true
            useWideViewPort = true
            javaScriptEnabled = true
            domStorageEnabled = true
            allowContentAccess = true
            builtInZoomControls = false
            loadWithOverviewMode = true
            defaultTextEncodingName = "urf-8"
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            javaScriptCanOpenWindowsAutomatically = true
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

            val cacheDirPath = mContext.filesDir.absolutePath + "/h5Cache"
            setAppCachePath(cacheDirPath)
            setAppCacheEnabled(true)
        }

        requestFocus()

        webViewClient = H5WebViewClient()
    }

    /**
     * TODO->清除webView缓存
     */
    fun clearWvCache() {

        // 清理缓存数据库
        try {
            mContext.deleteDatabase("webView.db")
            mContext.deleteDatabase("webViewCache.db")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 缓存文件
        val appCacheDir = File("${mContext.filesDir.absolutePath}/h5Cache")
        val webViewCacheDir = File("${mContext.filesDir.absolutePath}/webViewCache")

        if (webViewCacheDir.exists()) deleteFile(webViewCacheDir)
        if (appCacheDir.exists()) deleteFile(appCacheDir)
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    private fun deleteFile(file: File) {
        if (file.exists()) {
            if (file.isFile) {
                file.delete()
            } else if (file.isDirectory) {
                val files = file.listFiles()
                for (i in 0..files!!.size) {
                    deleteFile(files[i])
                }
            }
            file.delete()
        }
    }
}