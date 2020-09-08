package com.example.businessapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.Fragment.OrderFragment1;
import com.example.FragmentPagerAdapter.OrderFragmentPagerAdapter;
import com.example.FragmentPagerAdapter.OrderFragmentStatePagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private static OrderFragmentStatePagerAdapter orderFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order);
        tabLayout=findViewById(R.id.ordertable);
        viewPager=findViewById(R.id.orderVP);
        orderFragmentPagerAdapter=new OrderFragmentStatePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(orderFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        one=tabLayout.getTabAt(0);
        two=tabLayout.getTabAt(1);
        three=tabLayout.getTabAt(2);
        one.setText("未处理");
        two.setText("已处理");
        three.setText("已完成");
        ImageView manger=findViewById(R.id.mangers);
        manger.setOnClickListener(this);
        ImageView my=findViewById(R.id.my);
        my.setOnClickListener(this);
        /*tabLayout.addTab(one);
        tabLayout.addTab(two);
        tabLayout.addTab(three);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mangers:
                Intent tomanger=new Intent(MainOrderActivity.this,MainMangerActivity.class);
                startActivity(tomanger);
                break;

            case R.id.my:
                Intent tomy=new Intent(MainOrderActivity.this,MyActivity.class);
                startActivity(tomy);
                break;

                default:
                    break;
        }
    }



    public static void refreshF1(){
        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        orderFragmentPagerAdapter.notifyDataSetChanged();
        //transaction.replace(R.layout.fragment_order1, OrderFragment1.newInstance()).commit();
    }

}
