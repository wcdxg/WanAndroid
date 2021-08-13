package com.yuaihen.wcdxg.utils

import android.text.Html
import android.view.View
import android.view.ViewStub
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.adapters.ViewStubBindingAdapter
import com.yuaihen.wcdxg.databinding.ArticleRecycleItemBinding
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.ui.activity.WebViewActivity
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
import kotlinx.android.synthetic.main.layout_article_normal.view.*
import kotlinx.android.synthetic.main.layout_article_pic.view.*

/**
 * Created by Yuaihen.
 * on 2021/6/16
 */
object ArticleUtils {

    fun setArticleData(
        mBinding: ArticleRecycleItemBinding,
        articleModel: ArticleModel?,
        isCollectPage: Boolean = false,
        listener: OnCollectClickListener?,
        position: Int
    ) {
        articleModel?.let {
            if (it.envelopePic.isEmpty()) {
                //普通文章列表
                mBinding.layoutNormal.root.visible()
                mBinding.layoutPic.root.gone()
                with(mBinding.layoutNormal) {
                    tvAuthor.text = when {
                        it.author.isNotEmpty() -> {
                            it.author
                        }
                        it.shareUser.isNotEmpty() -> {
                            it.shareUser
                        }
                        else -> {
                            "匿名"
                        }
                    }

                    tvAuthor.setOnClickListener {
                        //TODO 点击事件未添加
                        ToastUtil.show("TODO ")
                    }

                    tvPublishTime.text = it.niceDate
                    tvTitle.text = Html.fromHtml(it.title)
                    if (it.desc.isEmpty()) {
                        tvTitle.isSingleLine = false
                    } else {
                        tvTitle.isSingleLine = true
                        val desc = Html.fromHtml(it.desc).toString()
                        tvDesc.text = StringUtil.removeAllBank(desc, 2)
                    }

                    if (isCollectPage) {
                        tvChapterName.text = it.chapterName
                    } else {
                        tvChapterName.text =
                            Html.fromHtml("${it.superChapterName}-${it.chapterName}")
                    }
                    tvChapterName.setOnClickListener {
                        //TODO 点击事件未添加
                        ToastUtil.show("TODO ")
                    }
                    if (it.tags.isNotEmpty()) {
                        tvTagName.text = it.tags[0].name
                        //TODO 点击事件未添加
                        tvTagName.setOnClickListener {
                            ToastUtil.show("TODO ")
                        }
                    } else {
                        tvTagName.gone()
                    }
                    tvDesc.isGone = it.desc.isEmpty()
                    tvTop.isVisible = it.type == 1
                    if (isCollectPage) {
                        it.collect = true
                    }
                    ivCollect.isSelected = it.collect
                    ivCollect.setOnClickListener { _ ->
                        if (it.collect) {
                            //取消收藏
                            listener?.unCollect(it.id, it.originId, position)
                            it.collect = false
                        } else {
                            //收藏
                            listener?.onCollect(it.id)
                            it.collect = true
                        }
                        ivCollect.isSelected = it.collect
                    }

                }
            } else {
                //带图片的文章列表
                mBinding.layoutNormal.root.gone()
                mBinding.layoutPic.root.visible()
                with(mBinding.layoutPic) {
                    GlideUtil.showImageView(ivBanner, it.envelopePic)
                    tvPublishTime.text = it.niceDate
                    tvTitle.text = Html.fromHtml(it.title)
                    if (it.desc.isEmpty()) {
                        tvTitle.isSingleLine = false
                    } else {
                        tvTitle.isSingleLine = true
                        val desc = Html.fromHtml(it.desc).toString()
                        tvDesc.text = StringUtil.removeAllBank(desc, 2)
                    }
                    if (isCollectPage) {
                        it.collect = true
                    }
                    ivCollect.isSelected = it.collect
                    ivCollect.setOnClickListener { _ ->
                        if (it.collect) {
                            //取消收藏
                            listener?.unCollect(it.id, it.originId, position)
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

            mBinding.clRoot.setOnClickListener { _ ->
                //跳转相关文章页面
                val context = mBinding.root.context
                WebViewActivity.start(context, it.link, it.id, it.collect)
            }


        }
    }
}