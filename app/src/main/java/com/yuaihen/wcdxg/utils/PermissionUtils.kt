package com.yuaihen.wcdxg.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.request.ExplainScope
import com.permissionx.guolindev.request.ForwardScope
import com.yuaihen.wcdxg.R

/**
 * Created by Yuaihen.
 * on 2021/5/27
 */
object PermissionUtils {

    private const val TAG = "PermissionUtils"

    private val permissionList = mutableListOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    fun requestAppPermission(
        activity: FragmentActivity,
        listener: (Boolean, List<String?>) -> Unit
    ) {
        PermissionX.init(activity)
            .permissions(permissionList)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope: ExplainScope, deniedList: List<String?>? ->
                scope.showRequestReasonDialog(
                    deniedList,
                    activity.getString(R.string.permission_explain_reason),
                    "确定",
                    "取消"
                )
            }
            .onForwardToSettings { scope: ForwardScope, deniedList: List<String?>? ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    activity.getString(R.string.permission_forward_desc),
                    "确定",
                    "取消"
                )
            }
            .request { allGranted: Boolean, grantedList: List<String?>?, deniedList: List<String?> ->
//                if (allGranted) {
//                    listener()
//                    LogUtil.d(TAG, "所有申请的权限都已通过")
//                } else {
//                    toast("您拒绝了如下权限：$deniedList")
//                }
                listener(allGranted, deniedList)
            }
    }

    fun requestAppPermission(fragment: Fragment, listener: (Boolean, List<String?>) -> Unit) {
        PermissionX.init(fragment)
            .permissions(permissionList)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope: ExplainScope, deniedList: List<String?>? ->
                scope.showRequestReasonDialog(
                    deniedList,
                    fragment.getString(R.string.permission_explain_reason),
                    "确定",
                    "取消"
                )
            }
            .onForwardToSettings { scope: ForwardScope, deniedList: List<String?>? ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    fragment.getString(R.string.permission_forward_desc),
                    "确定",
                    "取消"
                )
            }
            .request { allGranted: Boolean, grantedList: List<String?>?, deniedList: List<String?> ->
//                if (allGranted) {
//                    LogUtil.d(TAG, "所有申请的权限都已通过")
//                } else {
//                    toast("您拒绝了如下权限：$deniedList")
//                }
                listener(allGranted, deniedList)
            }
    }


}