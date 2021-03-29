package com.yuaihen.policeinfo.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.yuaihen.policeinfo.R
import com.yuaihen.policeinfo.databinding.TitleViewBinding
import com.yuaihen.policeinfo.ui.interf.OnTitleViewListener
import com.yuaihen.policeinfo.utils.gone
import com.yuaihen.policeinfo.utils.visible

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
        attrs?.let {
            val ty = context.obtainStyledAttributes(it, R.styleable.TitleView)
            titleName = ty.getString(R.styleable.TitleView_titleName) ?: "Text"
            backIconDrawable = ty.getDrawable(R.styleable.TitleView_titleBackIcon)
                ?: ContextCompat.getDrawable(context, R.drawable.ic_arrow_left_36dp)
            showBackIcon = ty.getBoolean(R.styleable.TitleView_titleShowBackIcon, false)
            ty.recycle()
        }

        binding.tvTitle.text = titleName
        if (showBackIcon) {
            binding.ivBack.visible()
            binding.ivBack.setImageDrawable(backIconDrawable)
        } else {
            binding.ivBack.gone()
        }

        binding.ivBack.setOnClickListener {
            onTitleViewListener?.onBackBtnClick()
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