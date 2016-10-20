package tristanheal.popularmovies.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tristanheal.popularmovies.PopularMovies;
import tristanheal.popularmovies.R;
import tristanheal.popularmovies.interfaces.IGetMoviesTaskCallback;
import tristanheal.popularmovies.models.MovieModel;

/**
 * Created by tsheal on 19/10/2016.
 */

public class MovieDbApi {

    // Static singleton elements
    private static MovieDbApi mInstance;

    public static MovieDbApi getInstance() {

        if (mInstance == null) {

            mInstance = new MovieDbApi();
        }
        return  mInstance;
    }

    // Instance elements
    private RequestQueue mRequestQueue;
    private Context mApplicationContext;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private MovieDbApi() {

        mApplicationContext = PopularMovies.getAppContext();
        mRequestQueue = Volley.newRequestQueue(mApplicationContext);
    }

    public void getMoviesByPopularity(IGetMoviesTaskCallback callback) {

        String path = getFullPath(mApplicationContext.getString(R.string.moviedb_popular_movies_relative_url));
        getMoviesFromPath(path, callback);
    }

    public void getMoviesByRating(IGetMoviesTaskCallback callback) {

        String path = getFullPath(mApplicationContext.getString(R.string.moviedb_top_rated_movies_relative_url));
        getMoviesFromPath(path, callback);
    }

    private String getFullPath(String endpoint) {

        String path = mApplicationContext.getString(R.string.moviedb_base_url);
        path += endpoint + "?api_key=";
        path += mApplicationContext.getString(R.string.moviedb_api_key);
        return path;
    }

    private void getMoviesFromPath(String path, final IGetMoviesTaskCallback callback) {

        final List<MovieModel> movieList = new ArrayList<>();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject obj) {

                try {
                    JSONArray items = obj.getJSONArray("results");
                    for (int n = 0; n < items.length(); n++) {

                        JSONObject movieItem = items.getJSONObject(n);
                        MovieModel newMovie = new MovieModel();

                        newMovie.PosterPath = movieItem.getString("poster_path");
                        newMovie.OriginalTitle = movieItem.getString("original_title");
                        newMovie.Overview = movieItem.getString("overview");
                        newMovie.VoteAverage = movieItem.getDouble("vote_average");
                        newMovie.ReleaseDate = dateFormat.parse(movieItem.getString("release_date"));
                        movieList.add(newMovie);

                        callback.GetMoviesTaskComplete(movieList);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                callback.GetMoviesTaskComplete(movieList);
            }
        });

        mRequestQueue.add(objectRequest);
    }
}
