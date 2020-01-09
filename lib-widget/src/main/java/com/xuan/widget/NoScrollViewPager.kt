package com.xuan.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @author songjie
 * @date 2019/6/26 0026 10:37
 *
 * @describe TODO->
 * @copyright 云南云车宝车联网科技有限公司
 */
@SuppressLint("ClickableViewAccessibility")
class NoScrollViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var isScroll = true

    override fun onTouchEvent(mv: MotionEvent): Boolean {
        return if (!isScroll) false else super.onTouchEvent(mv)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (!isScroll) false else super.onInterceptTouchEvent(ev)
    }

    fun setScroll(isScroll: Boolean) {
        this.isScroll = isScroll
    }
}