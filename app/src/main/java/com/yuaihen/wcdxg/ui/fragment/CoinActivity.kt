package com.yuaihen.wcdxg.ui.fragment

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
import com.yuaihen.wcdxg.databinding.ActivityCoinBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.CoinViewModel
import com.yuaihen.wcdxg.ui.activity.CoinRankActivity
import com.yuaihen.wcdxg.ui.adapter.CoinRecordAdapter
import com.yuaihen.wcdxg.utils.UserUtil
import com.yuaihen.wcdxg.utils.gone
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/17
 * 积分页面
 */
class CoinActivity : BaseActivity() {
    private lateinit var binding: ActivityCoinBinding
    private val viewModel by viewModels<CoinViewModel>()
    private val adapter by lazy { CoinRecordAdapter() }
    private var isLoadDataEnd = false

    override fun getBindingView(): View {
        binding = ActivityCoinBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CoinActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initListener() {
        super.initListener()
        binding.ivBack.setOnClickListener { finish() }
        binding.ivRank.setOnClickListener { CoinRankActivity.start(this) }
        binding.recycler.adapter = adapter

        viewModel.apply {
            errorLiveData.observe(this@CoinActivity) {
                toast(it)
            }
            coinRecordLiveData.observe(this@CoinActivity) {
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            }
            loadingLiveData.observe(this@CoinActivity) {
                binding.loadingView.isVisible = it
            }
        }
        addPagingAdapterListener()
    }

    private fun addPagingAdapterListener() {
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


    private var tempCoinCount = 0
    override fun initData() {
        setCoinCount()
        getCoinRecord()
    }

    private fun getCoinRecord() {
        viewModel.getCoinRecord()
        isLoadDataEnd = true
    }

    private fun setCoinCount() {
        val coinCount = UserUtil.getUserCoinCount()
        lifecycleScope.launch {
            while (tempCoinCount < coinCount) {
                tempCoinCount += 3
                binding.tvCoinCount.text = tempCoinCount.toString()
                delay(5)
            }
        }
        binding.tvCoinCount.text = coinCount.toString()
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }
}