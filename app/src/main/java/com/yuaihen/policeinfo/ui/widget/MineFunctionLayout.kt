package com.yuaihen.policeinfo.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
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
    private var functionName = "功能"
    private var functionIcon: Drawable? =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_mine_leave, null)

    private val binding =
        LayoutMineFunctionBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        attrs?.let {
            val ty = context.obtainStyledAttributes(it, R.styleable.MineFunctionLayout)
            functionName = ty.getString(R.styleable.MineFunctionLayout_functionName) ?: "功能"
            functionIcon =
                ty.getDrawable(R.styleable.MineFunctionLayout_functionIcon)
                    ?: ResourcesCompat.getDrawable(resources, R.drawable.ic_mine_leave, null)
            ty.recycle()

            binding.ivFunctionIcon.setImageDrawable(functionIcon)
            binding.tvFunctionName.text = functionName
        }
    }

    fun setFunctionName(functionName: String) {
        binding.tvFunctionName.text = functionName
    }

    fun setFunctionIcon(resId: Int) {
        binding.ivFunctionIcon.setImageResource(resId)
    }
}