package com.example.newblue.jumppart.bpart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

/**
 * ================================================
 * 作者: leilei
 * 版本: 1.0
 * 创建日期: 2017/12/15 15:55
 * 描述:
 * 修订历史:
 * ================================================
 * 查看图片
 */

public class SamplePagerAdapter extends PagerAdapter {
    private List<String> mList;

    public SamplePagerAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap bitmap = BitmapFactory.decodeFile(mList.get(position));
        photoView.setImageBitmap(bitmap);
        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
