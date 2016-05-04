package com.example.lecision.zoomeye;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    //  图片数组源
    private Integer[] imgs = { R.drawable.img1, R.drawable.img2,
            R.drawable.img3, R.drawable.img4};

    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    // 获取图片位置
    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    // 获取图片ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageview = new ImageView(mContext);

        imageview.setImageResource(imgs[position]);
        imageview.setLayoutParams(new Gallery.LayoutParams(1200, 800));      // 设置布局 图片120×120显示
        imageview.setScaleType(ImageView.ScaleType.CENTER);             // 设置显示比例类型（不缩放）
        return imageview;
    }
}