package com.yuaihen.wcdxg.ui.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuaihen.wcdxg.base.BaseFragment
import com.yuaihen.wcdxg.databinding.FragmentWordBookBinding
import com.yuaihen.wcdxg.mvvm.viewmodel.WordBookViewModel
import com.yuaihen.wcdxg.utils.TextUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Created by Yuaihen.
 * on 2021/4/30
 */
class WordBookFragment : BaseFragment() {

    private var _binding: FragmentWordBookBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WordBookViewModel>()

    override fun getBindingView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentWordBookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initListener() {

    }


    lateinit var job: Job
    override fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result =
                TextUtil.readJsonFromUrl("https://gitee.com/yuaihen/blog-img/raw/master/menu.txt")
            Log.d("hello", "initData: $result")
            val menuList = mutableListOf<String>()
            result?.let {
                menuList.addAll(it.split(","))
            }

            //循环滚动展示菜单名称
            job = lifecycleScope.launch(Dispatchers.Main) {
                while (true) {
                    delay(50)
                    binding.tvMenu.text = menuList[Random.nextInt(menuList.size)]
                }
            }
        }


        binding.btnStart.setOnClickListener {
            job?.cancel()
        }
    }

    override fun unBindView() {
        _binding = null
    }
}