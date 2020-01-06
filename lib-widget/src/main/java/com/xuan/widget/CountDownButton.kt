package com.xuan.widget

import android.content.Context
import android.os.CountDownTimer
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet

class CountDownButton : AppCompatButton {

    private var mText = ""
    private var mHint = "%d秒"
    private var mSecond = 60
    private var mTimer: CountDownTimer? = null
    private var mBaseText: String = ""

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        R.style.CountDownButtonStyle
    )

    constructor(context: Context, attrs: AttributeSet?, def: Int) : super(context, attrs, def)

    override fun setText(text: CharSequence?, type: BufferType?) {
        this.mText = text.toString()
        super.setText(text, type)
    }

    fun setHintText(hint: String) {
        // TODO->hint格式为内容+%d,如：“%d秒后重新获取”
        this.mHint = hint
    }

    fun setCountdownTime(second: Int) {
        this.mSecond = second
    }

    fun start() {
        mBaseText = text.toString()
        if (mTimer == null) {
            mTimer = getCountDownTimer(mSecond)
        }
        mTimer?.cancel()
        mTimer?.start()
    }

    fun cancel() {
        if (mTimer != null) {
            mTimer?.cancel()
        }
    }

    private fun setHintCount(i: Int) {
        val text = String.format(mHint, i)
        setText(text)
    }

    private fun getCountDownTimer(time: Int): CountDownTimer {
        return object : CountDownTimer(time * 1000L, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if (isEnabled) isEnabled = false
                setHintCount(millisUntilFinished.toInt() / 1000)
            }

            override fun onFinish() {
                isEnabled = true
                text = mBaseText
            }
        }
    }
}