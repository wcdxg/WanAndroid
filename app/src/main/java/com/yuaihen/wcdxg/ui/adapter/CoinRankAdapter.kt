package com.yuaihen.wcdxg.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.databinding.CoinRankItemBinding
import com.yuaihen.wcdxg.net.model.CoinRecordModel
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 积分排行榜
 */
class CoinRankAdapter :
    PagingDataAdapter<CoinRecordModel.CoinData, BaseBindingViewHolder<CoinRankItemBinding>>(
        DifferCallback
    ) {

    object DifferCallback : DiffUtil.ItemCallback<CoinRecordModel.CoinData>() {
        override fun areItemsTheSame(
            oldItem: CoinRecordModel.CoinData,
            newItem: CoinRecordModel.CoinData
        ): Boolean {
            return oldItem.userId == newItem.userId
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
    ): BaseBindingViewHolder<CoinRankItemBinding> {
        return parent.getViewHolder(CoinRankItemBinding::inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<CoinRankItemBinding>,
        position: Int
    ) {
        val data = getItem(position)
        data?.let {
            with(holder.mBinding) {
                tvUserName.text = data.username
                tvCoinCount.text = data.coinCount.toString()
                when (position) {
                    0 -> tvRank.setBackgroundResource(R.drawable.ic_rank_1)
                    1 -> tvRank.setBackgroundResource(R.drawable.ic_rank_2)
                    2 -> tvRank.setBackgroundResource(R.drawable.ic_rank_3)
                    else -> {
                        tvRank.setBackgroundDrawable(null)
                        tvRank.text = "${position + 1}"
                    }
                }
            }
        }
    }

}