package tristanheal.popularmovies.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.adapters.ThumbnailsAdapter;
import tristanheal.popularmovies.enums.MovieSortMode;
import tristanheal.popularmovies.fragments.DetailsFragment;
import tristanheal.popularmovies.interfaces.IGetMoviesTaskCallback;
import tristanheal.popularmovies.interfaces.IMovieItemSelectCallback;
import tristanheal.popularmovies.models.MovieModel;
import tristanheal.popularmovies.services.Favorites;
import tristanheal.popularmovies.services.MovieDbApi;

public class MainActivity extends AppCompatActivity implements IMovieItemSelectCallback {

    public final static String MOVIE_OBJECT = "tristanheal.popularmovies.MOVIE_OBJECT";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.detail_container) != null;
    }

    @Override
    public void movieItemSelected(MovieModel movie) {

        if (!mTwoPane) {

            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra(MainActivity.MOVIE_OBJECT, movie);
            startActivity(i);

        } else {

            Bundle args = new Bundle();
            args.putParcelable("movie", movie);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            Fragment fragment = new DetailsFragment();
            fragment.setArguments(args);
            ft.replace(R.id.detail_container, fragment);
            ft.commit();
        }
    }
}
