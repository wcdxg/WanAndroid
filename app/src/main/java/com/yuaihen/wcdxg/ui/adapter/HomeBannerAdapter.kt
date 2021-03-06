package com.yuaihen.wcdxg.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.yuaihen.wcdxg.databinding.HomeBannerItemBinding
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.ui.fragment.HomeFragment
import com.yuaihen.wcdxg.utils.GlideUtil
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/5/30
 * 主页Banner Adapter
 */
class HomeBannerAdapter(private val homeFragment: HomeFragment) :
    RecyclerView.Adapter<BaseBindingViewHolder<HomeBannerItemBinding>>() {

    private val data = mutableListOf<BannerModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<HomeBannerItemBinding> {
        return parent.getViewHolder(HomeBannerItemBinding::inflate)
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<HomeBannerItemBinding>,
        position: Int
    ) {
        holder.mBinding.banner.apply {
            addBannerLifecycleObserver(homeFragment.viewLifecycleOwner)
            setIndicator(CircleIndicator(getContext()))
            setAdapter(object : BannerImageAdapter<BannerModel>(data) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerModel?,
                    position: Int,
                    size: Int
                ) {
                    holder?.imageView?.let {
                        it.scaleType = ImageView.ScaleType.CENTER_CROP
                        GlideUtil.showImageView(it, data?.imagePath)
                    }
                }
            })
        }
    }

    fun setData(newData: MutableList<BannerModel>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return 1
    }
}