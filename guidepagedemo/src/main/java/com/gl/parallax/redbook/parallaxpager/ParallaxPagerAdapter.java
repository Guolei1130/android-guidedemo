package com.gl.parallax.redbook.parallaxpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
//import static android.view.ViewGroup.LayoutParams;
/**
 * Created by mac on 15-11-26.
 */
public class ParallaxPagerAdapter extends PagerAdapter{
    private int count = 0;
    private final Context context;
    private final LinkedList<View> recycleBin = new LinkedList<View>();

    public ParallaxPagerAdapter(Context context){
        this.context = context;
    }

    public void setCount(int count){
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view;
        if (!recycleBin.isEmpty()){
            view = recycleBin.pop();
        }else {
            view = new View(context);
            //这里注意，如果不想加前缀，请以静态的方式将包和属性导进来
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        View view = (View) object;
        container.removeView(view);
        recycleBin.push(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
