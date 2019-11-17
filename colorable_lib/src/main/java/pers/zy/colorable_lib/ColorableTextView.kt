package pers.zy.colorable_lib

import android.content.Context
import android.text.Spannable
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.toSpannable

/**
 * date: 2019-11-16   time: 22:16
 * author zy
 * Have a nice day :)
 **/
class ColorableTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    var normalColor: Int = context.resources.getColor(R.color.def_colorable_normal_color)
    var pressedColor: Int = context.resources.getColor(R.color.def_colorable_pressed_color)
    private val spanMethod = ColorAbleSpanMethod.getInstance(pressedColor, ColorAbleSpanMethod.MODE_ROUND)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorAbleTextView)
        normalColor = context.resources.getColor(typedArray.getResourceId(R.styleable.ColorAbleTextView_normal_color, R.color.def_colorable_normal_color))
        pressedColor = context.resources.getColor(typedArray.getResourceId(R.styleable.ColorAbleTextView_pressed_color, R.color.def_colorable_pressed_color))
        typedArray.recycle()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        movementMethod = spanMethod
    }

    fun setColorAbleClickListener(vararg clickItems: Triple<Int, Int, ColorClickListener>) {
        val spannable = text.toSpannable()
        clickItems.forEach {
            spannable.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    it.third.onClick(widget)
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.linkColor = normalColor
                    ds.color = ds.linkColor
                }
            }, it.first, it.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    fun setColorAbleClickListener(start: Int, end: Int, listener: ColorClickListener) {
        val spannable = text.toSpannable().apply {
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    listener.onClick(widget)
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.linkColor = normalColor
                    ds.color = ds.linkColor
                }
            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        text = spannable
    }

    interface ColorClickListener {
        fun onClick(widget: View)
    }
}