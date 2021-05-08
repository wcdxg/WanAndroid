package com.yuaihen.wcdxg.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.yuaihen.wcdxg.base.BaseApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
     * @param assetFileName assets文件名 a.txt
     * @return 返回读取的字符串
     */
    public static String readJsonFromAsset(String assetFileName) {
        String resultString = "";
        InputStream inputStream = null;
        try {
            inputStream = BaseApplication.Companion.getContext().getResources().getAssets().open(assetFileName);
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

    /**
     * 从网络上获取json转换成String
     *
     * @param jsonUrl url地址全路径 https://xxx.com/menu.txt
     */
    public static String readJsonFromUrl(String jsonUrl) {
        String result = "";
        try {
            URL url = new URL(jsonUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("无法连接到服务器");
            } else {
                int fileSize = urlConnection.getContentLength();
                InputStreamReader isReader = new InputStreamReader(urlConnection.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(isReader);
                StringBuilder strBuilder = new StringBuilder();
                String line;
                line = reader.readLine();
                while (line != null) {
                    strBuilder.append(line);
                    //添加换行符
                    strBuilder.append(" ");
                    //读取下一行
                    line = reader.readLine();
                }

                isReader.close();
                reader.close();
                System.out.println(strBuilder.toString());
                result = strBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
