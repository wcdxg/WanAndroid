package com.yuaihen.wcdxg.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentWendaBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.WenDaViewModel
import com.yuaihen.wcdxg.ui.adapter.ArticleAdapter
import com.yuaihen.wcdxg.utils.gone
import com.yuaihen.wcdxg.utils.visible
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/21
 * 问答界面Fragment
 */
class WenDaFragment : BaseFragment() {

    private var _binding: FragmentWendaBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WenDaViewModel>()
    private val adapter by lazy { ArticleAdapter() }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentWendaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        super.initListener()
        addPagingAdapterListener()
        viewModel.wenDaArticleLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun addPagingAdapterListener() {
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    if (isFirstLoading) {
                        binding.swipeRefresh.isRefreshing = false
                        binding.loadingView.gone()
                    }
                }
                is LoadState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    binding.loadingView.gone()
                }
                is LoadState.Loading -> {
                }
            }
        }
    }

    private var isFirstLoading = false
    override fun initData() {
        super.initData()
        binding.recycler.adapter = adapter
        binding.loadingView.visible()
        isFirstLoading = true
        viewModel.getWenDaList()
    }

    override fun unBindView() {
        _binding = null
    }
}