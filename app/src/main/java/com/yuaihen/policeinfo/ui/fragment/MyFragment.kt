package com.yuaihen.policeinfo.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuaihen.policeinfo.base.BaseFragment
import com.yuaihen.policeinfo.databinding.FragmentMyBinding
import com.yuaihen.policeinfo.utils.DialogUtil

/**
 * Created by Yuaihen.
 * on 2021/3/26
 * 我的
 */
class MyFragment : BaseFragment() {

    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val mDialogUtil by lazy { DialogUtil() }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentMyBinding.inflate(inflater)
        return binding.root
    }

    override fun initListener() {
        binding.tvExitLogin.setOnClickListener {
            mDialogUtil.showExitLoginDialog(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}