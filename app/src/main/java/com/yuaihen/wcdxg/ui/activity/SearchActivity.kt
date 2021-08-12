package com.yuaihen.wcdxg.ui.activity

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.blankj.utilcode.util.KeyboardUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivitySearchBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.SearchViewModel
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.ui.adapter.ArticleAdapter
import com.yuaihen.wcdxg.ui.adapter.FlexBoxAdapter
import com.yuaihen.wcdxg.ui.adapter.MyPagingLoadStateAdapter
import com.yuaihen.wcdxg.ui.interf.OnCollectClickListener
import com.yuaihen.wcdxg.ui.widget.EditTextWithClear
import com.yuaihen.wcdxg.utils.DialogUtil
import com.yuaihen.wcdxg.utils.SPUtils
import com.yuaihen.wcdxg.utils.gone
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/25
 * 搜索页面
 */
class SearchActivity : BaseActivity(), TextView.OnEditorActionListener,
    FlexBoxAdapter.OnItemClickListener,
    EditTextWithClear.OnClearClickListener {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private val dialogUtil by lazy { DialogUtil() }
    private var historyList = mutableListOf<String>()
    private val pagingAdapter by lazy { ArticleAdapter() }
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
        addPagingAdapterListener()
        binding.apply {
            searchRecycler.apply {
                adapter = pagingAdapter
            }

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
            editSearch.setOnClearClickListener(this@SearchActivity)
        }
        viewModel.apply {
            loadingLiveData.observe(this@SearchActivity) {
                binding.loadingView.isVisible = it
            }
            errorLiveData.observe(this@SearchActivity) {
                toast(it.errorMsg)
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
            searchArticleLiveData.observe(this@SearchActivity) {
                setArticleToAdapter(it)
            }
        }
    }

    private fun addPagingAdapterListener() {
        pagingAdapter.addOnCollectClickListener(object : OnCollectClickListener {
            override fun onCollect(id: Int) {
                viewModel.collectOrCancelArticle(id, true)
            }

            override fun unCollect(id: Int, originId: Int, position: Int) {
                viewModel.collectOrCancelArticle(id, false)
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
                        isLoadDataEnd = false
                    }
                }
                is LoadState.Error -> showEmptyView(true)
                is LoadState.Loading -> {
                }
            }
        }
    }

    private var isLoadDataEnd = false
    private fun setArticleToAdapter(pagingData: PagingData<ArticleModel>) {
        showSearchResultLayout(true)
        lifecycleScope.launch {
            pagingAdapter.submitData(pagingData)
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
        isLoadDataEnd = true
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
        getSearchResult(key)
        //加入到历史搜索中
        addSearchToHistory(key)
    }

    private fun getSearchResult(key: String) {
        viewModel.getSearchResult(key)
    }

    /**
     * 将搜索结果加入到历史记录
     */
    private fun addSearchToHistory(key: String) {
        if (historyList.contains(key)) {
            return
        }
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

    private fun showEmptyView(isVisible: Boolean) {
        binding.apply {
            loadingView.gone()
            emptyView.isVisible = isVisible
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }

    /**
     * 清空当前搜索的结果
     */
    override fun onClearClick() {
        showSearchResultLayout(false)
    }

    /**
     * 显示搜索结果RecyclerView
     */
    private fun showSearchResultLayout(isVisible: Boolean) {
        binding.searchRecycler.isVisible = isVisible
    }
}