package tristanheal.popularmovies.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.fragments.DetailsFragment;
import tristanheal.popularmovies.models.MovieModel;

public class DetailActivity extends AppCompatActivity {

    private FrameLayout mDetailFragmentContainer;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailFragmentContainer = (FrameLayout)findViewById(R.id.detail_container);
        mIntent = getIntent();

        MovieModel movie = mIntent.getParcelableExtra(MainActivity.MOVIE_OBJECT);

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
