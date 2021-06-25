package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.blankj.utilcode.util.KeyboardUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivitySearchBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.SearchViewModel
import com.yuaihen.wcdxg.ui.adapter.FlexBoxAdapter
import com.yuaihen.wcdxg.utils.DialogUtil
import com.yuaihen.wcdxg.utils.SPUtils

/**
 * Created by Yuaihen.
 * on 2021/6/25
 * 搜索页面
 */
class SearchActivity : BaseActivity(), TextView.OnEditorActionListener,
    FlexBoxAdapter.OnItemClickListener {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private val dialogUtil by lazy { DialogUtil() }
    private var historyList = mutableListOf<String>()
    private var hotSearchAdapter: FlexBoxAdapter? = null
    private var historySearchAdapter: FlexBoxAdapter? = null

    override fun getBindingView(): View {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        binding.data = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initListener() {
        super.initListener()
        Log.d("hello", "initListener: ${viewModel.isExpandLiveData.value}")

        binding.apply {
            ivBack.setOnClickListener {
                KeyboardUtils.hideSoftInput(this@SearchActivity)
                finish()
            }
            editSearch.setOnEditorActionListener(this@SearchActivity)

            tvClearHistory.setOnClickListener {
                dialogUtil.clearCacheDialog(
                    this@SearchActivity,
                    getString(R.string.clear_history_confirm)
                ) {
                    historyList.clear()
                    SPUtils.clearHistorySearch()
                    viewModel.setHistorySearchCount(0)
                }
            }
            ivExpand.setOnClickListener {
                viewModel.isExpandLiveData.value?.let {
                    viewModel.setExpand(!it)
                }
            }
        }
        viewModel.apply {
            loadingLiveData.observe(this@SearchActivity) {
                binding.loadingView.isVisible = it
            }
            errorLiveData.observe(this@SearchActivity) {
                toast(it)
            }
            hotSearchLiveData.observe(this@SearchActivity) { list ->
                val nameList = mutableListOf<String>()
                list.forEach {
                    nameList.add(it.name)
                }
                initHotSearchRecycler(nameList)
            }
            isExpandLiveData.observe(this@SearchActivity) {
                if (it) {
                    binding.ivExpand.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                } else {
                    binding.ivExpand.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                }
            }
        }

    }

    override fun initData() {
        //热门搜索记录
        getHotSearchData()
        //历史搜索记录
        setHistorySearchData()
    }

    private fun getHotSearchData() {
        viewModel.getHotSearchList()
    }

    /**
     * 设置热门搜索记录
     */
    private fun initHotSearchRecycler(nameList: MutableList<String>) {
        hotSearchAdapter =
            FlexBoxAdapter().apply { setOnItemClickListener(this@SearchActivity) }
        binding.hotSearchRecycler.apply {
            layoutManager =
                FlexboxLayoutManager(this@SearchActivity, FlexDirection.ROW, FlexWrap.WRAP)
            adapter = hotSearchAdapter
        }

        hotSearchAdapter?.setNewData(nameList)
    }

    /**
     * 设置历史搜索记录
     */
    private fun setHistorySearchData() {
        val list = SPUtils.getHistorySearchList()
        historyList.addAll(list)
        viewModel.setHistorySearchCount(historyList.size)
        historySearchAdapter =
            FlexBoxAdapter().apply { setOnItemClickListener(this@SearchActivity) }
        binding.historySearchRecycler.apply {
            layoutManager = FlexboxLayoutManager(this@SearchActivity)
            adapter = historySearchAdapter
        }
        historySearchAdapter?.setNewData(historyList)
    }

    private fun search(key: String) {
        toast(key)
        //加入到历史搜索中
        addSearchToHistory(key)
    }

    /**
     * 将搜索结果加入到历史记录
     */
    private fun addSearchToHistory(key: String) {
        historyList.add(0, key)
        historySearchAdapter?.setNewData(historyList)
        viewModel.setHistorySearchCount(historyList.size)
        SPUtils.addHistorySearch(historyList)

        KeyboardUtils.hideSoftInput(this@SearchActivity)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val key = binding.editSearch.text.toString()
            search(key)
            return true
        }
        return false
    }


    override fun onFlexItemClick(name: String, position: Int) {
        binding.editSearch.setText(name)
        search(name)
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }
}