package com.raksharao.projectpopularmovies;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raksharao.projectpopularmovies.models.MovieDetail;
import com.raksharao.projectpopularmovies.models.MovieResult;
import com.raksharao.projectpopularmovies.models.MovieReview;
import com.raksharao.projectpopularmovies.models.MovieTrailer;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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
public class MovieDetailActivityFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_MOVIE_DETAIL = "movieDetail";
    public static final String KEY_MOVIE_ID = "movieId";

    private MovieDetail mMovieDetail;
    private MovieTrailer movieTrailer;
    private Context mContext;

    private LinearLayout trailersLayout;
    private TextView originalTitleTextView;
    private ImageView movieThumbnailImageView;
    private TextView movieRelDateTextView;
    private TextView userRatingTextView;
    private TextView plotSynopsisTextView;
    private TextView movieDuration;
    private String LOG_CATEGORY = "movieDetailActivityFragment";
    private int mMovieId;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mContext = this.getActivity().getApplicationContext();

        Intent intent = getActivity().getIntent();
        mMovieId = intent.getIntExtra(MainActivityFragment.MOVIE_ID, -1);

        originalTitleTextView = (TextView) rootView.findViewById(R.id.tv_original_title);
        movieThumbnailImageView = (ImageView) rootView.findViewById(R.id.iv_movie_thumbnail);
        movieRelDateTextView = (TextView) rootView.findViewById(R.id.tv_movie_rel_date);
        userRatingTextView = (TextView) rootView.findViewById(R.id.tv_user_rating);
        plotSynopsisTextView = (TextView) rootView.findViewById(R.id.tv_plot_synopsis);
        movieDuration = (TextView) rootView.findViewById(R.id.tv_duration);

        trailersLayout = (LinearLayout) rootView.findViewById(R.id.linear_layout_trailers);

        if (savedInstanceState != null) {
            mMovieDetail = savedInstanceState.getParcelable(KEY_MOVIE_DETAIL);
        } else {
            getMovieDetails();
        }

        Button getReviewsButton = (Button) rootView.findViewById(R.id.button_read_reviews);
        getReviewsButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_MOVIE_DETAIL, mMovieDetail);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getMovieDetails();
        getMovieTrailers();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                mMovieId = data.getIntExtra(MainActivityFragment.MOVIE_ID, -1);
            }
        }
    }

    private void getMovieDetails() {
        FetchMovieDetailsTask fetchMovieDetailsTask = new FetchMovieDetailsTask();
        fetchMovieDetailsTask.execute(mMovieId);
    }

    private void getMovieTrailers() {
        FetchMovieTrailersTask fetchMovieTrailersTask = new FetchMovieTrailersTask();
        fetchMovieTrailersTask.execute(mMovieId);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_read_reviews:
                Intent reviewIntent = new Intent(getActivity(), MovieReviewListActivity.class);
                reviewIntent.putExtra(KEY_MOVIE_ID, mMovieId);
                startActivityForResult(reviewIntent, 1);
                break;
        }
    }

    public class FetchMovieDetailsTask extends AsyncTask<Integer, Void, MovieDetail> {

        @Override
        protected MovieDetail doInBackground(Integer... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {

                //TODO Insert API Key
                // String apiKey = "";
                String apiKey = "4defca6ee7be68c2803bd4d1a11b5cdd";

                final String BASE_URL = "http://api.themoviedb.org/3/movie/" + String.valueOf(params[0]);
                final String API_KEY_PARAM = "api_key";

                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, apiKey)
                        .build();

                URL url = new URL(uri.toString());

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                MovieDetail movieDetail = getMovieDetailFromJsonString(stringBuffer.toString());
                return movieDetail;

            } catch (JSONException je) {
                Log.e(LOG_CATEGORY, "Error in JSON", je);
                return null;
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
        protected void onPostExecute(MovieDetail movieDetail) {
            originalTitleTextView.setText(movieDetail.getOriginalTitle());
            movieRelDateTextView.setText(movieDetail.getRelDate().substring(0, 4));
            userRatingTextView.setText(movieDetail.getUserRating() + "/10");
            plotSynopsisTextView.setText(movieDetail.getPlotSynopsis());
            movieDuration.setText(movieDetail.getDuration() + " mins");

            String fullPath = "http://image.tmdb.org/t/p/" + "w500" + movieDetail.getPosterPath();
            Picasso.with(mContext).load(fullPath).into(movieThumbnailImageView);
        }

        private MovieDetail getMovieDetailFromJsonString(String movieDetailJsonStr) throws JSONException {
            mMovieDetail = new MovieDetail();

            JSONObject movieJson = new JSONObject(movieDetailJsonStr);

            mMovieDetail.setOriginalTitle(movieJson.getString("original_title"))
                .setPosterPath(movieJson.getString("poster_path"))
                .setRelDate(movieJson.getString("release_date"))
                .setUserRating(movieJson.getString("vote_average"))
                .setPlotSynopsis(movieJson.getString("overview"))
                .setDuration(movieJson.getString("runtime"));

            return mMovieDetail;
        }
    }


    public class FetchMovieTrailersTask extends AsyncTask<Integer, Void, MovieTrailer> {
        @Override
        protected MovieTrailer doInBackground(Integer... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {

                //TODO Insert API Key
                // String apiKey = "";
                String apiKey = "4defca6ee7be68c2803bd4d1a11b5cdd";

                final String BASE_URL = "http://api.themoviedb.org/3/movie/" +
                        String.valueOf(params[0]) +
                        "/videos";
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

                movieTrailer = gson.fromJson(bufferedReader, MovieTrailer.class);

                return movieTrailer;

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
        protected void onPostExecute(final MovieTrailer movieTrailer) {
            for (final MovieTrailer.Result trailer : movieTrailer.getResults()) {
                int id = movieTrailer.getResults().indexOf(trailer) + 1;

                TextView valueTV = new TextView(mContext);
                valueTV.setText("Trailer " + String.valueOf(id));
                valueTV.setTextColor(Color.BLACK);
                valueTV.setTextSize(getResources().getDimension(R.dimen.textview_text_size));
                valueTV.setId(id);
                valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
                valueTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
                            startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            String videoUrl = "https://www.youtube.com/watch?v=" + trailer.getKey();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
                        }
                    }
                });
                trailersLayout.addView(valueTV);
            }
        }
    }
}
