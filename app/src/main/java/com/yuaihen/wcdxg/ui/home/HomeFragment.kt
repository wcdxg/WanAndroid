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
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.net.model.HomeArticleModel
import com.yuaihen.wcdxg.ui.home.adapter.ArticleLoadStateAdapter
import com.yuaihen.wcdxg.ui.home.adapter.HomeArticleAdapter
import com.yuaihen.wcdxg.ui.home.adapter.HomeBannerAdapter
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
//        binding.banner.apply {
//            addBannerLifecycleObserver(this@HomeFragment)
//            setIndicator(CircleIndicator(requireContext()))
//            setIndicatorSelectedColor(getResources().getColor(R.color.bili_bili_pink))
//            setAdapter(object : BannerImageAdapter<BannerModel.Data>(bannerModel) {
//                override fun onBindView(
//                    holder: BannerImageHolder?,
//                    data: BannerModel.Data?,
//                    position: Int,
//                    size: Int
//                ) {
//                    holder?.imageView?.let { GlideUtil.showImageView(it, data?.imagePath) }
//                }
//            })
//        }
    }

    override fun unBindView() {
        _binding = null
    }

}