package com.yuaihen.wcdxg.ui.fragment

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentMineBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.MyViewModel
import com.yuaihen.wcdxg.net.model.MineMenuModel
import com.yuaihen.wcdxg.net.model.UserInfoModel
import com.yuaihen.wcdxg.ui.activity.EditUserInfoActivity
import com.yuaihen.wcdxg.ui.adapter.MineFunctionAdapter
import com.yuaihen.wcdxg.utils.UserUtil

/**
 * Created by Yuaihen.
 * on 2021/3/26
 * 我的
 */
class MineFragment : BaseFragment() {

    private var _binding: FragmentMineBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MyViewModel>()

    companion object {
        const val REQUEST_CODE_EDIT = 6666
    }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentMineBinding.inflate(inflater)
        return binding.root
    }

    override fun initListener() {
        binding.tvEdit.setOnClickListener {
            startActivityForResult(
                Intent(requireContext(), EditUserInfoActivity::class.java),
                REQUEST_CODE_EDIT
            )
        }

        viewModel.apply {
            errorLiveData.observe(this@MineFragment) {
                toast(it.errorMsg)
            }
            userInfoLiveData.observe(this@MineFragment) {
                setUserInfo(it)
            }
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        createMenuItem()
        viewModel.getUserInfo()
    }

    private fun createMenuItem() {
        val menuNameList = resources.getStringArray(R.array.menu_name)
        val menuId = resources.getIntArray(R.array.menu_id)
        val ty = resources.obtainTypedArray(R.array.menu_icon)
        val menuItemList = mutableListOf<MineMenuModel>()
        menuNameList.forEachIndexed { index, menuName ->
            menuItemList.add(
                MineMenuModel(
                    menuId[index],
                    menuName,
                    ty.getResourceId(index, 0)
                )
            )
        }
        ty.recycle()
        val adapter = MineFunctionAdapter(menuItemList)
        binding.recyclerFunction.adapter = adapter
    }

    private fun setUserInfo(data: UserInfoModel) {
        binding.apply {
            tvName.text = UserUtil.getUserName()
            tvRank.text = data.rank
            tvCoinCount.text = data.coinCount.toString()
            UserUtil.setUserCoinCount(data.coinCount)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_EDIT) {
                toast("编辑资料完成,更新...")
            }
        }
    }

    override fun unBindView() {
        _binding = null
    }
}