package com.yuaihen.wcdxg.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentEmptyBinding

/**
 * Created by Yuaihen.
 * on 2021/6/18
 */
class TestFragment : BaseFragment() {

    private var _binding: FragmentEmptyBinding? = null
    private val binding get() = _binding!!

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View? {
        _binding = FragmentEmptyBinding.inflate(inflater)
        return binding.root
    }

    override fun unBindView() {
        _binding = null
    }
}