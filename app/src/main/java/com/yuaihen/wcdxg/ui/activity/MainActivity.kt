package com.yuaihen.wcdxg.ui.activity

import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.gyf.immersionbar.ImmersionBar
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun getBindingView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        setupBottomNavigationBar()
        binding.bottomNavigationView.selectedItemId = R.id.wordBookFragment
    }

    override fun initListener() {

    }

    override fun initData() {

    }


    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this)
//            .statusBarColorTransform(android.R.color.white)
            .statusBarDarkFont(true)
//            .statusBarColor(R.color.white)
            .navigationBarAlpha(0f)
            .fullScreen(true)
            .barAlpha(0f)
            .init()
    }

    override fun onSupportNavigateUp(): Boolean {
        logD("onSupportNavigateUp")
        return navController.navigateUp()
    }

    private fun setupBottomNavigationBar() {
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        //BottomNavigationView点击事件
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            true
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            //跳转另外一个Fragment的时候弹出当前Fragment
            controller.popBackStack()
        }
    }

    private var isLastPage = false
    override fun onBackPressed() {
        logD("onBackPressed")
        if (!isLastPage) {
            isLastPage = true
            toast("再按一次退出智慧政工")
            lifecycleScope.launch {
                delay(2500)
                isLastPage = false
            }
        } else {
            super.onBackPressed()
        }
    }


    override fun onStop() {
        super.onStop()
        Log.d("hello", "onStop: ${isFinishing}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("hello", "onDestroy: ")
    }

}