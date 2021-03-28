package com.yuaihen.policeinfo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.yuaihen.policeinfo.R
import com.yuaihen.policeinfo.databinding.TitleViewBinding

/**
 * Created by Yuaihen.
 * on 2021/3/28
 * TitleView 页面顶部标题栏
 */
class TitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var _binding: TitleViewBinding? = null
    private val binding = TitleViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var titleName = "Text"


    init {
        attrs?.let {
            val ty = context.obtainStyledAttributes(it, R.styleable.TitleView)
            titleName = ty.getString(R.styleable.TitleView_titleName) ?: "Text"
            ty.recycle()
        }

        binding.tvTitle.text = titleName
    }

    fun setTitleText(title: String) {
        binding.tvTitle.text = title
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}