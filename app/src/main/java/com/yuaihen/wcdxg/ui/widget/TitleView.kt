package com.yuaihen.wcdxg.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.databinding.TitleViewBinding
import com.yuaihen.wcdxg.ui.interf.OnTitleViewListener
import com.yuaihen.wcdxg.utils.gone
import com.yuaihen.wcdxg.utils.visible

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
    private var backIconDrawable: Drawable? = null
    private var showBackIcon = false
    private var onTitleViewListener: OnTitleViewListener? = null


    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TitleView, 0, 0).apply {
            try {
                titleName = getString(R.styleable.TitleView_titleName) ?: "Text"
                showBackIcon = getBoolean(R.styleable.TitleView_titleShowBackIcon, false)
                val iconId = getResourceId(
                    R.styleable.TitleView_titleBackIcon,
                    R.drawable.ic_arrow_left_36dp
                )

                if (showBackIcon) {
                    if (iconId != 0) {
                        binding.ivBack.visible()
                        binding.ivBack.setImageDrawable(backIconDrawable)
                    }
                } else {
                    binding.ivBack.gone()
                }
                binding.tvTitle.text = titleName
                binding.ivBack.setOnClickListener {
                    onTitleViewListener?.onBackBtnClick()
                }
            } finally {
                recycle()
            }
        }
    }

    fun setTitleText(title: String) {
        binding.tvTitle.text = title
    }

    fun setBackIconDrawable(resId: Int) {
        binding.ivBack.setImageResource(resId)
    }

    fun setTitleViewClickListener(onTitleViewListener: OnTitleViewListener) {
        this.onTitleViewListener = onTitleViewListener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}