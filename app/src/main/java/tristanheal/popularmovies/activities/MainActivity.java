package tristanheal.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.adapters.ThumbnailsAdapter;
import tristanheal.popularmovies.interfaces.IGetMoviesTaskCallback;
import tristanheal.popularmovies.models.MovieModel;
import tristanheal.popularmovies.tasks.GetMoviesTask;

public class MainActivity extends AppCompatActivity implements IGetMoviesTaskCallback {

    public final static String MOVIE_OBJECT = "tristanheal.popularmovies.MOVIE_OBJECT";

    private GridView mThumbnailsGrid;
    private ThumbnailsAdapter mThumbnailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mThumbnailsGrid = (GridView)findViewById(R.id.thumbnails_grid);
        mThumbnailsAdapter = new ThumbnailsAdapter(this);
        mThumbnailsAdapter.SetMovies(new ArrayList<MovieModel>());
        mThumbnailsGrid.setAdapter(mThumbnailsAdapter);

        GetMoviesTask task = new GetMoviesTask(this, this);
        task.execute(getString(R.string.moviedb_popular_movies_relative_url));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        GetMoviesTask task = new GetMoviesTask(this, this);

        switch (item.getItemId()) {

            case R.id.popular:

                task.execute(getString(R.string.moviedb_popular_movies_relative_url));
                break;

            case R.id.rating:

                task.execute(getString(R.string.moviedb_top_rated_movies_relative_url));
                break;

        }
        return true;
    }

    @Override
    public void GetMoviesTaskComplete(List<MovieModel> movies) {

        mThumbnailsAdapter.SetMovies(movies);
    }
}
