package com.yuaihen.wcdxg.ui.fragment

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentMyBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.MyViewModel
import com.yuaihen.wcdxg.ui.activity.EditUserInfoActivity
import com.yuaihen.wcdxg.ui.activity.LoginActivity
import com.yuaihen.wcdxg.utils.DialogUtil
import com.yuaihen.wcdxg.utils.UserUtil

/**
 * Created by Yuaihen.
 * on 2021/3/26
 * 我的
 */
class MyFragment : BaseFragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val mDialogUtil by lazy { DialogUtil() }
    private val myViewModel by viewModels<MyViewModel>()

    companion object {
        const val REQUEST_CODE_EDIT = 6666
    }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentMyBinding.inflate(inflater)
        return binding.root
    }

    override fun initListener() {
        binding.tvExitLogin.setOnClickListener {
            mDialogUtil.showExitLoginDialog(requireContext()) {
                //清除保存的Cookie信息
                UserUtil.clearCookie()
                myViewModel.logout()
            }
        }
        binding.tvEdit.setOnClickListener {
            startActivityForResult(
                Intent(requireContext(), EditUserInfoActivity::class.java),
                REQUEST_CODE_EDIT
            )
        }

        myViewModel.logoutSuccess.observe(this) {
            if (it) {
                //退回到登录页面
                activity?.finish()
                startActivity(Intent(context, LoginActivity::class.java))
            }
        }
        myViewModel.errorLiveData.observe(this) {
            toast(it)
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