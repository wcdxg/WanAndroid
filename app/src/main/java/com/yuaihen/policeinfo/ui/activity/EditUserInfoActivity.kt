package com.yuaihen.policeinfo.ui.activity

import android.content.Intent
import android.view.View
import com.yuaihen.policeinfo.base.BaseActivity
import com.yuaihen.policeinfo.databinding.ActivityEditUserInfoBinding
import com.yuaihen.policeinfo.ui.interf.OnTitleViewListener

/**
 * Created by Yuaihen.
 * on 2021/3/29
 * 编辑用户个人资料
 */
class EditUserInfoActivity : BaseActivity() {

    private lateinit var binding: ActivityEditUserInfoBinding

    override fun getBindingView(): View {
        binding = ActivityEditUserInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
        binding.titleView.setTitleViewClickListener(object : OnTitleViewListener {
            override fun onBackBtnClick() {
                val intent = Intent()
                intent.putExtra("url", "wcdxg.com")
                setResult(RESULT_OK, intent)
                finish()
            }

        })
    }

    override fun initData() {

    }
}