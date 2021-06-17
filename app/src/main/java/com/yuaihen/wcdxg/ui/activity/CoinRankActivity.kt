package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityCoinRankBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.CoinViewModel
import com.yuaihen.wcdxg.ui.adapter.CoinRankAdapter
import com.yuaihen.wcdxg.ui.adapter.MyPagingLoadStateAdapter
import com.yuaihen.wcdxg.utils.gone
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/17
 * 积分排行榜
 */
class CoinRankActivity : BaseActivity() {

    private lateinit var binding: ActivityCoinRankBinding
    private val viewModel by viewModels<CoinViewModel>()
    private val adapter by lazy { CoinRankAdapter() }
    private var isLoadDataEnd = false

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CoinRankActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getBindingView(): View {
        binding = ActivityCoinRankBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        super.initListener()
        binding.recycler.adapter = adapter
        viewModel.apply {
            coinRankRecordLiveData.observe(this@CoinRankActivity) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
            errorLiveData.observe(this@CoinRankActivity) {
                toast(it)
            }
            loadingLiveData.observe(this@CoinRankActivity) {
                binding.loadingView.isVisible = it
            }
        }
        addPagingAdapterListener()
    }

    private fun addPagingAdapterListener() {
        adapter.withLoadStateFooter(MyPagingLoadStateAdapter(adapter::retry))
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    if (isLoadDataEnd) {
                        binding.loadingView.gone()
                    }
                }
                is LoadState.Error -> binding.loadingView.gone()
                is LoadState.Loading -> {
                }
            }
        }
    }

    override fun initData() {
        viewModel.getCoinRankRecord()
        isLoadDataEnd = true
    }


    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }
}