package com.raksharao.projectpopularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.raksharao.projectpopularmovies.models.MovieReview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieReviewListActivityFragment extends Fragment {

    private static final String LOG_CATEGORY = "moviereviewfragment";

    private MovieReview movieReview;
    private Context mContext;
    private int mMovieId;

    private List<MovieReview.Result> mReviews = new ArrayList<>();
    private ReviewItemAdapter mReviewAdapter;

    public MovieReviewListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews_list, container, false);

        mMovieId = getActivity().getIntent().getIntExtra(MovieDetailActivityFragment.KEY_MOVIE_ID, -1);

        ListView reviewListView = (ListView) rootView.findViewById(R.id.lv_reviews);

        mReviewAdapter = new ReviewItemAdapter(getActivity(), mReviews);
        reviewListView.setAdapter(mReviewAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        getMovieReviews();
        super.onStart();
    }

    private void getMovieReviews() {
        FetchMovieReviewsTask fetchMovieReviewsTask = new FetchMovieReviewsTask();
        fetchMovieReviewsTask.execute(mMovieId);
    }

    public class FetchMovieReviewsTask extends AsyncTask<Integer, Void, MovieReview> {
        @Override
        protected MovieReview doInBackground(Integer... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {

                //TODO Insert API Key
                // String apiKey = "";
                String apiKey = "4defca6ee7be68c2803bd4d1a11b5cdd";

                final String BASE_URL = "http://api.themoviedb.org/3/movie/" +
                        String.valueOf(params[0]) +
                        "/reviews";
                final String API_KEY_PARAM = "api_key";

                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, apiKey)
                        .build();

                URL url = new URL(uri.toString());

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                Gson gson = new Gson();

                movieReview = gson.fromJson(bufferedReader, MovieReview.class);

                return movieReview;

            }  catch (IOException ioe) {
                Log.e(LOG_CATEGORY, "Error receiving JSON", ioe);
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_CATEGORY, "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(MovieReview movieReview) {
            List<MovieReview.Result> reviews = movieReview.getResults();

            mReviewAdapter.updateReviewsList(reviews);
            super.onPostExecute(movieReview);
        }
    }
}
