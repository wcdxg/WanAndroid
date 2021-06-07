package com.yuaihen.wcdxg.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentHomeBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.HomeViewModel
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.ui.home.adapter.ArticleLoadStateAdapter
import com.yuaihen.wcdxg.ui.home.adapter.HomeArticleAdapter
import com.yuaihen.wcdxg.ui.home.adapter.HomeBannerAdapter
import com.yuaihen.wcdxg.ui.home.adapter.TopArticleAdapter
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/4/30
 */
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val pagingAdapter by lazy { HomeArticleAdapter() }
    private val topArticleAdapter by lazy { TopArticleAdapter() }
    private val bannerAdapter by lazy { HomeBannerAdapter(this) }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initListener() {
        pagingAdapter.withLoadStateFooter(ArticleLoadStateAdapter(pagingAdapter::retry))
        homeViewModel.apply {
            loadingLiveData.observe(this@HomeFragment) {
                if (it) showLoading() else hideLoading()
            }
            errorLiveData.observe(this@HomeFragment) {
                toast(it)
            }
            bannerLiveData.observe(this@HomeFragment) {
                setBanner(it)
            }
            articleLiveData.observe(this@HomeFragment) {
                binding.swipeRefresh.isRefreshing = false
                setArticleData(it)
            }
            topArticleLiveData.observe(this@HomeFragment) {
                setTopArticleData(it)
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
        }

    }

    override fun initData() {
        //在这里合并多个Adapter
        val concatConfig = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter =
            ConcatAdapter(concatConfig, bannerAdapter, topArticleAdapter, pagingAdapter)
        binding.recyclerArticle.adapter = concatAdapter

        if (homeViewModel.bannerLiveData.value.isNullOrEmpty()) {
            homeViewModel.getBanner()
            homeViewModel.getArticle()
            homeViewModel.getArticleTop()
        } else {
            setHomePageData(
                homeViewModel.bannerLiveData.value!!,
                homeViewModel.articleLiveData.value,
                homeViewModel.topArticleLiveData.value
            )
        }
    }

    private fun setHomePageData(
        bannerData: List<BannerModel.Data>,
        articleLiveData: PagingData<ArticleModel>?,
        topArticle: List<ArticleModel>?
    ) {
        setBanner(bannerData)
        setArticleData(articleLiveData)
        setTopArticleData(topArticle)
    }

    private fun setTopArticleData(topArticle: List<ArticleModel>?) {
        topArticleAdapter.submitList(topArticle)
    }

    private fun setArticleData(pagingData: PagingData<ArticleModel>?) {
        pagingData?.let {
            lifecycleScope.launch {
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun setBanner(bannerModel: List<BannerModel.Data>) {
        bannerAdapter.setData(bannerModel.toMutableList())
    }

    override fun unBindView() {
        _binding = null
    }

}