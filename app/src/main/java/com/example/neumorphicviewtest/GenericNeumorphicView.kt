package com.example.neumorphicviewtest

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.core.graphics.drawable.toDrawable

class GenericNeumorphicView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attributeSet, defStyle) {

    private val shadowOffset = 28F
    private val highlightOffset = -28F

    private val shadowPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#B3C1D4")
    }

    //Default padding for the view (space for shadow)
    private val padding = 48F

    //Rect object required to draw a rounded rectangle on canvas
    private val roundRect by lazy {
        RectF(
            padding,
            padding, measuredWidth - padding, measuredHeight - padding
        )
    }


    private val neuShadowRadius = 16F


    private val neuShadowColor = Color.parseColor("#A6B5CC")
    private val neuHighlightColor = Color.parseColor("#BDC8DA")

    init {
        gravity = Gravity.CENTER
        background = Color.TRANSPARENT.toDrawable()

        /**
         * Android P and above natively supports shadows
         * So anything below that needs the shadow to be rendered
         * by software, hence the "LAYER_TYPE_SOFTWARE"
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, shadowPaint)
        }
    }


    /**
     * OnDraw method works like a Framelayout
     * The last draw operation stays on the top of the view
     * In our case, its the text
     */
    override fun onDraw(canvas: Canvas?) {
        /**
         * Enables the shadow layer on the paint object
         * takes in shadowRadius,offset across X-axis, offset across Y-axis and shadow Color
         *
         * We just modify the offsets and colors to draw Shadows and Highlights
         */
        shadowPaint.setShadowLayer(
            neuShadowRadius, shadowOffset, shadowOffset, neuShadowColor
        )

        /**
         * Draw a rounded Rectangle on the canvas
         * Takes in rectF object,corner Radius across X-axis, offset across Y-axis and Paint Object
         */
        canvas?.drawRoundRect(roundRect, padding, padding, shadowPaint)
        shadowPaint.setShadowLayer(
            neuShadowRadius, highlightOffset, highlightOffset, neuHighlightColor
        )
        canvas?.drawRoundRect(roundRect, padding, padding, shadowPaint)
        super.onDraw(canvas)
    }

    @SuppressLint("SetTextI18n")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /**
         * Set Desired width/height to your customView
         * There are other ways to handle this than to hardcode the values
         */
        setMeasuredDimension(1000, 400)
        text = "Neumorphism"
    }
}
