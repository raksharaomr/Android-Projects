package com.raksharao.projectpopularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raksharao.projectpopularmovies.models.MovieDetail;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    private Context mContext;

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

        FetchMovieDetailsTask fetchMovieDetailsTask = new FetchMovieDetailsTask();
        fetchMovieDetailsTask.execute(mMovieId);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FetchMovieDetailsTask fetchMovieDetailsTask = new FetchMovieDetailsTask();
        fetchMovieDetailsTask.execute(mMovieId);
    }

    public class FetchMovieDetailsTask extends AsyncTask<Integer, Void, MovieDetail> {

        @Override
        protected MovieDetail doInBackground(Integer... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {

                //TODO Insert API Key
                String apiKey = "";

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

                Log.i(LOG_CATEGORY, stringBuffer.toString());
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
            MovieDetail movieDetail = new MovieDetail();

            JSONObject movieJson = new JSONObject(movieDetailJsonStr);

            movieDetail.setOriginalTitle(movieJson.getString("original_title"))
                .setPosterPath(movieJson.getString("poster_path"))
                .setRelDate(movieJson.getString("release_date"))
                .setUserRating(movieJson.getString("vote_average"))
                .setPlotSynopsis(movieJson.getString("overview"))
                .setDuration(movieJson.getString("runtime"));

            return movieDetail;
        }
    }
}
