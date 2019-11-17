package pers.zy.colorable_lib

import android.graphics.PointF
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.TextView
import kotlin.math.abs

/**
 * date: 2019-11-16   time: 22:17
 * author zy
 * Have a nice day :)
 **/

class ColorAbleSpanMethod(pressedColor: Int,var mode: Int = MODE_TEXT) : LinkMovementMethod() {

    companion object {
        const val MODE_TEXT = 1
        const val MODE_ROUND = 2
        fun getInstance(pressedColor: Int, mode: Int = MODE_TEXT) = ColorAbleSpanMethod(pressedColor, mode)
    }

    private lateinit var link: Array<ClickableSpan>
    private val mPreDownPoint = PointF()
    private val textColorSpan = ForegroundColorSpan(pressedColor)
    private val roundColorSpan = BackgroundColorSpan(pressedColor)

    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val action = event.action

        var x = event.x.toInt()
        var y = event.y.toInt()

        x -= widget.totalPaddingLeft
        y -= widget.totalPaddingTop

        x += widget.scrollX
        y += widget.scrollY

        val layout = widget.layout
        val line = layout.getLineForVertical(y)
        val off = layout.getOffsetForHorizontal(line, x.toFloat())

        link = buffer.getSpans(off, off, ClickableSpan::class.java)
        val modeSpan = getModeSpan()
        if (link.isEmpty()) {
            buffer.removeSpan(modeSpan)
            return super.onTouchEvent(widget, buffer, event)
        } else {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    mPreDownPoint.x = event.x
                    mPreDownPoint.y = event.y
                    buffer.setSpan(
                        modeSpan,
                        buffer.getSpanStart(link[0]),
                        buffer.getSpanEnd(link[0]),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                MotionEvent.ACTION_MOVE -> {
                    val scaledTouchSlop = ViewConfiguration.get(widget.context).scaledTouchSlop
                    if (abs(event.x - mPreDownPoint.x) > scaledTouchSlop
                        || abs(event.y - mPreDownPoint.y) > scaledTouchSlop) {
                        buffer.removeSpan(modeSpan)
                        return super.onTouchEvent(widget, buffer, event)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    buffer.removeSpan(modeSpan)
                    link[0].onClick(widget)
                }
                MotionEvent.ACTION_CANCEL -> {
                    buffer.removeSpan(modeSpan)
                    return super.onTouchEvent(widget, buffer, event)
                }
            }
            return true
        }
    }

    private fun getModeSpan(): CharacterStyle {
        return when (mode) {
            MODE_TEXT -> {
                textColorSpan
            }
            else -> {
                roundColorSpan
            }
        }
    }
}