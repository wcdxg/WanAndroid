package com.yuaihen.wcdxg.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuaihen.wcdxg.databinding.HomeArticleTagItemBinding
import com.yuaihen.wcdxg.net.model.HomeArticleModel
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 首页文章列表Tag Adapter
 */
class ArticleTagAdapter(val data: List<HomeArticleModel.Data.Data.Tag>?) :
    RecyclerView.Adapter<BaseBindingViewHolder<HomeArticleTagItemBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<HomeArticleTagItemBinding> {
        return parent.getViewHolder(HomeArticleTagItemBinding::inflate)
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<HomeArticleTagItemBinding>,
        position: Int
    ) {
        holder.mBinding.tvTagName.text = data?.get(position)?.name
        //TODO 点击事件

    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }
}