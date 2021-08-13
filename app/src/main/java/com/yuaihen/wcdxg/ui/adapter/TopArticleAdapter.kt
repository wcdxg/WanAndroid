package com.yuaihen.wcdxg.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yuaihen.wcdxg.databinding.ItemArticleBinding
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
import com.yuaihen.wcdxg.utils.ArticleUtils
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 首页置顶文章列表
 */
class TopArticleAdapter(private val isCollectPage: Boolean = false) :
    ListAdapter<ArticleModel, BaseBindingViewHolder<ItemArticleBinding>>(
        DifferCallback
    ) {

    object DifferCallback : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areItemsTheSame(
            oldItem: ArticleModel,
            newItem: ArticleModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ArticleModel,
            newItem: ArticleModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<ItemArticleBinding> {
        return parent.getViewHolder(ItemArticleBinding::inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<ItemArticleBinding>,
        position: Int
    ) {
        ArticleUtils.setArticleData(
            holder.mBinding,
            getItem(position),
            isCollectPage,
            listener,
            position
        )
    }

    private var listener: OnCollectClickListener? = null
    fun addOnCollectClickListener(listener: OnCollectClickListener) {
        this.listener = listener
    }
}