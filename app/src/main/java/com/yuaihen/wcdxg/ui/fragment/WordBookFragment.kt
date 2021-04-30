package com.yuaihen.wcdxg.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentWordBookBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.WordBookViewModel
import com.yuaihen.wcdxg.ui.adapter.WordBookAdapter

/**
 * Created by Yuaihen.
 * on 2021/4/30
 */
class WordBookFragment : BaseFragment() {

    private var _binding: FragmentWordBookBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WordBookViewModel>()

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentWordBookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initListener() {

    }

    override fun initData() {
        val wordBookAdapter = WordBookAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = wordBookAdapter
        }
    }

    override fun unBindView() {
        _binding = null
    }
}