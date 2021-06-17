package com.yuaihen.wcdxg.utils

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.yuaihen.wcdxg.R
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.CropSquareTransformation

/**
 * Created by Yuaihen.
 * on 2020/10/28
 */
object GlideUtil {


    /**
     * 最简单的加载图片
     */
    fun showImageView(imageView: ImageView, url: String? = "") {
        Glide.with(imageView.context)
            .asBitmap()
            .load(url)
            .placeholder(R.color.bitmap_place_hold)
            .into(imageView)
    }

    fun showImageView(imageView: ImageView, url: String? = "", showPlaceHolder: Boolean = false) {
        Glide.with(imageView.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    /**
     * 加载图片时有缩略图
     */
    fun showImageViewWithThumbnail(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            //用原图的1/10作为缩略图
            .thumbnail(0.1f)
            .into(imageView)
    }

    /**
     * 加载本地资源图
     */
    fun showImageView(imageView: ImageView, resId: Int) {
        Glide.with(imageView.context)
            .load(resId)
            .placeholder(R.color.bitmap_place_hold)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    /**
     * 当ImageView 为Wrap_content时,加载图片的的原始大小
     */
    fun showImageViewWithDefaultSize(imageView: ImageView, url: String?) {
        Log.d("Bitmap", url ?: "")
        Glide.with(imageView.context)
            .asBitmap()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.color.bitmap_place_hold)
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .into(imageView)
    }

    /**
     * 高斯模糊Bitmap
     */
    fun showImageViewBlur(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.color.bitmap_place_hold)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(BlurTransformation(25, 3))
            .into(imageView)
    }

    fun showImageViewBlur(imageView: ImageView, resId: Int) {
        Glide.with(imageView.context)
            .load(resId)
            .transform(BlurTransformation(5, 1))
            .into(imageView)
    }

    /**
     * 圆角显示Bitmap
     */
    fun showImageViewCircle(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.color.bitmap_place_hold)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CropCircleTransformation())
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView)
    }

    /**
     * 方形显示Bitmap
     */
    fun showImageViewSquare(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.color.bitmap_place_hold)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CropSquareTransformation())
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(imageView)
    }

    /**
     * 不使用缓存 适用于频繁刷新图片的情况
     */
    fun showImageViewNoCache(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.color.bitmap_place_hold)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}