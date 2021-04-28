package com.yuaihen.wcdxg.common.imagePicker;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.yuaihen.wcdxg.R;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, Uri uri, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .asBitmap()
                .load(uri)
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_default_image)
//                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}
