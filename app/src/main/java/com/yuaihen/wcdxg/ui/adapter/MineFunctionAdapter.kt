package com.yuaihen.wcdxg.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.databinding.MineFunctionItemBinding
import com.yuaihen.wcdxg.net.model.MineMenuModel
import com.yuaihen.wcdxg.ui.activity.AboutUsActivity
import com.yuaihen.wcdxg.ui.activity.AppSettingActivity
import com.yuaihen.wcdxg.ui.activity.MyCollectActivity
import com.yuaihen.wcdxg.ui.activity.CoinActivity
import com.yuaihen.wcdxg.utils.ToastUtil
import com.yuaihen.wcdxg.utils.gone
import com.yuaihen.wcdxg.viewbinding.BaseBindingViewHolder
import com.yuaihen.wcdxg.viewbinding.getViewHolder

/**
 * Created by Yuaihen.
 * on 2021/6/15
 * 我的页面 功能菜单列表
 */
class MineFunctionAdapter(private val menuList: MutableList<MineMenuModel>) :
    RecyclerView.Adapter<BaseBindingViewHolder<MineFunctionItemBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<MineFunctionItemBinding> {
        return parent.getViewHolder(MineFunctionItemBinding::inflate)
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<MineFunctionItemBinding>,
        position: Int
    ) {
        holder.mBinding.apply {
            if (position == menuList.size - 1) {
                viewLine.gone()
            }

            val data = menuList[position]
            tvFunctionName.text = data.menuName
            data.menuIconResId?.let {
                ivIcon.setBackgroundResource(it)
            }

            llRoot.setOnClickListener {
                openPageById(data.menuId, holder.itemView.context)
            }
        }
    }

    private fun openPageById(id: Int, context: Context) {
        when (id) {
            Constants.ID_MY_COLLECT -> MyCollectActivity.start(context)
            Constants.ID_MY_COIN -> CoinActivity.start(context)
            Constants.ID_MY_SHARES -> ToastUtil.show("开发中")
            Constants.ID_MY_COLLECT4 -> ToastUtil.show("开发中")
            Constants.ID_ABOUT_US -> AboutUsActivity.start(context)
            Constants.ID_SYSTEM_CONFIG -> AppSettingActivity.start(context)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

}