package com.yuaihen.wcdxg.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.TitleViewBinding
import com.yuaihen.wcdxg.ui.activity.WebViewActivity
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
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
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: TitleViewBinding? = null
    private val binding = TitleViewBinding.inflate(LayoutInflater.from(context), this, true)
    private var titleName = ""
    private var showBackIcon = false
    private var showCollectIcon = false
    private val TAG = "TitleView"
    private var titleColor: Int = 0
    private var titleViewColor: Int = 0
    private var isCollect = false
    private var articleId = 0
    private val bgColor = resources.getColor(R.color.bili_bili_pink)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.TitleView, 0, 0).apply {
            try {
                titleName = getString(R.styleable.TitleView_titleName) ?: ""
                showBackIcon = getBoolean(R.styleable.TitleView_titleShowBackIcon, true)
                showCollectIcon = getBoolean(R.styleable.TitleView_titleShowCollectIcon, false)
                titleColor = getColor(R.styleable.TitleView_titleColor, Color.WHITE)
                titleViewColor = getColor(R.styleable.TitleView_titleViewBgColor, bgColor)
                binding.ivBack.isVisible = showBackIcon
                binding.ivCollect.isVisible = showCollectIcon
                binding.apply {
                    tvTitle.text = titleName
                    clRoot.setBackgroundColor(titleViewColor)
                    tvTitle.setTextColor(titleColor)
                    ivBack.setOnClickListener {
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
                    ivCollect.setOnClickListener {
                        if (isCollect) {
                            //取消收藏
                            collectClickListener?.unCollect(articleId)
                        } else {
                            collectClickListener?.onCollect(articleId)
                        }
                        isCollect = !isCollect
                        ivCollect.isSelected = isCollect

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

    fun showCollectIcon() {
        binding.ivCollect.visible()
    }

    fun hideCollectIcon() {
        binding.ivCollect.gone()
    }

    fun setBgColor(colorResId: Int) {
        binding.clRoot.setBackgroundColor(colorResId)
    }

    fun setCollectState(isCollect: Boolean) {
        this.isCollect = isCollect
        binding.ivCollect.isSelected = isCollect
    }

    fun setArticleId(id: Int) {
        articleId = id
    }

    private var listener: OnTitleViewBackClickListener? = null
    fun setOnTitleViewBackClickListener(listener: OnTitleViewBackClickListener) {
        this.listener = listener
    }

    private var collectClickListener: OnCollectClickListener? = null
    fun addOnCollectClickListener(collectClickListener: OnCollectClickListener) {
        this.collectClickListener = collectClickListener
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}