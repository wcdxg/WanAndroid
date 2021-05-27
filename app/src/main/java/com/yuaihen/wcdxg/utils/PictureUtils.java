package com.yuaihen.wcdxg.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureUtils {
    public static final int REQUEST_CODE_IMAGE = 0x100; // 	请求相册
    public static final int REQUEST_CODE_CAMERA = 0x101; // 请求相机

    /**
     * @param context
     * @deprecated 打开相册选择图片
     * if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK) {
     * Uri uri = data.getData();
     * Cursor cursor = getContentResolver().query(uri, null, null, null,null);
     * if (cursor != null && cursor.moveToFirst()) {
     * String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
     * }
     */
    public static void pickPicutre(AppCompatActivity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    /**
     * 打开相机，拍照后将照片保存到指定路径
     *
     * @param context
     * @param targetPath
     */
    public static void openCamera(AppCompatActivity context, String targetPath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //path为保存图片的路径，执行完拍照以后能保存到指定的路径下
        File file = new File(targetPath);
        Uri imageUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 打开相机，拍照后，相片数据手动解析
     * if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
     * Bundle bundle = data.getExtras();
     * // 获取相机返回的数据，并转换为Bitmap图片格式 ，这是缩略图
     * Bitmap bitmap = (Bitmap) bundle.get("data");
     * }
     *
     * @param context
     */
    public static void openCamera(AppCompatActivity context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    // ---------------------------------------- 压缩图片 ------------------------------
    private static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {    //循环判断如果压缩后图片是否大于200kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 5;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    // ---------------------------------------- 压缩图片 ------------------------------
    public static String compressAndReturnPath(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) {    //循环判断如果压缩后图片是否大于300kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        String outPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/faccpptest/";
        File outDir = new File(outPath);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File target = new File(outDir, "compress.jpg");
        BufferedOutputStream bos = null;
        int len = 0;
        byte[] buffer = new byte[1024];
        try {
            bos = new BufferedOutputStream(new FileOutputStream(target));
            while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, len);
            }
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                bis.close();
            }
            if (baos != null) {
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target.getAbsolutePath();
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        //		System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    public static String getPicturePath() {
        String tempImgPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        return tempImgPath;
    }

    /**
     * 获取本地图片宽高
     *
     * @param imgPath
     * @return
     */
    public static int[] getImgWidthHeight(String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);
        int width = options.outWidth;
        int height = options.outHeight;
        return new int[]{width, height};
    }


    /**
     * 根据原图和变长绘制圆形图片
     *
     * @param source
     * @param min
     * @return
     */
    public static Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 根据原图添加圆角
     *
     * @param source
     * @return
     */
    public static Bitmap createRoundConerImage(Bitmap source, int sideLength, float radius) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(sideLength, sideLength, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

}
