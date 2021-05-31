package com.yuaihen.wcdxg.ui.activity

import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import com.bigkoo.pickerview.R
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.isseiaoki.simplecropview.FreeCropImageView
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.yuaihen.wcdxg.base.BaseActivity
import com.yuaihen.wcdxg.databinding.ActivityEditUserInfoBinding
import com.yuaihen.wcdxg.ui.interf.OnTitleViewListener
import com.yuaihen.wcdxg.utils.LOGGER
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Yuaihen.
 * on 2021/3/29
 * 编辑用户个人资料
 */
class EditUserInfoActivity : BaseActivity() {

    companion object {
        const val IMAGE_PICKER = 100
    }

    private lateinit var binding: ActivityEditUserInfoBinding

    override fun getBindingView(): View {
        binding = ActivityEditUserInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initListener() {
//        binding.titleView.setTitleViewClickListener(object : OnTitleViewListener {
//            override fun onBackBtnClick() {
//                val intent = Intent()
//                intent.putExtra("url", "wcdxg.com")
//                setResult(RESULT_OK, intent)
//                finish()
//            }
//
//        })
        binding.btnSubmit.setOnClickListener {
            val intent = Intent()
            intent.putExtra("url", "wcdxg.com")
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.ivSelectBirthDate.setOnClickListener {
            initTimerPicker()
        }
        binding.llChangeHeadPhoto.setOnClickListener {
            openAlbumAndCrop()
        }
    }

    /**
     * 打开相册选择图片并裁剪
     */
    private fun openAlbumAndCrop() {
        ImagePicker.getInstance().apply {
            isCrop = true
            isMultiMode = false
            focusWidth = 400//裁剪框的宽度，仅对crop有效，对isFreeCrop无效
            focusHeight = 400
            isShowCamera = false
            selectLimit = 1
            style = CropImageView.Style.CIRCLE//仅对crop有效，对isFreeCrop无效
            setFreeCrop(false, FreeCropImageView.CropMode.FREE)
        }

        val intent = Intent(this, ImageGridActivity::class.java)
        startActivityForResult(intent, IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                val images =
                    data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>?
                logD("选择的照片个数= ${images?.size}  ${images?.get(0).toString()}")
                images?.let {
//                    binding.ivHead.setImageURI(images[0].uri)
                    ImagePicker.getInstance()
                        .imageLoader
                        .displayImage(
                            this,
                            it[0].uri,
                            binding.ivHead,
                            images[0].width,
                            images[0].height
                        )
                }

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 日期选择器
     */
    private fun initTimerPicker() {
        //时间选择器
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        startDate[1900, 0] = 1
//            endDate[2020, 11] = 31
        //Dialog 模式下，在底部弹出
        val pvTime = TimePickerBuilder(this) { date, _ ->
            logD(LOGGER, date.toString())
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE)
            val dateStr = df.format(date)
            logD(LOGGER, dateStr)
            binding.tvBirthDate.text = dateStr
        }
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
            .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
            .setLineSpacingMultiplier(2.0f)
            .setDate(selectedDate)//默认选择的时间
            .setRangDate(startDate, selectedDate)
            .isAlphaGradient(true)
            .setCancelText(getString(R.string.pickerview_cancel))
            .setSubmitText(getString(R.string.pickerview_submit))
            .build()

        // 去除设置的margin
        pvTime.dialogContainerLayout.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
        ).also {
            it.leftMargin = 0
            it.rightMargin = 0
        }
        //解决部分手机上dialog左右有间距，宽度不能铺满屏幕的问题
        pvTime.dialog.window?.let {
            val lp = it.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.attributes = lp
            it.setGravity(Gravity.BOTTOM)
            it.setWindowAnimations(R.style.picker_view_slide_anim)
        }

        pvTime.show()
    }


    override fun initData() {

    }
}