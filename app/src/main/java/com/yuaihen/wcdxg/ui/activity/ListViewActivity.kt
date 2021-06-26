package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.databinding.ActivityListViewBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.FindViewModel
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.ui.adapter.ArticleAdapter
import com.yuaihen.wcdxg.ui.adapter.MyPagingLoadStateAdapter
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
import com.yuaihen.wcdxg.utils.gone
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/24
 * ListView Activity
 */
class ListViewActivity : BaseActivity() {

    private lateinit var binding: ActivityListViewBinding
    private val pagingAdapter by lazy { ArticleAdapter() }
    private val viewModel by viewModels<FindViewModel>()
    private var id = 0
    private var name = ""
    private var isLoadDataEnd = false
    private var loadType = WX_Article

    override fun getBindingView(): View {
        binding = ActivityListViewBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        const val WX_Article = 1
        const val PROJECT_Article = 2
        fun start(context: Context, bundle: Bundle?) {
            val intent = Intent(context, ListViewActivity::class.java).apply {
                bundle?.let {
                    putExtras(it)
                }
            }
            context.startActivity(intent)
        }
    }

    override fun initListener() {
        super.initListener()
        addPagingAdapterListener()
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                pagingAdapter.refresh()
            }
            recycler.apply {
                layoutManager = LinearLayoutManager(this@ListViewActivity)
                adapter = pagingAdapter
            }
        }
        viewModel.apply {
            errorLiveData.observe(this@ListViewActivity) {
                binding.swipeRefresh.isRefreshing = false
                toast(it)
            }
            loadingLiveData.observe(this@ListViewActivity) {
                binding.loadingView.isVisible = it
            }
            wxArticleLiveData.observe(this@ListViewActivity) {
                setArticleListToAdapter(it)
            }
            projectArticleLiveData.observe(this@ListViewActivity) {
                setArticleListToAdapter(it)
            }
        }
    }

    private fun setArticleListToAdapter(pagingData: PagingData<ArticleModel>) {
        binding.swipeRefresh.isRefreshing = false
        lifecycleScope.launch {
            pagingAdapter.submitData(pagingData)
        }
    }

    private fun addPagingAdapterListener() {
        pagingAdapter.addOnCollectClickListener(object : OnCollectClickListener {
            override fun onCollect(id: Int) {
                viewModel.collectArticle(id)
            }

            override fun unCollect(id: Int, originId: Int, position: Int) {
                viewModel.unCollectByOriginId(id)
            }

        })
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

    override fun initData() {
        intent.apply {
            id = getIntExtra(Constants.ID, 0)
            name = getStringExtra(Constants.NAME) ?: ""
            loadType = getIntExtra(Constants.LOAD_TYPE, 0)
        }

        binding.titleView.setTitle(name)
        if (loadType == WX_Article) {
            getWxArticleList()
        } else if (loadType == PROJECT_Article) {
            getProjectArticleList()
        }
    }

    private fun getProjectArticleList() {
        viewModel.getProjectArticleList(id)
        isLoadDataEnd = true
    }

    private fun getWxArticleList() {
        viewModel.getWxArticleList(id)
        isLoadDataEnd = true
    }

    private fun showEmptyView(isShow: Boolean) {
        binding.apply {
            swipeRefresh.isRefreshing = false
            loadingView.gone()
            emptyView.isVisible = isShow
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }
}