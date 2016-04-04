package com.raksharao.projectpopularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class FavoriteMoviesListFragment extends Fragment {

    public FavoriteMoviesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_movies_list, container, false);

        ListView reviewListView = (ListView) rootView.findViewById(R.id.lv_favorite_movies);

        mReviewAdapter = new ReviewItemAdapter(getActivity(), mReviews);
        reviewListView.setAdapter(mReviewAdapter);

        return rootView;
    }
}
