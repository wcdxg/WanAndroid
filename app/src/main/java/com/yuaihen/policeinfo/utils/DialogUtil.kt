package com.yuaihen.policeinfo.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.yuaihen.policeinfo.R
import com.yuaihen.policeinfo.databinding.DialogExitLoginBinding

/**
 * Created by Yuaihen.
 * on 2021/3/29
 */
class DialogUtil {

    fun showExitLoginDialog(context: Context) {
        CustomDialog.show(object : OnBindView<CustomDialog>(R.layout.dialog_exit_login) {
            override fun onBind(dialog: CustomDialog, v: View) {
                dialog.setMaskColor(ContextCompat.getColor(context, R.color.dialog_mask_color))
                val binding = DialogExitLoginBinding.bind(v)
                binding.btnCancel.setOnClickListener { dialog.dismiss() }
                binding.btnConfirm.setOnClickListener {
                    //退回到登录页面
                    dialog.dismiss()
                }
            }

        })
    }
}