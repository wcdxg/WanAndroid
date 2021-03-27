package com.yuaihen.policeinfo.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuaihen.policeinfo.base.BaseFragment
import com.yuaihen.policeinfo.databinding.FragmentPoliticalWorkBinding

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}