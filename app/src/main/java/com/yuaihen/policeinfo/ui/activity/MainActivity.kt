package com.yuaihen.policeinfo.ui.activity

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.yuaihen.policeinfo.R
import com.yuaihen.policeinfo.base.BaseActivity
import com.yuaihen.policeinfo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun getBindingView(): View {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        setupBottomNavigationBar()
        binding.bottomNavigationView.selectedItemId = R.id.political_work_Fragment
    }

    override fun initListener() {

    }

    override fun initData() {

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
        } else {
            super.onBackPressed()
        }
    }


}