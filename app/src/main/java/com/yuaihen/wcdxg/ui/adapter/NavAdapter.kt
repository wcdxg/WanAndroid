package com.yuaihen.wcdxg.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.yuaihen.wcdxg.databinding.NavAdapterItemBinding
import com.yuaihen.wcdxg.net.model.ArticleListModel
import com.yuaihen.wcdxg.ui.activity.ListViewActivity
import com.yuaihen.wcdxg.ui.fragment.FindFragment
import com.yuaihen.wcdxg.utils.gone
import com.yuaihen.wcdxg.utils.visible
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder
import java.util.*

/**
 * Created by Yuaihen.
 * on 2021/6/23
 */
class NavAdapter(private val index: Int) :
    RecyclerView.Adapter<BaseBindingViewHolder<NavAdapterItemBinding>>() {

    private val mData: MutableList<Any> = mutableListOf()
    private var mOnClickListener: OnItemClickListener? = null
    private val mFlexItemTextViewCaches: Queue<TextView> = LinkedList()
    private var mInflater: LayoutInflater? = null

    fun updateData(data: List<Any>) {
        mData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<NavAdapterItemBinding> {
        return parent.getViewHolder(NavAdapterItemBinding::inflate)
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<NavAdapterItemBinding>,
        position: Int
    ) {
        val tagList = mutableListOf<String>()
        with(holder.mBinding) {
            when (index) {
                FindFragment.KNOWLEDGE_TREE -> {
                    val data: ArticleListModel.Data =
                        mData[position] as ArticleListModel.Data
                    data.children.forEach { modelData ->
                        tagList.add(modelData.name)
                    }
                    tvClassName.text = Html.fromHtml(data.name)
                    val adapter = createTagAdapter(this, tagList)
                    adapter.setOnItemClickListener(object : FlexBoxAdapter.OnItemClickListener {
                        override fun onFlexItemClick(name: String, position: Int) {
                            mOnClickListener?.onKnowledgeItemClick(data, position)
                        }
                    })
                }
                FindFragment.PAGE_NAV -> {
                    val data = mData[position] as ArticleListModel.Data
                    data.articles.forEach { modelData ->
                        tagList.add(modelData.title)
                    }
                    tvClassName.text = Html.fromHtml(data.name)
                    val adapter = createTagAdapter(this, tagList)
                    adapter.setOnItemClickListener(object : FlexBoxAdapter.OnItemClickListener {
                        override fun onFlexItemClick(name: String, position: Int) {
                            mOnClickListener?.onNaviItemClick(data.articles[position].link)
                        }
                    })
                }
                FindFragment.OFFICIAL_ACCOUNTS -> {
                    val data = mData[position] as ArticleListModel.Data
                    showCardView(this, data, index)
                }
                FindFragment.PROJECT -> {
                    val data = mData[position] as ArticleListModel.Data
                    showCardView(this, data, index)
                }
                else -> {
                }
            }
        }
    }

    private fun createTagAdapter(
        binding: NavAdapterItemBinding,
        list: List<String>
    ): FlexBoxAdapter {
        val tagAdapter = FlexBoxAdapter().apply {
            setNewData(list)
        }
        binding.tagRecycler.apply {
            layoutManager = FlexboxLayoutManager(
                context,
                FlexDirection.ROW,
                FlexWrap.WRAP
            )
            adapter = tagAdapter
        }

        return tagAdapter
    }

    private fun showCardView(
        binding: NavAdapterItemBinding,
        data: ArticleListModel.Data,
        index: Int
    ) {
        binding.apply {
            val lp = LinearLayout.LayoutParams(rootView.layoutParams)
            lp.topMargin = 0
            lp.bottomMargin = 0
            rootView.layoutParams = lp
            tvClassName.gone()
            tagRecycler.gone()
            cardView.visible()
            tvCardName.text = Html.fromHtml(data.name)
            if (index == FindFragment.OFFICIAL_ACCOUNTS) {
                cardView.setOnClickListener {
                    mOnClickListener?.onWxItemClick(
                        data.id,
                        data.name,
                        ListViewActivity.WX_Article
                    )
                }
            } else if (index == FindFragment.PROJECT) {
                cardView.setOnClickListener {
                    mOnClickListener?.onWxItemClick(
                        data.id,
                        data.name,
                        ListViewActivity.PROJECT_Article
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    interface OnItemClickListener {
        fun onKnowledgeItemClick(data: ArticleListModel.Data, position: Int)
        fun onNaviItemClick(link: String)
        fun onWxItemClick(id: Int, name: String, loadType: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mOnClickListener = listener
    }
}