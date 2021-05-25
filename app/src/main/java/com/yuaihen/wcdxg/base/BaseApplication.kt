package com.yuaihen.wcdxg.base

import android.app.Application
import android.content.Context
import com.kongzue.dialogx.DialogX
import com.lzy.imagepicker.ImagePicker
import com.tencent.bugly.Bugly
import com.tencent.mmkv.MMKV
import com.yuaihen.wcdxg.common.imagePicker.GlideImageLoader
import com.yuaihen.wcdxg.utils.AppUtil

/**
 * Created by Yuaihen.
 * on 2021/3/23
 */
class BaseApplication : Application() {

    companion object {
        private lateinit var context: Application
        fun getContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initLibrary()
    }

    /**
     * 初始化第三方库
     */
    private fun initLibrary() {
        MMKV.initialize(context)
        // 调试时，将第三个参数改为true输出日志
        Bugly.init(context, "117d8d9e5f", !AppUtil.isRelease())
        //DialogX
        DialogX.init(this)
        //设置主题样式
//        DialogX.globalStyle = MaterialStyle.style();
//        DialogX.globalTheme = DialogX.THEME.LIGHT
//        DialogX.cancelable = true
//        DialogX.backgroundColor = resources.getColor(R.color.dialog_mask_color)
        //ImagePicker
        ImagePicker.getInstance().imageLoader = GlideImageLoader()


    }
}