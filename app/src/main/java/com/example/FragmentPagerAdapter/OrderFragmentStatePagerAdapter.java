package com.example.FragmentPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.Fragment.OrderFragment1;
import com.example.Fragment.OrderFragment2;
import com.example.Fragment.OrderFragment3;

public class OrderFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles = new String[]{"未处理", "已处理", "已完成"};
    private FragmentManager fm;

    @Override
    public Fragment getItem(int position) {
        if (position == 1)
            return new OrderFragment2(fm);
        else if (position == 2)
            return new OrderFragment3(fm);
        return new OrderFragment1(fm);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    public CharSequence getPageTitle(int position) {
        //返回tab选项的名字
        return mTitles[position];
    }

    public OrderFragmentStatePagerAdapter(FragmentManager fm){
        super(fm);
        this.fm=fm;
    }
    public int getItemPosition(@NonNull Object object) {
        // 否则返回状态值 POSITION_NONE
        return POSITION_NONE;
    }
}
