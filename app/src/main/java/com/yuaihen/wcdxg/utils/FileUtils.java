package com.yuaihen.wcdxg.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.yuaihen.wcdxg.base.Constants;
import com.yuaihen.wcdxg.ui.interf.DownloadCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }


    /**
     * 将输入流inputStream写入到文件中
     *
     * @return 写入是否成功
     */
    public static boolean writeFile(java.io.InputStream inputStream, File file, long contentLength, DownloadCallback callback) {
        if (file.exists()) {
            file.delete();
        }
        try {
            //文件大小
            LogUtils.e(TAG, "api contentLength= " + contentLength);
            //读取文件
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[2048];
            int len = 0;
            int currLength = 0;
            //循环读取文件的内容，把他放到新的文件目录里面
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                currLength += len;
                //获取下载进度
                int progress = (int) (currLength * 100 / contentLength);
                android.util.Log.e(TAG, "writeFile: =====>> " + progress);
                //发送进度
                if (callback != null) {
                    callback.onProgress(progress);
                }
            }
            //下载完成后 更新进度
            fos.close();
            inputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onFail(e.getLocalizedMessage(), -1);
            }
        }
        return false;
    }


    /**
     * 生成缩略图路径
     */
    public static String getThumbnailPath() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "mnote_thumb");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                LogUtil.INSTANCE.d("MyCameraApp", "failed to create directory");
                return null;
            } else {
                mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "mnote_thumb");
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "thumb_" + timeStamp + ".jpg");
        return mediaFile.getAbsolutePath();
    }


    /**
     * 判断文件是否存在
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        return new File(filePath).exists();
    }


    /**
     * 获取URL对应文件名称 最后一个斜杠后面的名称
     * /.jpg
     */
    public static String getFileName(String url) {
        if (!TextUtils.isEmpty(url)) {
            int nameIndex = url.lastIndexOf("/");
            return url.substring(nameIndex + 1);
        }
        return "";
    }

    /**
     * 获取文件类型对应的缓存目录
     */
    public static String getFileTypePath(String fileName) {
        String path = "";
        if (fileName.endsWith("jpg") || fileName.endsWith("png") ||
                fileName.endsWith("gif") || fileName.endsWith("jpeg") || fileName.endsWith("webp")) {
            path = Constants.IMAGE_CACHE_DIRECTORY;
        } else {
            path = Constants.FILE_CACHE_DIRECTORY;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }


    /**
     * 获取图片缓存目录
     */
    public static String getImageCachePath() {
        String path = Constants.IMAGE_CACHE_DIRECTORY;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    /**
     * 清空图片缓存目录
     */
    public static void clearImageCacheFiles() {
        String localDirPath = Constants.IMAGE_CACHE_DIRECTORY;
        File localDir = new File(localDirPath);
        if (localDir.exists()) {
            for (File file : localDir.listFiles()) {
                file.delete();
            }
        }
    }

    /**
     * @return 获取缓存目录
     */
    public static File getCacheFolder() {
        File file = new File(Constants.NET_CACHE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

}
