package com.yuaihen.policeinfo.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuaihen.policeinfo.base.BaseFragment
import com.yuaihen.policeinfo.databinding.FragmentHealthBinding

/**
 * Created by Yuaihen.
 * on 2021/3/26
 * 健康
 */
class HealthFragment : BaseFragment() {

    private var _binding: FragmentHealthBinding? = null
    private val binding get() = _binding!!

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentHealthBinding.inflate(inflater)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}