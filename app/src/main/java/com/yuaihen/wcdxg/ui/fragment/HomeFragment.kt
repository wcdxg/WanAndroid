package com.yuaihen.wcdxg.ui.fragment

import android.content.Intent
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
import com.yuaihen.wcdxg.ui.activity.LoginActivity
import com.yuaihen.wcdxg.ui.adapter.MyPagingLoadStateAdapter
import com.yuaihen.wcdxg.ui.adapter.HomeArticleAdapter
import com.yuaihen.wcdxg.ui.adapter.HomeBannerAdapter
import com.yuaihen.wcdxg.ui.adapter.TopArticleAdapter
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/4/30
 */
class HomeFragment : BaseFragment(), OnCollectClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<HomeViewModel>()
    private val pagingAdapter by lazy { HomeArticleAdapter() }
    private val topArticleAdapter by lazy { TopArticleAdapter() }
    private val bannerAdapter by lazy { HomeBannerAdapter(this) }

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initListener() {
        pagingAdapter.withLoadStateFooter(MyPagingLoadStateAdapter(pagingAdapter::retry))
        viewModel.apply {
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
            unLoginStateLiveData.observe(this@HomeFragment) { isUnlogin ->
                if (isUnlogin) {
                    //未登录跳转登录页面
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
        }

        topArticleAdapter.addOnCollectClickListener(this)
        pagingAdapter.addOnCollectClickListener(this)
    }

    override fun initData() {
        //在这里合并多个Adapter
        val concatConfig = ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build()
        val concatAdapter =
            ConcatAdapter(concatConfig, bannerAdapter, topArticleAdapter, pagingAdapter)
        binding.recyclerArticle.adapter = concatAdapter

        if (viewModel.bannerLiveData.value.isNullOrEmpty()) {
            viewModel.getBanner()
            viewModel.getArticle()
            viewModel.getArticleTop()
        } else {
            setHomePageData(
                viewModel.bannerLiveData.value!!,
                viewModel.articleLiveData.value,
                viewModel.topArticleLiveData.value
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

    override fun onCollect(id: Int) {
        viewModel.collectArticle(id)
    }

    override fun unCollect(id: Int, originId: Int, position: Int) {
        viewModel.unCollectByOriginId(id)
    }

}