package com.yuaihen.wcdxg.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yuaihen.wcdxg.databinding.CoinRecordItemBinding
import com.yuaihen.wcdxg.net.model.CoinRecordModel
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 个人积分获取记录
 */
class CoinRecordAdapter :
    PagingDataAdapter<CoinRecordModel.CoinData, BaseBindingViewHolder<CoinRecordItemBinding>>(
        DifferCallback
    ) {

    object DifferCallback : DiffUtil.ItemCallback<CoinRecordModel.CoinData>() {
        override fun areItemsTheSame(
            oldItem: CoinRecordModel.CoinData,
            newItem: CoinRecordModel.CoinData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CoinRecordModel.CoinData,
            newItem: CoinRecordModel.CoinData
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<CoinRecordItemBinding> {
        return parent.getViewHolder(CoinRecordItemBinding::inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<CoinRecordItemBinding>,
        position: Int
    ) {
        val data = getItem(position)
        data?.let {
            val firstSpace: Int = it.desc.indexOf(" ")
            val secondSpace: Int = it.desc.indexOf(" ", firstSpace + 1)
            val time: String = it.desc.substring(0, secondSpace)
            val title: String = it.desc.substring(secondSpace + 1)
                .replace(",", "")
                .replace("：", "")
                .replace(" ", "")
            with(holder.mBinding) {
                tvReason.text = title
                tvDate.text = time
                tvCoinCount.text = "+${it.coinCount}"
            }
        }
    }

}