package tristanheal.popularmovies.services;

import android.app.Application;
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
import tristanheal.popularmovies.enums.MovieSortMode;
import tristanheal.popularmovies.interfaces.IGetMoviesTaskCallback;
import tristanheal.popularmovies.interfaces.IGetReviewsCallback;
import tristanheal.popularmovies.interfaces.IGetTrailersCallback;
import tristanheal.popularmovies.models.MovieModel;
import tristanheal.popularmovies.models.ReviewModel;
import tristanheal.popularmovies.models.TrailerModel;

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
    private MovieSortMode mSortMode;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private MovieDbApi() {

        mApplicationContext = PopularMovies.getAppContext();
        mRequestQueue = Volley.newRequestQueue(mApplicationContext);
        mSortMode = MovieSortMode.Popularity;
    }

    public void getReviews(int id, final IGetReviewsCallback callback) {

        Context context = PopularMovies.getAppContext();
        String path = getFullPath(context.getString(R.string.moviedb_reviews_relative_url, id));

        final List<ReviewModel> reviewsList = new ArrayList<>();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>()  {

            @Override
            public void onResponse(JSONObject obj) {

                try {
                    JSONArray items = obj.getJSONArray("results");
                    for (int n = 0; n < items.length(); n++) {

                        JSONObject reviewItem = items.getJSONObject(n);
                        ReviewModel newReview = new ReviewModel();

                        newReview.author = reviewItem.getString("author");
                        newReview.content = reviewItem.getString("content");
                        reviewsList.add(newReview);

                        callback.getReviewsComplete(reviewsList);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                callback.getReviewsComplete(reviewsList);
            }
        });

        mRequestQueue.add(objectRequest);
    }

    public void getTrailers(int id, final IGetTrailersCallback callback) {

        Context context = PopularMovies.getAppContext();
        String path = getFullPath(context.getString(R.string.moviedb_trailers_relative_url, id));

        final List<TrailerModel> trailerList = new ArrayList<>();

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>()  {

            @Override
            public void onResponse(JSONObject obj) {

                try {
                    JSONArray items = obj.getJSONArray("youtube");
                    for (int n = 0; n < items.length(); n++) {

                        JSONObject trailerItem = items.getJSONObject(n);
                        TrailerModel newTrailer = new TrailerModel();

                        newTrailer.name = trailerItem.getString("name");
                        newTrailer.source = trailerItem.getString("source");
                        trailerList.add(newTrailer);

                        callback.getTrailersComplete(trailerList);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                callback.getTrailersComplete(trailerList);
            }
        });

        mRequestQueue.add(objectRequest);
    }

    public void getMovies(IGetMoviesTaskCallback callback) {

        String path;
        switch (mSortMode) {

            case Popularity:

                path = getFullPath("movie/popular");
                getMoviesFromPath(path, callback);
                break;

            case Rating:

                path = getFullPath("movie/top_rated");
                getMoviesFromPath(path, callback);
                break;

            case Favorite:

                getMoviesFromFavorites(callback);
                break;
        }
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

                        newMovie.Id = movieItem.getInt("id");
                        newMovie.PosterPath = movieItem.getString("poster_path");
                        newMovie.OriginalTitle = movieItem.getString("original_title");
                        newMovie.Overview = movieItem.getString("overview");
                        newMovie.VoteAverage = movieItem.getDouble("vote_average");
                        newMovie.ReleaseDate = dateFormat.parse(movieItem.getString("release_date"));
                        movieList.add(newMovie);

                        callback.getMoviesTaskComplete(movieList);
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

                callback.getMoviesTaskComplete(movieList);
            }
        });

        mRequestQueue.add(objectRequest);
    }

    public void getMoviesFromFavorites (IGetMoviesTaskCallback callback) {

        callback.getMoviesTaskComplete(Favorites.getInstance().getFavorites());
    }


    public MovieSortMode getMovieSortMode() {

        return mSortMode;
    }

    public void setMovieSortMode(MovieSortMode sortMode) {

        mSortMode = sortMode;
    }
}
