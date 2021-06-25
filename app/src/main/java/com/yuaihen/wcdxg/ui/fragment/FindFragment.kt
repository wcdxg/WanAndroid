package com.yuaihen.wcdxg.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.databinding.FragmentFindBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.FindViewModel
import com.yuaihen.wcdxg.net.model.KnowLedgeTreeModel
import com.yuaihen.wcdxg.ui.activity.KnowledgeActivity
import com.yuaihen.wcdxg.ui.activity.ListViewActivity
import com.yuaihen.wcdxg.ui.activity.WebViewActivity
import com.yuaihen.wcdxg.ui.adapter.NavAdapter
import com.yuaihen.wcdxg.utils.gone

/**
 * Created by Yuaihen.
 * on 2021/6/21
 * 发现栏目下 通用Fragment
 */
class FindFragment : BaseFragment(), NavAdapter.OnItemClickListener {
    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!
    private var index = 0
    private val viewModel by viewModels<FindViewModel>()

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentFindBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        const val KNOWLEDGE_TREE = 0    //知识体系
        const val PAGE_NAV = 1          //页面导航
        const val OFFICIAL_ACCOUNTS = 2 //公众号
        const val PROJECT = 3           //项目

        fun newInstance(index: Int) = FindFragment().apply {
            arguments = Bundle().also {
                it.putInt("index", index)
            }
        }
    }

    override fun initListener() {
        super.initListener()
        viewModel.apply {
            loadingLiveData.observe(this@FindFragment) {
                binding.loadingView.isVisible = it
            }
            errorLiveData.observe(this@FindFragment) {
                binding.swipeRefresh.isRefreshing = false
                binding.loadingView.gone()
                toast(it)
            }
            knowledgeLiveData.observe(this@FindFragment) {
                setDataToAdapter(it)
            }
            navigationLiveData.observe(this@FindFragment) {
                setDataToAdapter(it)
            }
            officialAccountLiveData.observe(this@FindFragment) {
                setDataToAdapter(it)
            }
            projectTreeList.observe(this@FindFragment) {
                setDataToAdapter(it)
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            when (index) {
                KNOWLEDGE_TREE -> viewModel.getKnowledgeTree()
                PAGE_NAV -> viewModel.getNavPage()
                OFFICIAL_ACCOUNTS -> viewModel.getOfficialAccounts()
                PROJECT -> viewModel.getProjectTree()
            }
        }
    }

    private fun setDataToAdapter(list: List<Any>) {
        binding.swipeRefresh.isRefreshing = false
        mAdapter?.updateData(list)
    }

    private var mAdapter: NavAdapter? = null

    override fun lazyLoadData() {
        super.lazyLoadData()
        index = arguments?.getInt("index") ?: 0
        mAdapter = NavAdapter(index).apply {
            setOnItemClickListener(this@FindFragment)
        }
        binding.recyclerView.adapter = mAdapter
        getDataForIndex()
    }

    /**
     * 根据索引获取对应Tab的数据
     */
    private fun getDataForIndex() {
        when (index) {
            KNOWLEDGE_TREE -> viewModel.getKnowledgeTree()
            PAGE_NAV -> viewModel.getNavPage()
            OFFICIAL_ACCOUNTS -> viewModel.getOfficialAccounts()
            PROJECT -> viewModel.getProjectTree()
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

    /**
     * 导航item点击
     */
    override fun onNaviItemClick(link: String) {
        WebViewActivity.start(requireContext(), link)
    }

    /**
     * 微信公众号和项目列表item点击
     */
    override fun onWxItemClick(id: Int, name: String, loadType: Int) {
        val bundle = Bundle().apply {
            putInt(Constants.ID, id)
            putString(Constants.NAME, name)
            putInt(Constants.LOAD_TYPE, loadType)
        }
        ListViewActivity.start(requireContext(), bundle)
    }

    override fun unBindView() {
        _binding = null
    }


}