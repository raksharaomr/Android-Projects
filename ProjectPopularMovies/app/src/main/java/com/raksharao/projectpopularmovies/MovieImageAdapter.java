package com.raksharao.projectpopularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.raksharao.projectpopularmovies.models.MovieDetail;
import com.raksharao.projectpopularmovies.models.MovieResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by raksharaomr on 8/1/15.
 */
public class MovieImageAdapter extends BaseAdapter {

    private Context context;
    private List movieDetails;
    private boolean displayFavoriteMovies;

    public MovieImageAdapter(Context context, List movieDetails, boolean displayFavoriteMovies) {
        this.context = context;
        this.movieDetails = movieDetails;
        this.displayFavoriteMovies = displayFavoriteMovies;
    }

    public void updateMovieDetails(List movieDetails, boolean displayFavoriteMovies) {
        this.movieDetails.clear();
        if (displayFavoriteMovies) {

            this.movieDetails.addAll((List<MovieDetail>) movieDetails);
        } else {
            this.movieDetails.addAll((List<MovieResult>) movieDetails);
        }
        this.notifyDataSetChanged();
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

        String posterPath =
                (displayFavoriteMovies) ? ((MovieDetail) movieDetails.get(position)).getPosterPath()
                : ((MovieResult) movieDetails.get(position)).getPosterPath();
        String fullPath = "http://image.tmdb.org/t/p/" + "w500" + posterPath;

        Log.v("Adapter", fullPath);
        Picasso.with(context).load(fullPath).into(imageView);
        return gridView;
    }
}
