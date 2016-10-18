package tristanheal.popularmovies.tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.interfaces.IGetMoviesTaskCallback;
import tristanheal.popularmovies.models.MovieModel;

/**
 * Created by tsheal on 18/10/2016.
 */

public class GetMoviesTask extends AsyncTask<String, Void, List<MovieModel>>{

    private Context mContext;
    private IGetMoviesTaskCallback mCallback;

    public GetMoviesTask (Context context, IGetMoviesTaskCallback callback)
    {
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected List<MovieModel> doInBackground(String... params) {

        String baseUrl = mContext.getString(R.string.moviedb_base_url);
        String relativeUrl = params[0];
        String apiKey = mContext.getString(R.string.moviedb_api_key);
        String request = baseUrl + relativeUrl + "?api_key=" + apiKey;
        ArrayList<MovieModel> movieList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            URL requestUrl = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {

                InputStream inputStream = connection.getInputStream();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream));

                String responseLine;
                StringBuffer response = new StringBuffer();

                while ((responseLine = responseReader.readLine()) != null) {
                    response.append(responseLine+"\n");
                }
                responseReader.close();

                String responseString = response.toString();

                JSONObject obj = new JSONObject(responseString);
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
                }
            }
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return movieList;
    }

    @Override
    protected void onPostExecute(List<MovieModel> movieModels) {
        super.onPostExecute(movieModels);

        mCallback.GetMoviesTaskComplete(movieModels);
    }
}
