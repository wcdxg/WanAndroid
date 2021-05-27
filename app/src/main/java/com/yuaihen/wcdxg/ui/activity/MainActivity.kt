package com.yuaihen.wcdxg.ui.activity

import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun getBindingView(): View? {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun initData() {
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        navController = Navigation.findNavController(this, R.id.fragment_container)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        //BottomNavigationView点击事件
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            if (binding.bottomNavigationView.selectedItemId == it.itemId) return@setOnNavigationItemSelectedListener true
            navController.navigate(it.itemId)
            true
        }
//        appBarConfiguration = AppBarConfiguration(
//            setOf(R.id.home, R.id.word, R.id.mine)
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            //跳转另外一个Fragment的时候弹出当前Fragment
//            controller.navigateUp()
//        }
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
            .statusBarColor(R.color.bilibili_pink)
            .init()
    }
}