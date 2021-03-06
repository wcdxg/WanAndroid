package com.yuaihen.wcdxg.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentCollectBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.MyCollectViewModel
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.ui.adapter.ArticleAdapter
import com.yuaihen.wcdxg.ui.adapter.MyPagingLoadStateAdapter
import com.yuaihen.wcdxg.ui.adapter.TopArticleAdapter
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
import com.yuaihen.wcdxg.utils.gone
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/16
 * 收藏页面-文章和网站
 */
class CollectFragment : BaseFragment(), OnCollectClickListener {

    private var _binding: FragmentCollectBinding? = null
    private val binding get() = _binding!!
    private var currentIndex = 0    //当前页面是收藏文章还是收藏网站
    private val viewModel by viewModels<MyCollectViewModel>()
    private val pagingAdapter by lazy { ArticleAdapter(true) }
    private val webSiteAdapter by lazy { TopArticleAdapter(true) }
    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentCollectBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        const val COLLECT_ARTICLE_ARTICLE = 1
        const val COLLECT_ARTICLE_WEBSITE = 2

        fun newInstance(index: Int) = CollectFragment().also {
            it.arguments = Bundle().apply {
                putInt("index", index)
            }
        }
    }

    override fun initListener() {
        super.initListener()
        currentIndex = arguments?.getInt("index") ?: 0
        binding.swipeRefresh.setOnRefreshListener {
            if (currentIndex == COLLECT_ARTICLE_ARTICLE) {
                pagingAdapter.refresh()
            } else {
                getCollectWebSite()
            }
        }
        if (currentIndex == COLLECT_ARTICLE_ARTICLE) {
            binding.recycler.adapter = pagingAdapter
            addPagingAdapterListener()
            pagingAdapter.addOnCollectClickListener(this)
        } else {
            binding.recycler.adapter = webSiteAdapter
            webSiteAdapter.addOnCollectClickListener(this)
        }

        viewModel.apply {
            collectArticleLiveData.observe(viewLifecycleOwner) {
                binding.swipeRefresh.isRefreshing = false
                setCollectArticleToAdapter(it)
            }
            collectWebSiteLiveData.observe(viewLifecycleOwner) {
                binding.swipeRefresh.isRefreshing = false
                setCollectWebSiteToAdapter(it)
            }
            unCollectStatus.observe(viewLifecycleOwner) {
                if (it) {
                    toast(getString(R.string.unCollect_success))
                    pagingAdapter.notifyItemRemoved(currentItemPos)
                }
            }
            errorLiveData.observe(viewLifecycleOwner) {
                binding.swipeRefresh.isRefreshing = false
                toast(it.errorMsg)
            }
        }
    }

    private fun addPagingAdapterListener() {
        pagingAdapter.withLoadStateFooter(MyPagingLoadStateAdapter(pagingAdapter::retry))
        pagingAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    if (isLoadDataEnd) {
                        if (pagingAdapter.itemCount == 0) {
                            showEmptyView(true)
                        } else {
                            showEmptyView(false)
                        }
                    }
                }
                is LoadState.Error -> showEmptyView(true)
                is LoadState.Loading -> {
                }
            }
        }
    }

    private var isLoadDataEnd = false
    private fun setCollectArticleToAdapter(pagingData: PagingData<ArticleModel>) {
        lifecycleScope.launch {
            pagingAdapter.submitData(pagingData)
        }
    }

    private fun setCollectWebSiteToAdapter(list: List<ArticleModel>) {
        if (list.isNullOrEmpty()) {
            showEmptyView(true)
        } else {
            showEmptyView(false)
            webSiteAdapter.submitList(list)
        }
    }

    private fun showEmptyView(isShow: Boolean) {
        binding.apply {
            swipeRefresh.isRefreshing = false
            loadingView.gone()
            emptyView.isVisible = isShow
        }
    }


    override fun lazyLoadData() {
        super.lazyLoadData()
        if (currentIndex == COLLECT_ARTICLE_ARTICLE) {
            getCollectArticle()
        } else if (currentIndex == COLLECT_ARTICLE_WEBSITE) {
            getCollectWebSite()
        }
    }

    private fun getCollectArticle() {
        viewModel.getCollectArticle()
        isLoadDataEnd = true
    }

    private fun getCollectWebSite() {
        viewModel.getCollectWebSite()
    }

    private fun unCollectArticle(id: Int, originId: Int) {
        viewModel.unCollectById(id, originId)
    }

    private fun unCollectWebSite(id: Int) {
        viewModel.deleteCollectWebSite(id)
    }

    override fun onCollect(id: Int) {
    }

    private var currentItemPos = 0
    override fun unCollect(id: Int, originId: Int, position: Int) {
        currentItemPos = position
        if (currentIndex == COLLECT_ARTICLE_ARTICLE) {
            unCollectArticle(id, originId)
        } else if (currentIndex == COLLECT_ARTICLE_WEBSITE) {
            unCollectWebSite(id)
        }
    }

    override fun unBindView() {
        _binding = null
    }
}