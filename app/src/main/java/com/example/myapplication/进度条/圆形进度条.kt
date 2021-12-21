package com.example.myapplication.进度条

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ProgressBar
import kotlin.math.cos
import kotlin.math.sin

class 圆形进度条 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ProgressBar(context, attrs, defStyleAttr) {
    private var paint: Paint
    private val rectF: RectF
    private val rect: Rect
    var radius: Int
    var innerRadius: Int
    var centerX = 0
    var centerY = 0
    var unreachedColor: Int
    var reachedColor: Int
    var textColor: Int
    var paintWidth: Int
    var textSize: Int

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val desiredWidth = radius * 2 + paddingLeft + paddingRight
        val desiredHeight = radius * 2 + top + bottom
        val width: Int
        val height: Int
        width = if (widthMode == MeasureSpec.AT_MOST) {
            desiredWidth
        } else {
            Math.max(widthSize, desiredWidth)
        }
        height = if (heightMode == MeasureSpec.AT_MOST) {
            desiredHeight
        } else {
            Math.max(heightSize, desiredHeight)
        }
        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    @Synchronized
    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas);
        val width = width
        val height = height
//        val contentWidth = width - paddingLeft - paddingRight
//        val contentHeight = height - paddingTop - paddingBottom

        // 设置圆心为画布正中心
//        radius = if (contentWidth >= contentHeight) contentHeight / 2 else contentWidth / 2
        centerX = width / 2
        centerY = height / 2
//                画内部圆
        paint.shader = LinearGradient(
            (centerX - 0.7 * innerRadius).toFloat(),
            (centerY + 0.7 * innerRadius).toFloat(),
            (centerX + 0.7 * innerRadius).toFloat(),
            (centerY - 0.7 * innerRadius).toFloat(),
            Color.RED,
            Color.BLUE,
            Shader.TileMode.MIRROR
        )
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), innerRadius.toFloat(), paint)
//         画边的背景
        paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = unreachedColor
        paint.strokeWidth = paintWidth.toFloat()
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)
        rectF[(centerX - radius).toFloat(), (centerY - radius).toFloat(), (centerX + radius).toFloat()] =
            (centerY + radius).toFloat()
//        画边的路径
        paint = Paint()
        val ratio = progress * 1.0f / max
        val angle = (ratio * 360).toInt()
        paint.shader = LinearGradient(
            (centerX).toFloat(),
            (centerY - radius).toFloat(),
            (centerX + sin((angle + 90).toDouble() * radius)).toFloat(),
            (centerY - cos((angle + 90).toDouble() * radius)).toFloat(),
            Color.RED,
            Color.BLUE,
            Shader.TileMode.MIRROR
        )
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        canvas.drawArc(rectF, -90f, angle.toFloat(), false, paint)
        val text = "$progress%"

//        画文字
        paint = Paint()
        paint.color = textColor
        paint.style = Paint.Style.FILL
        paint.textSize = textSize.toFloat()
        paint.getTextBounds(text, 0, text.length, rect)
        val startX: Int = centerX - rect.width() / 2
        val fontMetrics = paint.fontMetricsInt
        val startY = (height - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
        canvas.drawText(text, startX.toFloat(), startY.toFloat(), paint)
    }


    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected fun dp2px(dpVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal.toFloat(), resources.displayMetrics
        ).toInt()
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected fun sp2px(spVal: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal.toFloat(), resources.displayMetrics
        ).toInt()
    }

    companion object {
        const val DEFAULT_UNREACHED_COLOR = -0xffffff
        const val DEFAULT_REACHED_COLOR = -0x10000
        const val DEFAULT_TEXT_COLOR = -0xffff33

        // 设置默认圆大小，单位为 dp
        const val DEFAULT_RADIUS = 24

        // 设置默认内部圆大小，单位为 dp
        const val DEFAULT_Inner_RADIUS = 17

        // 设置默认画笔宽度，单位为 dp
        const val DEFAULT_STROKE_WIDTH = 2

        // 设置文字默认大小，单位为 sp
        const val DEFAULT_TEXT_SIZE = 12
    }

    init {
        paint = Paint()
        rectF = RectF()
        rect = Rect()
        radius = dp2px(DEFAULT_RADIUS)
        innerRadius = dp2px(DEFAULT_Inner_RADIUS)
        paintWidth = dp2px(DEFAULT_STROKE_WIDTH)
        textSize = sp2px(DEFAULT_TEXT_SIZE)
        unreachedColor = DEFAULT_UNREACHED_COLOR
        reachedColor = DEFAULT_REACHED_COLOR
        textColor = DEFAULT_TEXT_COLOR
    }
}
