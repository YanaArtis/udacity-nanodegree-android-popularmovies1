package kiborgov.net.popularmovies1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesPostersFragment extends Fragment {
    private MoviesPostersAdapter moviesPostersAdapter = null;

    public MoviesPostersFragment () {}

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movies_posters_fragment, container, false);

        moviesPostersAdapter = new MoviesPostersAdapter(getActivity(), new ArrayList<MoviePoster>());

        ListView listView = (ListView) rootView.findViewById(R.id.listview_movies_posters);
        listView.setAdapter(moviesPostersAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MoviePoster moviePoster = (MoviePoster) moviesPostersAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra(getString(R.string.EXTRA_ORIGINAL_TITLE), moviePoster.original_title)
                        .putExtra(getString(R.string.EXTRA_IMAGE_URL), moviePoster.bmpPath)
                        .putExtra(getString(R.string.EXTRA_OVERVIEW), moviePoster.overview)
                        .putExtra(getString(R.string.EXTRA_VOTE_AVERAGE), moviePoster.vote_average)
                        .putExtra(getString(R.string.EXTRA_RELEASE_DATE), moviePoster.release_date);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movies_posters, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        String newSortMode = null;
        switch (item.getItemId()) {
        case R.id.action_sort_most_popular:
            updateMovies(newSortMode = Const.SORT_MOVIES_VALUE_BY_POPULARITY);
            break;
        case R.id.action_sort_highest_rated:
            updateMovies(newSortMode = Const.SORT_MOVIES_VALUE_BY_VOTE_AVERAGE);
            break;
        }
        if (newSortMode != null) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
            editor.putString(getString(R.string.prefs_key_sort_mode), newSortMode);
            editor.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateMovies (String sortMode) {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute(sortMode);
        String sortTitle = sortMode.equals(Const.SORT_MOVIES_VALUE_BY_POPULARITY)
                ? getString(R.string.title_sort_most_popular)
                : getString(R.string.title_sort_highest_rated)
                ;
        getActivity().setTitle(getString(R.string.app_name)+": "+sortTitle);
    }

    @Override
    public void onStart () {
        super.onStart();
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortMode = sharedPrefs.getString
                ( getString(R.string.prefs_key_sort_mode)
                , Const.SORT_MOVIES_VALUE_BY_POPULARITY
                );
        updateMovies(sortMode);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground (String... params) {
            if (params.length < 1) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String moviesJsonStr = null;

            String sortMode = params[0]; // Const.SORT_MOVIES_VALUE_BY_POPULARITY;

            try {
                Uri builtUri = Uri.parse(Const.URL_MOVIES).buildUpon()
                        .appendQueryParameter(Const.SORT_MOVIES_PARAM, sortMode+"."+Const.SORT_MOVIES_VALUE_DESC)
                        .appendQueryParameter(Const.MOVIES_KEY_PARAM, Const.MOVIES_KEY_VALUE)
                        .build();
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                if (buffer.length() < 1) {
                    return null;
                }
                moviesJsonStr = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                return new JSONObject(moviesJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute (JSONObject result ) {
            if (result != null) {
                moviesPostersAdapter.clear();
                try {
                    JSONArray jaMovies = result.getJSONArray("results");
                    for (int i = 0; i < jaMovies.length(); i++) {
                        JSONObject joMovie = jaMovies.getJSONObject(i);
                        String original_title = joMovie.getString("original_title");
                        String bmpPath = joMovie.getString("backdrop_path");
                        String overview = joMovie.getString("overview");
                        double vote_average = joMovie.getDouble("vote_average");
                        String release_date = joMovie.getString("release_date");

                        MoviePoster mp = new MoviePoster
                                ( original_title
                                , Const.URL_IMAGES+"/"+Const.IMAGE_SIZE[Const.DEFAULT_IMAGE_SIZE]+bmpPath
                                , overview, vote_average, release_date
                                );
                        moviesPostersAdapter.add(mp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
