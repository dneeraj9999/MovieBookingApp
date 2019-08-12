package com.example.drenu.testdrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drenu.testdrawer.model.CinemaCollection;
import com.example.drenu.testdrawer.model.MovieCollection;
import com.squareup.picasso.Picasso;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by drenu on 9/30/2018.
 */

public class MovieGridAdapter extends BaseAdapter {

    private Context context;
    private MovieCollection movieCollection;

    public MovieGridAdapter(Context context, MovieCollection movieCollection)
    {
        this.movieCollection=movieCollection;
        this.context=context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return movieCollection.movies[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        v= LayoutInflater.from(context).inflate(R.layout.moviegrid_row,null);

        TextView tv=v.findViewById(R.id.title);
        tv.setText(movieCollection.movies[i].title);

        ImageView iv=v.findViewById(R.id.movieImage);

         if(movieCollection.movies[i].poster_image_thumbnail !=null) {
             Log.i("image url: ",movieCollection.movies[i].poster_image_thumbnail);

             Picasso.get()
                     .load(movieCollection.movies[i].poster_image_thumbnail)
                     .fit()
                     .into(iv);


         }

        return v;
    }


}
