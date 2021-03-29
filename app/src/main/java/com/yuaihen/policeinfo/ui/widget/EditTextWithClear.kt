package com.yuaihen.policeinfo.ui.widget

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.yuaihen.policeinfo.R

/**
 * Created by Yuaihen.
 * on 2021/2/22
 * 带清空按钮的EditText
 */
class EditTextWithClear @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

    private var iconDrawable: Drawable? = null

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextWithClear, 0, 0)
            .apply {
                try {
                    val iconId = getResourceId(R.styleable.EditTextWithClear_clearIcon, 0)
                    if (iconId != 0) {
                        iconDrawable =
                            ContextCompat.getDrawable(context, iconId)
                    }
                } finally {
                    recycle()
                }
            }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        toggleClearIcon()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            iconDrawable?.let {
                //手指抬起时并且触摸范围在Drawable内
                if (e.action == MotionEvent.ACTION_UP
                    && e.x > width - it.intrinsicWidth
                    && e.x < width
                    && e.y > height / 2 - it.intrinsicHeight / 2
                    && e.y < height / 2 + it.intrinsicHeight / 2
                ) {
                    text?.clear()
                }
            }
        }
        performClick()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        //焦点变化时切换Drawable显示状态
        toggleClearIcon()
    }

    private fun toggleClearIcon() {
        val icon = if (isFocused && text?.isNotEmpty() == true) iconDrawable else null
        //设置Drawable在EditText的位置
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
    }
}