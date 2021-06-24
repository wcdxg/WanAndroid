package com.yuaihen.wcdxg.ui.activity

import android.view.MenuItem
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityMainBinding
import com.yuaihen.wcdxg.ui.adapter.MyViewPagerAdapter
import com.yuaihen.wcdxg.ui.fragment.HomeFragment
import com.yuaihen.wcdxg.ui.fragment.MineFragment
import com.yuaihen.wcdxg.ui.fragment.NavFragment
import com.yuaihen.wcdxg.ui.fragment.WenDaFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private var menuItem: MenuItem? = null
    private val fragmentList = mutableListOf<Fragment>().apply {
        add(HomeFragment())
        add(WenDaFragment())
        add(NavFragment())
        add(MineFragment())
    }

    override fun getBindingView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        super.initListener()
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                val selectID = binding.bottomNavigationView.menu[position].itemId
                binding.bottomNavigationView.selectedItemId = selectID
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun initData() {
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = MyViewPagerAdapter(
                supportFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            ).also {
                it.addFragmentList(fragmentList)
            }
            offscreenPageLimit = 4
        }

        //BottomNavigationView点击事件
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            menuItem = it
            if (binding.bottomNavigationView.selectedItemId == it.itemId) return@setOnNavigationItemSelectedListener true
            when (it.itemId) {
                R.id.nav_home -> switchVPCurrentItem(0)
                R.id.nav_qa -> switchVPCurrentItem(1)
                R.id.nav_nav -> switchVPCurrentItem(2)
                R.id.nav_mine -> switchVPCurrentItem(3)
            }
            true
        }
    }

    private fun switchVPCurrentItem(index: Int) {
        binding.viewPager.setCurrentItem(index, true)
    }

    private var isLastPage = false
    override fun onBackPressed() {
        logD("onBackPressed")
        if (!isLastPage) {
            isLastPage = true
            toast(R.string.exit_tip)
            lifecycleScope.launch {
                delay(2500)
                isLastPage = false
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
            .statusBarColor(R.color.bili_bili_pink)
            .init()
    }

}