package com.yuaihen.wcdxg.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuaihen.wcdxg.R;
import com.yuaihen.wcdxg.base.BaseApplication;

public class ToastUtil {
    private static final String TAG = "ToastUtil";

    private static Toast toast;
    private static TextView mTvMsg;

    public static void show(String msg) {
        try {
            if (toast != null) {
                toast.setText(msg);
            } else {
                toast = Toast.makeText(BaseApplication.Companion.getContext(), msg, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            LogUtil.INSTANCE.d(TAG, e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage());
        }
    }

    public static void showLongTime(String msg) {
        try {
            if (toast != null) {
                toast.setText(msg);
            } else {
                toast = Toast.makeText(BaseApplication.Companion.getContext(), msg, Toast.LENGTH_LONG);
            }
            toast.show();
        } catch (Exception e) {
            LogUtil.INSTANCE.d(TAG, e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage());
        }

    }


    public static void show(int resId) {
        try {
            if (toast != null) {
                toast.setText(resId);
            } else {
                toast = Toast.makeText(BaseApplication.Companion.getContext(), resId, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            LogUtil.INSTANCE.d(TAG, e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage());
        }
    }


    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    public static void showBigMsg(String message) {
        try {
            if (toast == null) {
                View view = View.inflate(BaseApplication.Companion.getContext(), R.layout.layout_toast, null);
                mTvMsg = view.findViewById(R.id.tv_msg);
                toast = new Toast(BaseApplication.Companion.getContext());
                //setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
                toast.setGravity(Gravity.CENTER, 0, 0);
                //setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
                //            sBigToast.setDuration(Toast.LENGTH_LONG);
                toast.setView(view);
            }

            if (!TextUtils.isEmpty(message)) {
                mTvMsg.setText(message);
                toast.show();
            }

        } catch (Exception e) {
            LogUtil.INSTANCE.d(TAG, e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage());
        }
    }

    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    public static void showBigMsg(String message, boolean isLongTime) {
        try {
            if (toast == null) {
                View view = View.inflate(BaseApplication.Companion.getContext(), R.layout.layout_toast, null);
                mTvMsg = view.findViewById(R.id.tv_msg);
                toast = new Toast(BaseApplication.Companion.getContext());
                //setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
                toast.setGravity(Gravity.CENTER, 0, 0);
                //setDuration方法：设置持续时间，
                // 以毫秒为单位。该方法是设置补间动画时间长度的主要方法
                if (isLongTime) {
                    toast.setDuration(Toast.LENGTH_LONG);
                }
                toast.setView(view);
            }

            if (!TextUtils.isEmpty(message)) {
                mTvMsg.setText(message);
                toast.show();
            }

        } catch (Exception e) {
            LogUtil.INSTANCE.d(TAG, e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage());
        }
    }
}
