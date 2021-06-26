package com.yuaihen.wcdxg.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.databinding.ViewEmptyBinding

/**
 * Created by Yuaihen.
 * on 2021/6/26
 * 空布局
 */
class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var _binding: ViewEmptyBinding? = null
    private val binding get() = _binding!!

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_empty, this)
        _binding = ViewEmptyBinding.bind(view)
    }

    fun setEmptyText(emptyText: String) {
        binding.tvEmpty.text = emptyText
    }

    fun setEmptyIconResource(resId: Int) {
        binding.ivEmpty.setBackgroundResource(resId)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}