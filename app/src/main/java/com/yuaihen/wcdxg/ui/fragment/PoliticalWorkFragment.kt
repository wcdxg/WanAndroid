package com.yuaihen.wcdxg.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentPoliticalWorkBinding

/**
 * Created by Yuaihen.
 * on 2021/3/26
 * 政工
 */
class PoliticalWorkFragment : BaseFragment() {

    private var _binding: FragmentPoliticalWorkBinding? = null
    private val binding get() = _binding!!

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPoliticalWorkBinding.inflate(inflater)
        return binding.root
    }


    override fun unBindView() {
        _binding = null
    }
}