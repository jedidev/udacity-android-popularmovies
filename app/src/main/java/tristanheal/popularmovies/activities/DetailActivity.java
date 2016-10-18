package tristanheal.popularmovies.activities;

import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.models.MovieModel;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mThumbnail;
    private TextView mYear;
    private TextView mRating;
    private TextView mOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        MovieModel movie = (MovieModel)intent.getSerializableExtra(MainActivity.MOVIE_OBJECT);

        mTitle = (TextView)findViewById(R.id.title);
        mThumbnail = (ImageView)findViewById(R.id.poster);
        mYear = (TextView)findViewById(R.id.year);
        mRating = (TextView)findViewById(R.id.rating);
        mOverview = (TextView)findViewById(R.id.overview);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        mTitle.setText(movie.OriginalTitle);
        Picasso.with(this).load(getString(R.string.moviedb_image_base_url) + movie.PosterPath).into(mThumbnail);
        mYear.setText(yearFormat.format(movie.ReleaseDate));
        mRating.setText(movie.VoteAverage.toString() + "/10");
        mOverview.setText(movie.Overview);


    }
}
