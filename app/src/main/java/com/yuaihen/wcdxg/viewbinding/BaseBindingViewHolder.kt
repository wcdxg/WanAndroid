package com.yuaihen.wcdxg.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * RecyclerView ViewHolder ViewBinding
 */
open class BaseBindingViewHolder<T : ViewBinding> private constructor(val mBinding: T) :
    RecyclerView.ViewHolder(mBinding.root) {

    constructor(
        parent: ViewGroup,
        creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T
    ) : this(creator(LayoutInflater.from(parent.context), parent, false))

}


fun <T : ViewBinding> ViewGroup.getViewHolder(
    creator: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T
): BaseBindingViewHolder<T> = BaseBindingViewHolder(this, creator)



