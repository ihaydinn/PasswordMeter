package com.ihaydin.passwordmeter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.max

/**
 * Created by ismailhakkiaydin on 13,September,2021
 */

class PasswordMeterView : View {
    private var paint: Paint? = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mStrengthBarRadius = 0
    private var mStrengthBarsTintColor = 0
    private var mStrengthBarWidth = 0
    private var mStrengthBarSpacer = 0
    private var mStrengthCountSize = 0
    private var mStrengthTextHeight = 0
    private var mStrengthSelectedIndex = 0
    private var mTextPaddingStart = 0f
    private var mStrengthBarHeight = 0f;
    private var mTextFontStyle = ""
    private var mList = listOf<State>()

    private val density = resources.displayMetrics.density

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val TEXT_HEIGHT = mStrengthTextHeight * density
        val SPACER = mStrengthBarSpacer
        val WIDTH = mStrengthBarWidth * density

        val r = Rect()
        canvas.getClipBounds(r)

        for (i in 0 until mStrengthCountSize) {
            paint?.textSize = TEXT_HEIGHT
            paint?.setTypeface(Typeface.createFromAsset(context.assets, mTextFontStyle))
            if (i < mList[mStrengthSelectedIndex].tintSize) {
                paint?.color = mList[mStrengthSelectedIndex].color
            } else {
                paint?.color = resources.getColor(mStrengthBarsTintColor)
            }

            canvas.drawRoundRect(
                RectF(
                    (i * WIDTH + SPACER * i),
                    r.height() / 2f - mStrengthBarHeight,
                    ((i + 1) * WIDTH + i * SPACER),
                    r.height() / 2f + mStrengthBarHeight
                ), mStrengthBarRadius.toFloat(), mStrengthBarRadius.toFloat(), paint!!
            )


            if (i == mStrengthCountSize - 1) {
                paint?.color = mList[mStrengthSelectedIndex].color
                drawText(
                    canvas,
                    paint!!,
                    mList[mStrengthSelectedIndex].text,
                    maxWidth + mTextPaddingStart + ((i + 1) * WIDTH + (i + 1) * SPACER)
                )
            }
        }

        requestLayout()
        super.onDraw(canvas)
    }

    private fun drawText(canvas: Canvas, paint: Paint, text: String, posX: Float) {
        val r = Rect()
        canvas.getClipBounds(r)
        val cHeight: Int = r.height()
        val cWidth: Int = r.width()
        paint.textAlign = Paint.Align.RIGHT
        paint.getTextBounds(text, 0, text.length, r)
        val x: Float = posX
        val y: Float = cHeight / 2f + r.height() / 2f - r.bottom
        canvas.drawText(text, x, y, paint)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val bounds = Rect()
        paint?.getTextBounds(
            mList[mStrengthSelectedIndex].text,
            0,
            mList[mStrengthSelectedIndex].text.length,
            bounds
        )

        val desiredWidth =
            (mStrengthCountSize) * (mStrengthBarWidth * density + mStrengthBarSpacer) + getMaxTextWidth() + mTextPaddingStart
        val desiredHeight = getMaxTextHeight()

        val widthResult = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                if (widthSize < desiredWidth) widthSize or View.MEASURED_STATE_TOO_SMALL
                else widthSize
            }
            MeasureSpec.AT_MOST -> {
                if (widthSize < desiredWidth) widthSize or View.MEASURED_STATE_TOO_SMALL
                else desiredWidth
            }
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> throw RuntimeException("Unknown width mode $widthMode")
        }
        val heightResult = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                if (heightSize < desiredHeight) heightSize or View.MEASURED_STATE_TOO_SMALL
                else heightSize
            }
            MeasureSpec.AT_MOST -> {
                if (heightSize < desiredHeight) heightSize or View.MEASURED_STATE_TOO_SMALL
                else desiredHeight
            }
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> throw RuntimeException("Unknown width mode $widthMode")
        }
        setMeasuredDimension(widthResult.toInt(), heightResult)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val values =
            context.theme.obtainStyledAttributes(attrs, R.styleable.StrengthPasswordView, 0, 0)

        mStrengthCountSize = values.getInt(R.styleable.StrengthPasswordView_cv_strength_count_size,5)
        mStrengthBarRadius = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_radius, 20)
        mStrengthBarWidth = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_width, 10)
        mStrengthBarSpacer = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_spacer_size, 6)
        mStrengthTextHeight = values.getInt(R.styleable.StrengthPasswordView_cv_strength_text_height_value, 14)
        mTextPaddingStart = values.getFloat(R.styleable.StrengthPasswordView_cv_strength_text_start_padding, 8f)
        mStrengthBarHeight = values.getFloat(R.styleable.StrengthPasswordView_cv_strength_bar_height, 3f)

        values.recycle()
    }

    private fun getMaxTextHeight(): Int {
        var maxHeight = 0
        mList.forEachIndexed { index, state ->
            val bounds = Rect()
            paint?.getTextBounds(
                mList[index].text,
                0,
                mList[index].text.length,
                bounds
            )
            maxHeight = max(maxHeight, bounds.height())
        }
        return maxHeight
    }

    var maxWidth = 0
    private fun getMaxTextWidth(): Int {

        mList.forEachIndexed { index, state ->
            val bounds = Rect()
            paint?.getTextBounds(
                mList[index].text,
                0,
                mList[index].text.length,
                bounds
            )
            maxWidth = max(maxWidth, bounds.width())
        }
        return maxWidth
    }

    data class State(
        val text: String,
        @ColorInt val color: Int,
        val tintSize: Int
    )

    fun setStrengthSelectedIndex(state: Int) {
        mStrengthSelectedIndex = state
        requestLayout()
    }

    fun setTextFontStyle(font: String){
        mTextFontStyle = font
    }

    fun setStrengthBarHeight(value: Float){
        mStrengthBarHeight = value;
    }

    fun setStrengthBarRadius(radius: Int){
        mStrengthBarRadius = radius
    }

    fun setStrengthCountSize(size: Int) {
        mStrengthCountSize = size
    }

    fun setTextPaddingStart(value: Float) {
        mTextPaddingStart = value * density
    }

    fun setList(color: List<State>) {
        mList = color
    }

    fun setStrengthBarsTintColor(color: Int) {
        mStrengthBarsTintColor = color
    }

    fun setStrengthBarWidth(value: Int) {
        mStrengthBarWidth = value
    }

    fun setStrengthBarSpacer(value: Int) {
        mStrengthBarSpacer = value
    }

    fun setStrengthTextHeight(value: Int) {
        mStrengthTextHeight = value
    }

}