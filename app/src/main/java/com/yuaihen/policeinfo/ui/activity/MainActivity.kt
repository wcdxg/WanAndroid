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
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun initListener() {
        //BottomNavigationView点击事件
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            navController.navigate(it.itemId)
            false
        }
    }

    override fun initData() {

    }
}