package com.xuan.widget.web

import android.content.Intent

/**
 *
 * @author: created by Songjie
 * @dateTime: 2019/10/12 0012 15:57
 * @email: songjie_xuan@126.com
 * @desc: sj-component
 */
interface InputFileCallback {

    fun onForResult(intent: Intent, path: String)
}