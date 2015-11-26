package com.gl;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;



/**
 * Created by mac on 15-11-25.
 */
public class NormalPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> mDatas;

    public NormalPagerAdapter(Context context,List<ImageView> mDatas){
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((ImageView) object);
    }


    @Override
    public ImageView instantiateItem(ViewGroup container, int position) {
        container.addView(mDatas.get(position));
        return mDatas.get(position);
    }

}


