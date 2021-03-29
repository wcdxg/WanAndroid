package com.yuaihen.policeinfo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.yuaihen.policeinfo.R
import com.yuaihen.policeinfo.databinding.LayoutMineFunctionBinding

/**
 * Created by Yuaihen.
 * on 2021/3/28
 * 我的界面 功能区域
 */
class MineFunctionLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding =
        LayoutMineFunctionBinding.inflate(LayoutInflater.from(context), this, true)
    private var functionName = "功能"

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.EditTextWithClear, 0, 0)
            .apply {
                try {
                    functionName = getString(R.styleable.MineFunctionLayout_functionName) ?: "功能"
                    val iconId = getResourceId(
                        R.styleable.MineFunctionLayout_functionIcon,
                        R.drawable.ic_mine_leave
                    )
                    if (iconId != 0) {
                        binding.ivFunctionIcon.setImageResource(iconId)
                    }

                    binding.tvFunctionName.text = functionName
                } finally {
                    recycle()
                }
            }
    }

    fun setFunctionName(functionName: String) {
        binding.tvFunctionName.text = functionName
    }

    fun setFunctionIcon(resId: Int) {
        binding.ivFunctionIcon.setImageResource(resId)
    }
}