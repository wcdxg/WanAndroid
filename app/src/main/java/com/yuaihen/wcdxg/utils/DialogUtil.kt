package com.yuaihen.wcdxg.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.kongzue.dialogx.dialogs.CustomDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.yuaihen.wcdxg.R
import com.yuaihen.wcdxg.databinding.DialogMessageTipBinding


/**
 * Created by Yuaihen.
 * on 2021/3/29
 */
class DialogUtil {

    /**
     * 注销当前用户
     * @param block 调用退出登录接口
     */
    fun showExitLoginDialog(context: Context, listener: () -> Unit) {
        CustomDialog.show(object : OnBindView<CustomDialog>(R.layout.dialog_message_tip) {
            override fun onBind(dialog: CustomDialog, v: View) {
                dialog.setMaskColor(ContextCompat.getColor(context, R.color.dialog_mask_color))
                val binding = DialogMessageTipBinding.bind(v)
                binding.apply {
                    tvTitle.text = context.getString(R.string.exit_login_hint)
                    tvCancel.setOnClickListener { dialog.dismiss() }
                    tvConfirm.setOnClickListener {
                        dialog.dismiss()
                        listener()
                    }
                }
            }
        })
    }

    /**
     * 清除缓存
     */
    fun clearCacheDialog(context: Context, dialogTitle: String? = null, listener: () -> Unit) {
        CustomDialog.show(object : OnBindView<CustomDialog>(R.layout.dialog_message_tip) {
            override fun onBind(dialog: CustomDialog, v: View) {
                dialog.setMaskColor(ContextCompat.getColor(context, R.color.dialog_mask_color))
                val binding = DialogMessageTipBinding.bind(v)
                binding.apply {
                    dialogTitle?.let {
                        tvTitle.text = it
                    }
                    tvCancel.setOnClickListener { dialog.dismiss() }
                    tvConfirm.setOnClickListener {
                        dialog.dismiss()
                        listener()
                    }
                }
            }
        })
    }
}