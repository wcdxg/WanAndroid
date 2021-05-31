package com.yuaihen.wcdxg.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.appbar.AppBarLayout
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentHomeBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.HomeViewModel
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.net.model.HomeArticleModel
import com.yuaihen.wcdxg.ui.home.adapter.ArticleLoadStateAdapter
import com.yuaihen.wcdxg.ui.home.adapter.HomeArticleAdapter
import com.yuaihen.wcdxg.ui.home.adapter.HomeBannerAdapter
import com.yuaihen.wcdxg.ui.interf.AppBarStateChangeListener
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
    private val bannerAdapter by lazy { HomeBannerAdapter(this) }

    private enum class State {
        //Appbar的三种状态
        EXPANDED, COLLAPSED, IDLE
    }

    private var currentState = State.EXPANDED

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initListener() {
        pagingAdapter.withLoadStateFooter(ArticleLoadStateAdapter(pagingAdapter::retry))

        homeViewModel.loadingLiveData.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        homeViewModel.errorLiveData.observe(this) {
            toast(it)
        }
        homeViewModel.bannerLiveData.observe(this) {
            setBanner(it)
        }

        homeViewModel.articleLiveData.observe(this) {
            binding.swipeRefresh.isRefreshing = false
            setArticleData(it)
        }
        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
        }

        binding.appbarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if (state == State.EXPANDED) {
                    logD("hello", "${state} 折叠")
                    //折叠 设置为透明
                    ImmersionBar.with(this@HomeFragment)
                        .statusBarColor(R.color.bili_bili_pink)
                        .statusBarDarkFont(false)
//                        .statusBarColorTransform(R.color.bili_bili_pink)
                        .init()
                } else if (state == State.COLLAPSED) {
                    logD("hello", "${state} 展开")
                    //展开 设置为主色调
                    ImmersionBar.with(this@HomeFragment)
                        .statusBarColor(R.color.transparent)
                        .statusBarDarkFont(true)
                        .init()
                }
//                else if (state == State.IDLE) {
//                    ImmersionBar.with(this@HomeFragment)
//                        .statusBarColorTransform(R.color.transparent)
//                        .init()
//                }
            }
        })
    }

    override fun initData() {
        //在这里合并多个Adapter
        val concatConfig = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter = ConcatAdapter(concatConfig, bannerAdapter, pagingAdapter)
        binding.recyclerArticle.adapter = concatAdapter

        if (homeViewModel.bannerLiveData.value.isNullOrEmpty()) {
            homeViewModel.getBanner()
            homeViewModel.getArticle()
        } else {
            setHomePageData(
                homeViewModel.bannerLiveData.value!!,
                homeViewModel.articleLiveData.value
            )
        }
    }

    private fun setHomePageData(
        bannerData: List<BannerModel.Data>,
        articleLiveData: PagingData<HomeArticleModel.Data.Data>?
    ) {
        setBanner(bannerData)
        setArticleData(articleLiveData)
    }

    private fun setArticleData(pagingData: PagingData<HomeArticleModel.Data.Data>?) {
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