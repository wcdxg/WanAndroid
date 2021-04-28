package com.yuaihen.wcdxg.ui.fragment

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentMyBinding
import com.yuaihen.wcdxg.ui.activity.EditUserInfoActivity
import com.yuaihen.wcdxg.utils.DialogUtil

/**
 * Created by Yuaihen.
 * on 2021/3/26
 * 我的
 */
class MyFragment : BaseFragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val mDialogUtil by lazy { DialogUtil() }

    companion object {
        const val REQUEST_CODE_EDIT = 6666
    }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentMyBinding.inflate(inflater)
        return binding.root
    }

    override fun initListener() {
        binding.tvExitLogin.setOnClickListener {
            mDialogUtil.showExitLoginDialog(requireContext())
        }
        binding.tvEdit.setOnClickListener {
            startActivityForResult(Intent(requireContext(), EditUserInfoActivity::class.java), REQUEST_CODE_EDIT)
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