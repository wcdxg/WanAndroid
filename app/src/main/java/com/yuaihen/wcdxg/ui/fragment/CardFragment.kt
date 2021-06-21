package com.yuaihen.wcdxg.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentCardBinding

/**
 * Created by Yuaihen.
 * on 2021/6/21
 * 发现栏目下 通用Fragment
 */
class CardFragment : BaseFragment() {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentCardBinding.inflate(inflater)
        return binding.root
    }

    override fun unBindView() {
        _binding = null
    }

}