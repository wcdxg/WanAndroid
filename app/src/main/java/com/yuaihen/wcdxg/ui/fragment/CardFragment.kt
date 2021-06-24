package com.yuaihen.wcdxg.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.databinding.FragmentCardBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.CardViewModel
import com.yuaihen.wcdxg.net.model.KnowLedgeTreeModel
import com.yuaihen.wcdxg.ui.activity.KnowledgeActivity
import com.yuaihen.wcdxg.ui.adapter.NavAdapter
import com.yuaihen.wcdxg.utils.gone

/**
 * Created by Yuaihen.
 * on 2021/6/21
 * 发现栏目下 通用Fragment
 */
class CardFragment : BaseFragment(), NavAdapter.OnItemClickListener {
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
    private var index = 0
    private val viewModel by viewModels<CardViewModel>()

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentCardBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        const val KNOWLEDGE_TREE = 0    //知识体系
        const val PAGE_NAV = 1          //页面导航
        const val OFFICIAL_ACCOUNTS = 2 //公众号
        const val PROJECT = 3           //项目

        fun newInstance(index: Int) = CardFragment().apply {
            arguments = Bundle().also {
                it.putInt("index", index)
            }
        }
    }

    override fun initListener() {
        super.initListener()
        viewModel.apply {
            loadingLiveData.observe(this@CardFragment) {
                binding.loadingView.isVisible = it
            }
            errorLiveData.observe(this@CardFragment) {
                binding.loadingView.gone()
                toast(it)
            }

            knowledgeLiveData.observe(this@CardFragment) {
                mAdapter?.updateData(it)
            }
        }
    }

    private var mAdapter: NavAdapter? = null

    override fun lazyLoadData() {
        super.lazyLoadData()
        index = arguments?.getInt("index") ?: 0
        mAdapter = NavAdapter(index).apply {
            setOnItemClickListener(this@CardFragment)
        }
        binding.recyclerView.adapter = mAdapter

        getDataForIndex()
    }

    private fun getDataForIndex() {
        when (index) {
            KNOWLEDGE_TREE -> viewModel.getKnowledgeTree()
            PAGE_NAV -> viewModel.getNavPage()
            OFFICIAL_ACCOUNTS -> viewModel.getOfficialAccounts()
            PROJECT -> viewModel.getProject()
        }
    }


    /**
     * 体系item点击
     */
    override fun onItemClick(data: KnowLedgeTreeModel.Data, position: Int) {
        if (index == KNOWLEDGE_TREE) {
            val bundle = Bundle().apply {
                putParcelable(Constants.KNOWLEDGE_LABEL, data)
                putInt(Constants.POSITION, position)
            }
            KnowledgeActivity.start(requireContext(), bundle)
        }
    }

    override fun unBindView() {
        _binding = null
    }


}