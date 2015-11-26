package com.gl;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gl.animation.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;


public class NormalViewPager extends AppCompatActivity {

    private ViewPager viewPager;
    private List<ImageView> mDatas;
    private NormalPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FILL_PARENT,
//                WindowManager.LayoutParams.FILL_PARENT);
        setContentView(R.layout.activity_normal_view_pager);
        initView();
        initData();
        initAdapter();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.normalviewpager);
    }

    private void initData() {
        ImageView imageView_1 = new ImageView(this,null);
        imageView_1.setBackgroundResource(R.drawable.hpw1);
        ImageView imageView_2 = new ImageView(this);
        imageView_2.setBackgroundResource(R.drawable.hpw2);
        ImageView imageView_3 = new ImageView(this);
        imageView_3.setBackgroundResource(R.drawable.hpw3);
        ImageView imageView_4 = new ImageView(this);
        imageView_4.setBackgroundResource(R.drawable.hpw4);
        ImageView imageView_5 = new ImageView(this);
        imageView_5.setBackgroundResource(R.drawable.hpw5);
        mDatas = new ArrayList<>();
        mDatas.add(imageView_1);
        mDatas.add(imageView_2);
        mDatas.add(imageView_3);
        mDatas.add(imageView_4);
        mDatas.add(imageView_5);

    }

    private void initAdapter() {
        adapter = new NormalPagerAdapter(this,mDatas);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("tag","this is offset--->"+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
