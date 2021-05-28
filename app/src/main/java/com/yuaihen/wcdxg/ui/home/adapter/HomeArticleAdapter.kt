package com.yuaihen.wcdxg.ui.home.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yuaihen.wcdxg.databinding.ArticleRecycleItemBinding
import com.yuaihen.wcdxg.net.model.HomeArticleModel
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 首页文章列表
 */
class HomeArticleAdapter :
    PagingDataAdapter<HomeArticleModel.Data.Data, BaseBindingViewHolder<ArticleRecycleItemBinding>>(
        DifferCallback
    ) {

    object DifferCallback : DiffUtil.ItemCallback<HomeArticleModel.Data.Data>() {
        override fun areItemsTheSame(
            oldItem: HomeArticleModel.Data.Data,
            newItem: HomeArticleModel.Data.Data
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HomeArticleModel.Data.Data,
            newItem: HomeArticleModel.Data.Data
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<ArticleRecycleItemBinding> {
        return parent.getViewHolder(ArticleRecycleItemBinding::inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<ArticleRecycleItemBinding>,
        position: Int
    ) {
        val data = getItem(position)
        data?.let {
            with(holder.mBinding) {
                tvAuthor.text = if (it.author.isEmpty()) it.shareUser else it.author
                tvPublishTime.text = it.niceDate
                tvTitle.text = Html.fromHtml(it.title).toString()
                tvDesc.text = Html.fromHtml(it.desc).toString()
                tvChapterName.text = "${it.superChapterName}-${it.chapterName}"
                ivCollect.isSelected = it.collect
                recycleTags.adapter = ArticleTagAdapter(it.tags)

                tvDesc.isGone = it.desc.isEmpty()
            }
        }
    }


}