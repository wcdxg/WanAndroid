package com.yuaihen.wcdxg.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.databinding.DialogExitLoginBinding
import com.yuaihen.wcdxg.ui.activity.LoginActivity

/**
 * Created by Yuaihen.
 * on 2021/3/29
 */
class DialogUtil {

    /**
     * 注销当前用户
     */
    fun showExitLoginDialog(context: Context) {
        CustomDialog.show(object : OnBindView<CustomDialog>(R.layout.dialog_exit_login) {
            override fun onBind(dialog: CustomDialog, v: View) {
                dialog.setMaskColor(ContextCompat.getColor(context, R.color.dialog_mask_color))
                val binding = DialogExitLoginBinding.bind(v)
                binding.btnCancel.setOnClickListener { dialog.dismiss() }
                binding.btnConfirm.setOnClickListener {
                    //退回到登录页面
                    dialog.dismiss()
                    UserUtil.setUserIsLogin(false)
                    context.let {
                        (it as Activity?)?.finish()
                        it.startActivity(Intent(context, LoginActivity::class.java))
                    }
                }
            }

        })
    }
}