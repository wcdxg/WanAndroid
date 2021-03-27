package com.yuaihen.policeinfo.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.yuaihen.policeinfo.base.BaseApplication;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Bruce.Zhou on 2019/4/18 09:22.
 * Email: 907160968@qq.com
 */
public class TextUtil {
    public static void setTextColor(TextView textView, int startIndex, int endIndex, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());

        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
//        ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
//        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);
//        ForegroundColorSpan greenSpan = new ForegroundColorSpan(Color.GREEN);
//        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(Color.YELLOW);

        builder.setSpan(redSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(whiteSpan, 1, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        builder.setSpan(blueSpan, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(greenSpan, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(yellowSpan, 4,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(builder);
    }

    /**
     * @param fileName assets文件名 a.txt
     * @return 返回读取的字符串
     */
    public static String readLocalJson(String fileName) {
        String resultString = "";
        InputStream inputStream = null;
        try {
            inputStream = BaseApplication.Companion.getContext().getResources().getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "UTF-8");
        } catch (Exception e) {
            Log.e("readLocalJson ", e.getLocalizedMessage());
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e("readLocalJson ", e.getLocalizedMessage());
            }
        }
        return resultString;
    }
}
