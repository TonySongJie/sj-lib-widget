package com.xuan.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


/**
 * @author 宋能东
 * @date 2020/3/18 0018 16:10
 * @description TODO->侧边栏导航视图
 */
@SuppressLint("ResourceAsColor")
class LetterNavigationView : View {

    // 导航栏内容间隔
    private var mContentDiv = 5.0f

    // 导航栏文字大小
    private var mContentTextSize = 16.0f

    // 导航栏文字颜色
    private var mContentTextColor = android.R.color.white

    // 按下时导航栏背景颜色
    private var mBackgroundColor = android.R.color.transparent

    // 按下时导航栏背景圆角度数
    private var mBackgroundAngle = 0

    // 按下时导航栏文字颜色
    private var mDownContentTextColor = android.R.color.holo_red_light

    private var mCurrentLetter = ""
    private var mEventActionState = false
    private var mOnNavigationScrollerListener: OnNavigationScrollerListener? = null

    // 导航栏容集合
    private var mNavigationContent: Array<String>? = null

    private val mRectF = RectF()

    // 声明相关画笔
    private lateinit var mTextPaint: TextPaint
    private lateinit var mBackgroundPaint: Paint

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context!!, attrs!!)
        initDefaultData()
    }

    override fun onDraw(canvas: Canvas?) {

        val viewWidth = width

        // TODO->1.绘制背景
        mRectF.set(0f, 0f, viewWidth.toFloat(), height.toFloat())
        if (mEventActionState) {
            mTextPaint.color = mDownContentTextColor
            mBackgroundPaint.color = mBackgroundColor
            canvas?.drawRoundRect(
                mRectF,
                mBackgroundAngle.toFloat(),
                mBackgroundAngle.toFloat(),
                mBackgroundPaint
            )
        } else {
            mTextPaint.color = mContentTextColor
            mBackgroundPaint.color = Color.TRANSPARENT
            val background = background
            if (background is ColorDrawable) mBackgroundPaint.color = background.color
            canvas?.drawRoundRect(
                mRectF,
                mBackgroundAngle.toFloat(),
                mBackgroundAngle.toFloat(),
                mBackgroundPaint
            )
        }

        // TODO->2.绘制文本
        val textX = viewWidth / 2
        val contentLength = getContentLength()
        val heightShould = (height - mContentDiv * 2 - paddingTop - paddingBottom) / contentLength
        for (i in 0 until contentLength) {
            val startY = ((i + 1) * heightShould) + paddingTop
            canvas?.drawText(mNavigationContent!![i], textX.toFloat(), startY.toFloat(), mTextPaint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val eventY = event?.y

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mEventActionState = true
                invalidate()
                if (mOnNavigationScrollerListener != null) mOnNavigationScrollerListener?.onDown()
                scrollCount(eventY ?: 0f)
            }
            MotionEvent.ACTION_MOVE -> {
                scrollCount(eventY ?: 0f)
            }
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                mEventActionState = false
                invalidate()
                if (mOnNavigationScrollerListener != null) mOnNavigationScrollerListener?.onUp()
            }
        }

        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        // 获取控件尺寸
        var actualWidth = MeasureSpec.getSize(widthMeasureSpec)
        var actualHeight = MeasureSpec.getSize(heightMeasureSpec)

        val contentLength = getContentLength()

        // 计算一个文字的尺寸
        val rect = measureTextSize()

        // 内容的最小宽度，高度
        val contentWidth = rect.width() + mContentDiv * 2
        val contentHeight = rect.height() * contentLength + mContentDiv * (contentLength + 3)
        actualWidth = when (widthMode) {
            MeasureSpec.AT_MOST ->
                // 宽度包裹内容
                contentWidth.toInt() + paddingLeft + paddingRight
            MeasureSpec.EXACTLY ->
                // 宽度限制
                if (actualWidth < contentWidth) contentLength + paddingLeft + paddingRight else 0
            else -> 0
        }

        actualHeight = when (heightMode) {
            MeasureSpec.AT_MOST ->
                //高度包裹内容
                contentHeight.toInt() + paddingTop + paddingBottom
            MeasureSpec.EXACTLY ->
                //高度限制
                if (actualHeight < contentHeight) contentHeight.toInt() + paddingTop + paddingBottom
                else 0
            else -> 0
        }

        setMeasuredDimension(actualWidth, actualHeight)
    }

    fun addOnNavigationScrollerListener(listener: OnNavigationScrollerListener) {
        this.mOnNavigationScrollerListener = listener
    }

    fun setNavigationContent(content: String) {
        if (!TextUtils.isEmpty(content)) {
            mNavigationContent = null
            mNavigationContent = arrayOf()
            for (i in 0..content.length)
                mNavigationContent!![i] = content[i].toString()
        }
    }

    private fun initAttrs(
        context: Context,
        attrs: AttributeSet
    ) {
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterNavigationView)
        mContentTextColor = mTypedArray.getColor(
            R.styleable.LetterNavigationView_text_color,
            Color.parseColor("#333333")
        )
        mBackgroundColor = mTypedArray.getColor(
            R.styleable.LetterNavigationView_background_color_down,
            Color.parseColor("#d7d7d7")
        )
        mDownContentTextColor = mTypedArray.getColor(
            R.styleable.LetterNavigationView_text_color_down,
            Color.WHITE
        )
        mContentTextSize = mTypedArray.getDimension(
            R.styleable.LetterNavigationView_text_size,
            14f
        )
        mContentDiv = mTypedArray.getDimension(
            R.styleable.LetterNavigationView_letter_div_height,
            5.0f
        )
        mBackgroundAngle = mTypedArray.getInt(
            R.styleable.LetterNavigationView_background_angle,
            0
        )
        mTypedArray.recycle()
    }

    private fun initDefaultData() {
        mNavigationContent = arrayOf(
            "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )

        // TODO->初始化绘制文字画笔
        mTextPaint = TextPaint()
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = mContentTextSize
        mTextPaint.color = mContentTextColor
        mTextPaint.textAlign = Paint.Align.CENTER

        // TODO->初始化绘制背景画笔
        mBackgroundPaint = Paint()
        mBackgroundPaint.run {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
    }

    private fun getContentLength(): Int {
        return if (mNavigationContent != null) mNavigationContent?.size ?: 0 else 0
    }

    private fun scrollCount(mEventY: Float) {
        // TODO->滑动的时候利用滑动距离和每一个字符高度进行取整，获取到Index
        val mRect: Rect = measureTextSize()
        val index =
            ((mEventY - paddingTop - paddingBottom - mContentDiv * 2) / (mRect.height() + mContentDiv)).toInt()
        // TODO->防止越界
        if (index >= 0 && index < getContentLength()) {
            val newLetter = mNavigationContent!![index]
            // TODO->防止重复触发回调
            if (mCurrentLetter != newLetter) {
                mCurrentLetter = newLetter
                if (mOnNavigationScrollerListener != null) {
                    mOnNavigationScrollerListener!!.onScroll(mCurrentLetter, index)
                }
            }
        }
    }

    private fun measureTextSize(): Rect {
        val mRect = Rect()
        mTextPaint.getTextBounds("田", 0, 1, mRect)
        return mRect
    }

    interface OnNavigationScrollerListener {

        /**
         * @author 宋能东
         * @date 2020/3/18 0018 16:24
         * @description TODO->手指按下
         */
        fun onDown()

        /**
         * @param letter ->
         * @param position ->
         * @author 宋能东
         * @date 2020/3/18 0018 16:23
         * @description TODO->手指滑动
         */
        fun onScroll(letter: String?, position: Int)

        /**
         * @author 宋能东
         * @date 2020/3/18 0018 16:23
         * @description TODO->手指离开
         */
        fun onUp()
    }
}