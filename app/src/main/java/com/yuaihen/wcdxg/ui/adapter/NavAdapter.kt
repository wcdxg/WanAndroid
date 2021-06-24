package com.yuaihen.wcdxg.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.databinding.NavAdapterItemBinding
import com.yuaihen.wcdxg.net.model.KnowLedgeTreeModel
import com.yuaihen.wcdxg.net.model.NavigationModel
import com.yuaihen.wcdxg.ui.fragment.FindFragment
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
        with(holder.mBinding) {
            when (index) {
                FindFragment.KNOWLEDGE_TREE -> {
                    val data: KnowLedgeTreeModel.Data =
                        mData[position] as KnowLedgeTreeModel.Data
                    tvClassName.text = data.name
                    data.children.forEachIndexed { index, modelData ->
                        val child = createOrGetCacheFlexItemTextView(fbl)
                        child.text = modelData.name
                        child.setOnClickListener {
                            mOnClickListener?.onItemClick(data, index)
                        }
                        fbl.addView(child)
                    }
                    itemView.setOnClickListener {
                        mOnClickListener?.onItemClick(data, 0)
                    }
                }
                FindFragment.PAGE_NAV -> {
                    val data = mData[position] as NavigationModel.Data
                    tvClassName.text = data.name
                    data.articles.forEachIndexed { _, modelData ->
                        val child = createOrGetCacheFlexItemTextView(fbl)
                        child.text = modelData.title
                        child.setOnClickListener {
                            mOnClickListener?.onNaviItemClick(modelData.link)
                        }
                        fbl.addView(child)
                    }
                }
                FindFragment.OFFICIAL_ACCOUNTS -> {

                }
                FindFragment.PROJECT -> {
                }
                else -> {
                }
            }

        }

    }

    override fun onViewRecycled(holder: BaseBindingViewHolder<NavAdapterItemBinding>) {
        super.onViewRecycled(holder)
        val fbl = holder.mBinding.fbl
        for (i in 0 until fbl.childCount) {
            mFlexItemTextViewCaches.offer(fbl.getChildAt(i) as TextView)
        }
        fbl.removeAllViews()
    }

    private fun createOrGetCacheFlexItemTextView(fbl: FlexboxLayout): TextView {
        val tv = mFlexItemTextViewCaches.poll()
        return tv ?: createFlexItemTextView(fbl)
    }

    private fun createFlexItemTextView(fbl: FlexboxLayout): TextView {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(fbl.context)
        }
        return mInflater!!.inflate(R.layout.knowledge_child_item, fbl, false) as TextView
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    interface OnItemClickListener {
        fun onItemClick(data: KnowLedgeTreeModel.Data, position: Int)
        fun onNaviItemClick(link: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mOnClickListener = listener
    }
}