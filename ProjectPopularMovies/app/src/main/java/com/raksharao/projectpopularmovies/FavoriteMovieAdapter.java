package com.raksharao.projectpopularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.raksharao.projectpopularmovies.models.MovieReview;

import java.util.List;

/**
 * Created by raksharaomr on 4/4/16.
 */
public class FavoriteMovieAdapter extends BaseAdapter {
    private Context context;
    private List<MovieReview.Result> reviews;

    public ReviewItemAdapter(Context context, List<MovieReview.Result> reviews) {
        this.context = context;
        this.reviews = reviews;
    }


    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateReviewsList(List<MovieReview.Result> reviews) {

        this.reviews.clear();
        this.reviews.addAll(reviews);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View reviewListView;
        if (convertView == null) {
            reviewListView = inflater.inflate(R.layout.review_item, null);
        } else {
            reviewListView = (View) convertView;
        }

        TextView authorName = (TextView) reviewListView.findViewById(R.id.tv_author_name);
        authorName.setText("Author: " + reviews.get(position).getAuthor());

        TextView reviewText = (TextView) reviewListView.findViewById(R.id.tv_review);
        reviewText.setText(reviews.get(position).getContent());

        return reviewListView;
}
