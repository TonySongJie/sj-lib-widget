package com.xuan.widget

import android.content.Context
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class ButtonSpan : ClickableSpan {

    private var mColorId: Int
    private var mContext: Context
    private var mOnClickListener: View.OnClickListener

    constructor(context: Context, onClickListener: View.OnClickListener) : this(
        context,
        onClickListener,
        android.R.color.white
    )

    constructor(context: Context, onClickListener: View.OnClickListener, colorId: Int) {
        this.mContext = context
        this.mColorId = colorId
        this.mOnClickListener = onClickListener
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = ContextCompat.getColor(mContext, mColorId)
        ds.textSize = dip2px(16f).toFloat()
        ds.isUnderlineText = false
    }

    override fun onClick(widget: View) {
        mOnClickListener.onClick(widget)
    }

    private fun dip2px(dipValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }
}
