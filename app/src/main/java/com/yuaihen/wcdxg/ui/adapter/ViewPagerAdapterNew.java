package com.yuaihen.wcdxg.ui.adapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.yuaihen.wcdxg.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by hanjun
 * on 2019/4/13
 * ViewPager的Adapter 支持Fragment动态添加和销毁
 */
public class ViewPagerAdapterNew extends MyFragmentPagerAdapter {

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private List<Integer> mItemIdList = new ArrayList<>();
    private int id = 0;

    public ViewPagerAdapterNew(FragmentManager fm, @NonNull List<BaseFragment> fragmentList) {
        super(fm);
        for (BaseFragment fragment : fragmentList) {
            this.mFragmentList.add(fragment);
            mItemIdList.add(id++);
        }

    }

    public ViewPagerAdapterNew(FragmentManager fm) {
        super(fm);
    }

    public List<BaseFragment> getFragmentList() {
        return mFragmentList;
    }

    public void addPage(int index, BaseFragment fragment) {
        mFragmentList.add(index, fragment);
        mItemIdList.add(index, id++);
        notifyDataSetChanged();
    }

    public void addPage(BaseFragment fragment) {
        mFragmentList.add(fragment);
        mItemIdList.add(id++);
        notifyDataSetChanged();
    }

    public void delPage(int index) {
        mFragmentList.remove(index);
        mItemIdList.remove(index);
        notifyDataSetChanged();
    }

    public void delPage() {
        mFragmentList.clear();
        mItemIdList.clear();
        notifyDataSetChanged();
    }

    public void updatePage(List<BaseFragment> fragmentList) {
        mFragmentList.clear();
        mItemIdList.clear();

        for (int i = 0; i < fragmentList.size(); i++) {
            mFragmentList.add(fragmentList.get(i));
            mItemIdList.add(id++);//注意这里是id++，不是i++。
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * 返回值有三种，
     * POSITION_UNCHANGED  默认值，位置没有改变
     * POSITION_NONE       item已经不存在
     * position            item新的位置
     * 当position发生改变时这个方法应该返回改变后的位置，以便页面刷新。
     */
    @Override
    public int getItemPosition(Object object) {
        if (object instanceof BaseFragment) {
            if (mFragmentList.contains(object)) {
                return mFragmentList.indexOf(object);
            } else {
                return POSITION_NONE;
            }
        }
        return super.getItemPosition(object);
    }

    @Override
    public long getItemId(int position) {
        return mItemIdList.get(position);
    }

}
