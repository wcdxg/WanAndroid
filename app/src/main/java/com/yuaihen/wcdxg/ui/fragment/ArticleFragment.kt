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
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.databinding.FragmentArticleBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.MyCollectViewModel
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.ui.adapter.ArticleAdapter
import com.yuaihen.wcdxg.ui.adapter.MyPagingLoadStateAdapter
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
import com.yuaihen.wcdxg.utils.gone
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/16
 * 文章列表页面
 */
class ArticleFragment : BaseFragment(), OnCollectClickListener {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private var currentPageCid = 0
    private val viewModel by viewModels<MyCollectViewModel>()
    private val pagingAdapter by lazy { ArticleAdapter() }
    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentArticleBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        fun create(id: Int) = ArticleFragment().also {
            it.arguments = Bundle().apply {
                putInt(Constants.CID, id)
            }
        }
    }

    override fun initListener() {
        super.initListener()
        binding.swipeRefresh.setOnRefreshListener {
            pagingAdapter.refresh()
        }
        binding.recycler.adapter = pagingAdapter

        addPagingAdapterListener()
        pagingAdapter.addOnCollectClickListener(this)
        pagingAdapter.withLoadStateFooter(MyPagingLoadStateAdapter(pagingAdapter::retry))

        viewModel.apply {
            unCollectStatus.observe(viewLifecycleOwner) {
                if (it) {
                    toast(getString(R.string.unCollect_success))
                    pagingAdapter.notifyItemRemoved(currentItemPos)
                }
            }
            errorLiveData.observe(viewLifecycleOwner) {
                binding.swipeRefresh.isRefreshing = false
                toast(it)
            }
            knowledgeArticleLiveData.observe(this@ArticleFragment) {
                binding.swipeRefresh.isRefreshing = false
                setCollectArticleToAdapter(it)
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

    private fun showEmptyView(isShow: Boolean) {
        binding.loadingView.gone()
        binding.ivEmpty.isVisible = isShow
        binding.tvEmpty.isVisible = isShow
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        currentPageCid = arguments?.getInt(Constants.CID) ?: 0
        getArticleForCid()
    }

    private fun getArticleForCid() {
        viewModel.getKnowledgeArticleByCid(currentPageCid)
        isLoadDataEnd = true
    }

    private fun unCollectArticle(id: Int, originId: Int) {
        viewModel.unCollectById(id, originId)
    }

    override fun onCollect(id: Int) {
        viewModel.collectArticle(id)
    }

    private var currentItemPos = 0
    override fun unCollect(id: Int, originId: Int, position: Int) {
        currentItemPos = position
        unCollectArticle(id, originId)
    }

    override fun unBindView() {
        _binding = null
    }

}