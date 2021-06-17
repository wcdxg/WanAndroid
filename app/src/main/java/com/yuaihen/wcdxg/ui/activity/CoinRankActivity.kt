package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityCoinRankBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.CoinViewModel

/**
 * Created by Yuaihen.
 * on 2021/6/17
 * 积分排行榜
 */
class CoinRankActivity : BaseActivity() {

    private lateinit var binding: ActivityCoinRankBinding
    private val viewModel by viewModels<CoinViewModel>()

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

    override fun initData() {

    }


}