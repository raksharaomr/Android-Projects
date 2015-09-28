package com.raksharao.projectpopularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.raksharao.projectpopularmovies.models.MovieResult;

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
public class MainActivityFragment extends Fragment {

    private static String KEY_MOVIE_RESULTS_LIST = "movieResults";

    public static final String MOVIE_ID = "com.raksharao.projectpopularmovies.MainActivityFragment.MOVIE_ID";
    private MovieImageAdapter mImageAdapter;
    private ArrayList <MovieResult> movieImagePaths;

    private MovieResult movieResult;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView moviesGridView = (GridView) rootView.findViewById(R.id.gv_movies);

        if (savedInstanceState != null) {
            movieImagePaths = savedInstanceState.getParcelableArrayList(KEY_MOVIE_RESULTS_LIST);
        } else {
            movieImagePaths = new ArrayList<>();
        }

        mImageAdapter = new MovieImageAdapter(
            getActivity(),
            movieImagePaths
        );

        moviesGridView.setAdapter(mImageAdapter);

        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            int movieId = movieResult.getResults().get(position).getId();
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(MOVIE_ID, movieId);
            startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIE_RESULTS_LIST, movieImagePaths);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.grid_view_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_refresh) {
            updateMovieThumbnails();
            return true;
        } else
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this.getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        updateMovieThumbnails();
        super.onStart();
    }

    public void updateMovieThumbnails() {
        FetchMovieDataTask fetchMovieDataTask = new FetchMovieDataTask();
        fetchMovieDataTask.execute();
    }

    private String getSortingPref() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        return settings.getString("SortingOrderKey", "Most Popular");
    }

    public class FetchMovieDataTask extends AsyncTask<Void, Void, MovieResult> {

        private final String LOG_CATEGORY = FetchMovieDataTask.class.getSimpleName();

        @Override
        protected MovieResult doInBackground(Void... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {
                String sortOrder = "";
                if (getSortingPref().equals("Most Popular")) {
                    sortOrder = "popularity.desc";
                } else if (getSortingPref().equals("Highest Rated")) {
                    sortOrder = "vote_average.desc";
                }
                //TODO Insert AP KEY
                String apiKey = "";

                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie";
                final String SORT_PARAM = "sort_by";
                final String API_KEY_PARAM = "api_key";

                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, sortOrder)
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

                Gson gson = new Gson();

                movieResult = gson.fromJson(bufferedReader, MovieResult.class);

                return movieResult;

            } catch (IOException e) {
                Log.e(LOG_CATEGORY, "Error receiving JSON", e);
            } finally{
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

            return null;
        }

        @Override
        protected void onPostExecute(MovieResult movieResult) {
            movieImagePaths = new ArrayList<>();

            for (int i = 0; i < movieResult.getResults().size(); i++) {
                MovieResult tempMovieResult = new MovieResult(
                    movieResult.getResults().get(i).getId(),
                    movieResult.getResults().get(i).getPosterPath()
                );
                movieImagePaths.add(tempMovieResult);
            }

            mImageAdapter.updateImagePaths(movieImagePaths);
            super.onPostExecute(movieResult);
        }
    }
}
