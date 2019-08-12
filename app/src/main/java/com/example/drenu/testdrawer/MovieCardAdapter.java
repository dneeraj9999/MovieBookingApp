package com.example.drenu.testdrawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import com.bumptech.glide.Glide;
import com.example.drenu.testdrawer.model.Movie;
import com.example.drenu.testdrawer.model.MovieCollection;
import com.squareup.picasso.Picasso;

import java.util.List;
import android.content.Context;

/**
 * Created by drenu on 9/18/2018.
 */
public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.MyViewHolder> {

    private Context mContext;
    private MovieCollection movieCollection;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public MovieCardAdapter(Context mContext, MovieCollection mc) {
        this.mContext = mContext;
        this.movieCollection = mc;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Movie movie = movieCollection.movies[position];
        holder.title.setText(movie.title);
        holder.count.setText(movie.slug);
       // Log.i("image url: ",movie.poster_image_thumbnail);
        //if(movie.poster_image_thumbnail !=null) {
         /*   Picasso.get()
                    .load(movie.poster_image_thumbnail)
                    .fit()
                    .into(holder.thumbnail);
       */ //}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=mContext.getSharedPreferences("com.example.drenu.testdrawer", Context.MODE_PRIVATE);
                String selectedMovie=holder.title.getText().toString();
                String movieslug=holder.count.getText().toString();
                sharedPreferences.edit().putString("movie",selectedMovie).apply();
                sharedPreferences.edit().putString("movieslug",movieslug).apply();
                Intent i=new Intent(mContext.getApplicationContext(),SelectTimeActivity.class);
                mContext.startActivity(i);
            }
        });

        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        /*holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
        */
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
   /* private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }
*/
    /**
     * Click listener for popup menu items
     */
/*    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
*/
    @Override
    public int getItemCount() {
        return 10;
    }
}