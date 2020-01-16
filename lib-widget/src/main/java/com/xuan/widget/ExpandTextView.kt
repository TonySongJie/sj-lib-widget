package com.xuan.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.Layout
import android.text.SpannableString
import android.text.Spanned
import android.text.StaticLayout
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.widget.TextView
import java.lang.StringBuilder

class ExpandTextView : TextView {

    private var originText = ""// 原始内容文本
    private var initWidth = 0// TextView可展示宽度
    private var mMaxLines = 2// TextView最大行数，默认两行
    private var spanClose: SpannableString? = null// 收起的文案(颜色处理)
    private var spanExpand: SpannableString? = null// 展开的文案(颜色处理)
    private val textExpand = "全部"
    private val textClose = "收起"

    constructor(context: Context) : super(context) {
        initCloseEnd()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initCloseEnd()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initCloseEnd()
    }

    fun initMaxLines(maxLines: Int) {
        this.mMaxLines = maxLines
    }

    fun initWidth(width: Int) {
        this.initWidth = width
    }

    fun setCloseText(text: CharSequence) {
        if (spanClose == null) {
            initCloseEnd()
        }

        var appendShowAll = false
        originText = text.toString()

        var workingText = StringBuilder(originText).toString()
        val newMaxLines = mMaxLines
        if (newMaxLines != -1) {
            val layout = createWorkingLayout(workingText)
            if (layout.lineCount > newMaxLines) {
                workingText = originText.substring(0, layout.getLineEnd(newMaxLines - 1))
                val showText = "${originText.substring(
                    0,
                    layout.getLineEnd(newMaxLines - 1)
                ).trim()}...$spanClose"
                var layout2 = createWorkingLayout(showText)
                while (layout2.lineCount > newMaxLines) {
                    val lastSpace = workingText.length - 1
                    if (lastSpace == -1) break
                    workingText = workingText.substring(0, lastSpace)
                    layout2 = createWorkingLayout("${workingText}...$spanClose")
                }
                appendShowAll = true
                workingText = "${workingText}..."
            }
        }

        setText(workingText)
        if (appendShowAll) {
            append(spanClose)
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    @SuppressLint("SetTextI18n")
    fun setExpandText(text: String) {
        if (spanExpand == null) {
            initExpandEnd()
        }
        val layout1 = createWorkingLayout(text)
        val layout2 = createWorkingLayout(text + textClose)
        setText("$originText ")
//        if (layout2.lineCount > layout1.lineCount) {
//        } else {
//            setText(originText)
//        }
        append(spanExpand)
        movementMethod = LinkMovementMethod.getInstance()
    }

    private fun initCloseEnd() {
        val content = textExpand
        spanClose = SpannableString(content)
        val span = ButtonSpan(
            context,
            OnClickListener {
                maxLines = Integer.MAX_VALUE
                setExpandText(originText)
            },
            R.color.colorYellow
        )
        spanClose!!.setSpan(span, 0, content.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    private fun initExpandEnd() {
        val content = textClose
        spanExpand = SpannableString(content)
        val span = ButtonSpan(context, OnClickListener {
            maxLines = mMaxLines
            setCloseText(originText)
        }, R.color.colorYellow)

        spanExpand!!.setSpan(span, 0, content.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    private fun createWorkingLayout(workingText: String): Layout {
        return StaticLayout(
            workingText, paint, initWidth - paddingLeft - paddingRight,
            Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        )
    }
}