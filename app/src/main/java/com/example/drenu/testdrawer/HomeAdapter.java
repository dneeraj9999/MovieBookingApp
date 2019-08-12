package com.example.drenu.testdrawer;

import android.content.Context;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


/**
 * Created by drenu on 10/25/2018.
 */

public class HomeAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.pic_book,
            R.drawable.pic_prof,
            R.drawable.pip_bal,
            R.drawable.pic_ph,
    };

    // Constructor
    public HomeAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setMaxHeight(300);

        imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 300));
        return imageView;
    }

}