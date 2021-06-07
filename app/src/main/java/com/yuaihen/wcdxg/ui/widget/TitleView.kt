package com.yuaihen.wcdxg.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.TitleViewBinding
import com.yuaihen.wcdxg.ui.activity.WebViewActivity
import com.yuaihen.wcdxg.ui.interf.OnTitleViewBackClickListener
import com.yuaihen.wcdxg.utils.LogUtil
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
    private var titleName = ""
    private var showBackIcon = false
    private val TAG = "TitleView"
    private var titleColor: Int = 0
    private var titleViewColor: Int = 0

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TitleView, 0, 0).apply {
            try {
                titleName = getString(R.styleable.TitleView_titleName) ?: ""
                showBackIcon = getBoolean(R.styleable.TitleView_titleShowBackIcon, false)
                val iconId = getResourceId(
                    R.styleable.TitleView_titleBackIcon,
                    R.drawable.ic_arrow_left_36dp
                )
                titleColor = getColor(R.styleable.TitleView_titleColor, Color.BLACK)
                titleViewColor = getColor(R.styleable.TitleView_titleViewBgColor, Color.WHITE)

                if (showBackIcon) {
                    binding.ivBack.visible()
                    if (iconId != 0) {
                        binding.ivBack.setImageResource(iconId)
                    }
                } else {
                    binding.ivBack.gone()
                }
                binding.tvTitle.text = titleName
                binding.clRoot.setBackgroundColor(titleViewColor)
                binding.tvTitle.setTextColor(titleColor)

                binding.ivBack.setOnClickListener {
                    when (context) {
                        is WebViewActivity -> {
                            LogUtil.d(TAG, "TitleView: WebViewActivity")
                            listener?.onClickBack()
                        }
                        is BaseActivity -> {
                            (context as FragmentActivity).finish()
                        }
                        is Fragment -> {
                            LogUtil.d(TAG, "TitleView: Fragment")
                        }
                        else -> {
                            LogUtil.e(TAG, "TitleView: iv_back setOnClickListener")
                        }
                    }
                }
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun setTitleColor(colorResId: Int) {
        binding.tvTitle.setTextColor(colorResId)
    }

    fun hideTitle() {
        binding.tvTitle.gone()
    }

    fun setBackIcon(resId: Int) {
        binding.ivBack.setImageResource(resId)
    }

    fun hideBackIcon() {
        binding.ivBack.gone()
    }


    fun setBgColor(colorResId: Int) {
        binding.clRoot.setBackgroundColor(colorResId)
    }

    private var listener: OnTitleViewBackClickListener? = null
    fun setOnTitleViewBackClickListener(listener: OnTitleViewBackClickListener) {
        this.listener = listener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}