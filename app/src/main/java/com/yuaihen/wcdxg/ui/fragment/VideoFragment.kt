package com.yuaihen.wcdxg.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentVideoBinding

/**
 * Created by Yuaihen.
 * on 2021/4/30
 */
class VideoFragment : BaseFragment() {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun unBindView() {
        _binding = null
    }

}