package com.yuaihen.wcdxg.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.databinding.ArticleRecycleItemBinding
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.ui.activity.WebViewActivity
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
import com.yuaihen.wcdxg.utils.trimHtml
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 首页置顶文章列表
 */
class TopArticleAdapter :
    ListAdapter<ArticleModel, BaseBindingViewHolder<ArticleRecycleItemBinding>>(
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
                tvTitle.text = Html.fromHtml(it.title.trimHtml()).toString()
                tvDesc.text = Html.fromHtml(it.desc.trimHtml()).toString()
                tvChapterName.text = "${it.superChapterName}-${it.chapterName}"
                recycleTags.adapter = ArticleTagAdapter(it.tags)
                tvDesc.isGone = it.desc.isEmpty()
                tvTop.isVisible = it.type == 1
                clRoot.setOnClickListener {
                    //跳转相关文章页面
                    val context = holder.mBinding.root.context
                    val intent = Intent(context, WebViewActivity::class.java).apply {
                        putExtras(Bundle().also {
                            it.putString(Constants.URL, data.link)
                            it.putInt(Constants.ID, data.id)
                            it.putBoolean(Constants.COLLECT, data.collect)
                        })

                    }
                    context.startActivity(intent)
                }
                ivCollect.isSelected = it.collect
                ivCollect.setOnClickListener { view ->
                    if (it.collect) {
                        //取消收藏
                        listener?.unCollect(it.id)
                        it.collect = false
                    } else {
                        //收藏
                        listener?.onCollect(it.id)
                        it.collect = true
                    }
                    ivCollect.isSelected = it.collect
                }
            }
        }
    }

    private var listener: OnCollectClickListener? = null
    fun addOnCollectClickListener(listener: OnCollectClickListener) {
        this.listener = listener
    }
}