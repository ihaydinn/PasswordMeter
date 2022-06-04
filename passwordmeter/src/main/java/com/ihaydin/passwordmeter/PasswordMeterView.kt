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
    private var barRadius = 0
    private var barsTintColor = 0
    private var barWidth = 0
    private var barSpace = 0
    private var barCountSize = 0
    private var textHeight = 0
    private var selectedIndex = 0
    private var textPaddingStart = 0f
    private var barHeight = 0f;
    private var textFontStyle = ""
    private var list = listOf<State>()

    private val density = resources.displayMetrics.density

    constructor(context: Context?) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val TEXT_HEIGHT = textHeight * density
        val SPACER = barSpace
        val WIDTH = barWidth * density

        val r = Rect()
        canvas.getClipBounds(r)

        for (i in 0 until barCountSize) {
            paint?.textSize = TEXT_HEIGHT
            paint?.setTypeface(Typeface.createFromAsset(context.assets, textFontStyle))
            if (i < list[selectedIndex].tintSize) {
                paint?.color = list[selectedIndex].color
            } else {
                paint?.color = resources.getColor(barsTintColor)
            }

            canvas.drawRoundRect(
                RectF(
                    (i * WIDTH + SPACER * i),
                    r.height() / 2f - barHeight,
                    ((i + 1) * WIDTH + i * SPACER),
                    r.height() / 2f + barHeight
                ), barRadius.toFloat(), barRadius.toFloat(), paint!!
            )


            if (i == barCountSize - 1) {
                paint?.color = list[selectedIndex].color
                drawText(
                    canvas,
                    paint!!,
                    list[selectedIndex].text,
                    maxWidth + textPaddingStart + ((i + 1) * WIDTH + (i + 1) * SPACER)
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
            list[selectedIndex].text,
            0,
            list[selectedIndex].text.length,
            bounds
        )

        val desiredWidth =
            (barCountSize) * (barWidth * density + barSpace) + getMaxTextWidth() + textPaddingStart
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

        barCountSize = values.getInt(R.styleable.StrengthPasswordView_cv_strength_count_size,5)
        barRadius = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_radius, 20)
        barWidth = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_width, 10)
        barSpace = values.getInt(R.styleable.StrengthPasswordView_cv_strength_bar_spacer_size, 6)
        textHeight = values.getInt(R.styleable.StrengthPasswordView_cv_strength_text_height_value, 14)
        textPaddingStart = values.getFloat(R.styleable.StrengthPasswordView_cv_strength_text_start_padding, 8f)
        barHeight = values.getFloat(R.styleable.StrengthPasswordView_cv_strength_bar_height, 3f)

        values.recycle()
    }

    private fun getMaxTextHeight(): Int {
        var maxHeight = 0
        list.forEachIndexed { index, state ->
            val bounds = Rect()
            paint?.getTextBounds(
                list[index].text,
                0,
                list[index].text.length,
                bounds
            )
            maxHeight = max(maxHeight, bounds.height())
        }
        return maxHeight
    }

    var maxWidth = 0
    private fun getMaxTextWidth(): Int {

        list.forEachIndexed { index, state ->
            val bounds = Rect()
            paint?.getTextBounds(
                list[index].text,
                0,
                list[index].text.length,
                bounds
            )
            maxWidth = max(maxWidth, bounds.width())
        }
        return maxWidth
    }

    fun setSelectedIndex(state: Int) {
        selectedIndex = state
        requestLayout()
    }

    fun setTextFontStyle(font: String){
        textFontStyle = font
    }

    fun setBarHeight(value: Float){
        barHeight = value;
    }

    fun setBarRadius(radius: Int){
        barRadius = radius
    }

    fun setCountSize(size: Int) {
        barCountSize = size
    }

    fun setTextPaddingStart(value: Float) {
        textPaddingStart = value * density
    }

    fun setList(color: List<State>) {
        list = color
    }

    fun setBarsTintColor(color: Int) {
        barsTintColor = color
    }

    fun setBarWidth(value: Int) {
        barWidth = value
    }

    fun setBarSpace(value: Int) {
        barSpace = value
    }

    fun setTextHeight(value: Int) {
        textHeight = value
    }

}