package com.xuan.widget

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.lib_widget_view_top_toolbar.view.*

/**
 *
 * @author: created by Songjie
 * @dateTime: 2019/8/28 0028 9:03
 * @email: songjie_xuan@126.com
 * @desc: sj-component
 */
class TopToolbar : Toolbar {

    private var mContext: Context? = null

    private var leftText: String? = ""
    private var leftTextColor: Int? = 0
    private var leftIcon: Int? = 0

    private var titleText: String? = ""
    private var titleColor: Int? = 0

    private var rightText: String? = ""
    private var rightTextColor: Int? = 0
    private var rightIcon: Int? = 0

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.mContext = context

        LayoutInflater.from(context).inflate(R.layout.lib_widget_view_top_toolbar, this, true)

        val a = context?.obtainStyledAttributes(attrs, R.styleable.TopToolbar)

        // TODO->左侧按钮初始化
        leftText = a!!.getString(R.styleable.TopToolbar_leftText)
        leftTextColor = a.getColor(R.styleable.TopToolbar_leftTextColor, Color.BLACK)
        leftIcon = a.getResourceId(R.styleable.TopToolbar_leftIcon, 0)
        if (leftIcon != 0) {
            setLeftBtnVisible(false)
            setLeftIbtnVisible(true)

            setLeftIcon(leftIcon!!)
        } else {
            if (leftText != null && leftText!!.isNotBlank()) {
                setLeftBtnVisible(true)
                setLeftIbtnVisible(false)

                setLeftText(leftText!!)
                setLeftTextColor(leftTextColor!!)
            } else {
                setLeftBtnVisible(false)
                setLeftIbtnVisible(false)
            }
        }

        // TODO->标题设置
        titleText = a.getString(R.styleable.TopToolbar_titleText)
        if (titleText != null && titleText!!.isNotBlank()) {
            setTitleText(titleText!!)
        } else {
            setTitleText("")
        }
        titleColor = a.getColor(R.styleable.TopToolbar_titleColor, Color.BLACK)
        setTitleColor(titleColor!!)

        // TODO->右侧按钮初始化
        rightText = a.getString(R.styleable.TopToolbar_rightText)
        rightTextColor = a.getColor(R.styleable.TopToolbar_rightTextColor, Color.BLACK)
        rightIcon = a.getResourceId(R.styleable.TopToolbar_rightIcon, 0)
        if (rightIcon != 0) {
            setRightBtnVisible(false)
            setRightIbtnVisible(true)

            setRightIcon(rightIcon!!)
        } else {
            if (rightText != null && rightText!!.isNotBlank()) {
                setRightBtnVisible(true)
                setRightIbtnVisible(false)

                setRightText(rightText!!)
                setRightColor(rightTextColor!!)
            } else {
                setRightBtnVisible(false)
                setRightIbtnVisible(false)
            }
        }

        a.recycle()
    }

    fun setLeftBtnVisible(visible: Boolean) {
        libWidgetBtnTopToolbarLeft.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setLeftText(@StringRes resId: Int) {
        this.leftText = mContext!!.getString(resId)
        libWidgetBtnTopToolbarLeft.text = leftText
    }

    fun setLeftText(text: String) {
        this.leftText = text
        libWidgetBtnTopToolbarLeft.text = leftText
    }

    fun setLeftTextColor(@ColorRes color: Int) {
        leftTextColor = if (color == 0) Color.BLACK else color
        libWidgetBtnTopToolbarLeft.setTextColor(leftTextColor!!)
    }

    fun setLeftIbtnVisible(visible: Boolean) {
        libWidgetIbtnTopToolbarLeft.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setLeftIcon(@DrawableRes icon: Int) {
        leftIcon = icon
        val leftIconDrawable = ContextCompat.getDrawable(mContext!!, icon)
        libWidgetIbtnTopToolbarLeft.setImageDrawable(leftIconDrawable!!)
    }

    fun addToolbarLeftOnClickListener(listener: OnClickListener) {
        if (libWidgetBtnTopToolbarLeft.visibility == View.VISIBLE) {
            libWidgetBtnTopToolbarLeft.setOnClickListener(listener)
        }

        if (libWidgetIbtnTopToolbarLeft.visibility == View.VISIBLE) {
            libWidgetIbtnTopToolbarLeft.setOnClickListener(listener)
        }
    }

    fun setTitleText(@StringRes resId: Int) {
        this.titleText = mContext!!.getString(resId)
        libWidgetTvTopToolbarTitle.text = titleText
    }

    fun setTitleText(text: CharSequence?) {
        this.titleText = text.toString()
        libWidgetTvTopToolbarTitle.text = titleText
    }

    fun setTitleColor(@ColorRes color: Int) {
        titleColor = if (color == 0) Color.BLACK else color
        libWidgetTvTopToolbarTitle.setTextColor(titleColor!!)
    }

    fun setRightBtnVisible(visible: Boolean) {
        libWidgetBtnTopToolbarRight.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setRightText(@StringRes resId: Int) {
        this.rightText = mContext!!.getString(resId)
        libWidgetBtnTopToolbarRight.text = rightText
    }

    fun setRightText(text: CharSequence?) {
        this.rightText = text.toString()
        libWidgetBtnTopToolbarRight.text = rightText
    }

    fun setRightColor(@ColorRes color: Int) {
        rightTextColor = if (color == 0) Color.BLACK else color
        libWidgetBtnTopToolbarRight.setTextColor(rightTextColor!!)
    }

    fun setRightIbtnVisible(visible: Boolean) {
        libWidgetIbtnTopToolbarRight.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setRightIcon(icon: Int) {
        rightIcon = icon
        val rightIconDrawable = ContextCompat.getDrawable(mContext!!, icon)
        libWidgetIbtnTopToolbarRight.setImageDrawable(rightIconDrawable!!)
    }

    fun addToolbarRightOnClickListener(listener: OnClickListener) {
        if (libWidgetBtnTopToolbarRight.visibility == View.VISIBLE) {
            libWidgetBtnTopToolbarRight.setOnClickListener(listener)
        }

        if (libWidgetIbtnTopToolbarRight.visibility == View.VISIBLE) {
            libWidgetIbtnTopToolbarRight.setOnClickListener(listener)
        }
    }
}