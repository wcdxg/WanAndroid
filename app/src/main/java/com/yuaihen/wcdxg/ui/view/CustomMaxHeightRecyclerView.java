package com.yuaihen.wcdxg.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yuaihen.wcdxg.R;


/**
 * Created by Yuaihen.
 * on 2020/9/21
 * 支持设置maxHeight的RecyclerView
 */
public class CustomMaxHeightRecyclerView extends RecyclerView {

    private int mMaxHeight;
    private int heightMeasureSpec;

    public CustomMaxHeightRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomMaxHeightRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomMaxHeightRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView);
        mMaxHeight = typedArray.getLayoutDimension(R.styleable.MaxHeightRecyclerView_maxHeight, mMaxHeight);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (mMaxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        }

        super.onMeasure(widthSpec, heightMeasureSpec);
    }
}
