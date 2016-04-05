package com.raksharao.projectpopularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.raksharao.projectpopularmovies.models.MovieDetail;
import com.raksharao.projectpopularmovies.models.MovieReview;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by raksharaomr on 4/4/16.
 */
public class FavoriteMovieAdapter extends BaseAdapter {
    private Context context;
    private List<MovieDetail> movieDetails;

    public FavoriteMovieAdapter(Context context, List<MovieDetail> movieDetails) {
        this.context = context;
        this.movieDetails = movieDetails;
    }


    @Override
    public int getCount() {
        return movieDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return movieDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateReviewsList(List<MovieDetail> movieDetails) {

        this.movieDetails.clear();
        this.movieDetails.addAll(movieDetails);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_view_item, null);
        } else {
            gridView = (View) convertView;
        }
        ImageView imageView = (ImageView) gridView.findViewById(R.id.gv_thumbnail_image_view);

        String fullPath = "http://image.tmdb.org/t/p/" + "w500" + movieDetails.get(position).getPosterPath();
        Log.v("Adapter", fullPath);
        Picasso.with(context).load(fullPath).into(imageView);
        return gridView;
    }
}
