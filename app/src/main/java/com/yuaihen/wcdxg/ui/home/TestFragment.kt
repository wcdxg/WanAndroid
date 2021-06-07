package com.yuaihen.wcdxg.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentTestBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.HomeViewModel
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.ui.home.adapter.ArticleLoadStateAdapter
import com.yuaihen.wcdxg.ui.home.adapter.HomeArticleAdapter
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/5/30
 */
class TestFragment : BaseFragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val pagingAdapter by lazy { HomeArticleAdapter() }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View? {
        _binding = FragmentTestBinding.inflate(inflater)
        return binding.root
    }

    override fun initListener() {
        super.initListener()
        homeViewModel.articleLiveData.observe(this) {
//            binding.swipeRefresh.isRefreshing = false
            setArticleData(it)
        }
    }

    override fun initData() {
        super.initData()
        pagingAdapter.withLoadStateFooter(ArticleLoadStateAdapter(pagingAdapter::retry))
        binding.recycler.adapter = pagingAdapter
        homeViewModel.getArticle()
    }

    private fun setArticleData(pagingData: PagingData<ArticleModel>?) {
        pagingData?.let {
            lifecycleScope.launch {
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    override fun unBindView() {
        _binding = null
    }
}