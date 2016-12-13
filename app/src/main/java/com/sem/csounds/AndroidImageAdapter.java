package com.sem.csounds;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Slaton on 12/12/2016.
 */

public class AndroidImageAdapter extends PagerAdapter {
    Context mContext;

    private int[] sliderImagesIds;

    AndroidImageAdapter(Context context, int[] imageArray){
        this.mContext = context;
        this.sliderImagesIds = imageArray;
    }

    @Override
    public int getCount(){
        return sliderImagesIds.length;
    }

    @Override
    public boolean isViewFromObject(View v, Object obj){
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i){
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImageView.setImageResource(sliderImagesIds[i]);
        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj){
        ((ViewPager) container).removeView((ImageView) obj);
    }



}
