package com.yuaihen.wcdxg.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuaihen.wcdxg.databinding.FlexboxChildItemBinding
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/6/25
 * Tag标签Adapter
 */
class FlexBoxAdapter :
    RecyclerView.Adapter<BaseBindingViewHolder<FlexboxChildItemBinding>>() {

    private val mData = mutableListOf<String>()

    fun setNewData(list: List<String>) {
        mData.clear()
        mData.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<FlexboxChildItemBinding> {
        return parent.getViewHolder(FlexboxChildItemBinding::inflate)
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<FlexboxChildItemBinding>,
        position: Int
    ) {
        holder.mBinding.apply {
            val name = mData[position]
            tvName.text = name
            tvName.setOnClickListener {
                listener?.onFlexItemClick(name, position)
            }
        }
    }

    interface OnItemClickListener {
        fun onFlexItemClick(name: String, position: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount() = mData.size
}