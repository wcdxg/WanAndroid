package com.yuaihen.wcdxg.ui.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentHomeBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.HomeViewModel
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.utils.GlideUtil

/**
 * Created by Yuaihen.
 * on 2021/4/30
 */
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        Log.d("hello", "getBindingView: HomeFragment创建")
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun initListener() {
        homeViewModel.loadingLiveData.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        homeViewModel.errorLiveData.observe(this) {
            toast(it)
        }
        homeViewModel.bannerLiveData.observe(this) {
            setBanner(it)
        }
    }

    override fun initData() {
        if (homeViewModel.bannerLiveData.value.isNullOrEmpty()) {
            homeViewModel.getBanner()
            homeViewModel.getArticle()
        } else {
            setHomePageData(homeViewModel.bannerLiveData.value!!)
        }
    }

    private fun setHomePageData(bannerData: List<BannerModel.Data>) {
        setBanner(bannerData)
        setArticleData()
    }

    private fun setArticleData() {

    }

    private fun setBanner(bannerModel: List<BannerModel.Data>) {
        binding.banner.apply {
            addBannerLifecycleObserver(this@HomeFragment)
            setIndicator(CircleIndicator(requireContext()))
            setIndicatorSelectedColor(getResources().getColor(R.color.bilibili_pink))
            setAdapter(object : BannerImageAdapter<BannerModel.Data>(bannerModel) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerModel.Data?,
                    position: Int,
                    size: Int
                ) {
                    holder?.imageView?.let { GlideUtil.showImageView(it, data?.imagePath) }
                }
            })
        }
    }

    override fun unBindView() {
        _binding = null
    }

}