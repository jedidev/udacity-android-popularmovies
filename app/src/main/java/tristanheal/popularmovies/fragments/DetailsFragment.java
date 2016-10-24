package tristanheal.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.adapters.ReviewsAdapter;
import tristanheal.popularmovies.adapters.TrailersAdapter;
import tristanheal.popularmovies.interfaces.IGetReviewsCallback;
import tristanheal.popularmovies.interfaces.IGetTrailersCallback;
import tristanheal.popularmovies.models.MovieModel;
import tristanheal.popularmovies.models.ReviewModel;
import tristanheal.popularmovies.models.TrailerModel;
import tristanheal.popularmovies.services.Favorites;
import tristanheal.popularmovies.services.MovieDbApi;

/**
 * Created by tsheal on 23/10/2016.
 */

public class DetailsFragment extends Fragment implements IGetTrailersCallback, IGetReviewsCallback {

    private TextView mTitle;
    private ImageView mThumbnail;
    private TextView mYear;
    private TextView mRating;
    private TextView mOverview;
    private Button mFavoriteButton;

    private RecyclerView mTrailersList;
    private RecyclerView mReviewsList;
    private TrailersAdapter mTrailersAdapter;
    private ReviewsAdapter mReviewsAdapter;

    private MovieModel mMovie;
    private Favorites mFavorites;
    private boolean mIsFavorite;

    private Bundle mArgs;

    private MovieDbApi mMovieDbApi;

    public DetailsFragment () { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArgs = getArguments();
        mMovie = mArgs.getParcelable("movie");

        mMovieDbApi = MovieDbApi.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mFavorites = Favorites.getInstance();

        mTitle = (TextView)view.findViewById(R.id.title);
        mThumbnail = (ImageView)view.findViewById(R.id.poster);
        mYear = (TextView)view.findViewById(R.id.year);
        mRating = (TextView)view.findViewById(R.id.rating);
        mOverview = (TextView)view.findViewById(R.id.overview);
        mFavoriteButton = (Button)view.findViewById(R.id.favorite_button);
        mTrailersList = (RecyclerView)view.findViewById(R.id.trailers_list);
        mReviewsList = (RecyclerView)view.findViewById(R.id.reviews_list);

        mTrailersAdapter = new TrailersAdapter(getContext());
        mTrailersList.setAdapter(mTrailersAdapter);
        mTrailersList.setLayoutManager(new LinearLayoutManager(getContext()));

        mReviewsAdapter = new ReviewsAdapter(getContext());
        mReviewsList.setAdapter(mReviewsAdapter);
        mReviewsList.setLayoutManager(new LinearLayoutManager(getContext()));

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        mTitle.setText(mMovie.OriginalTitle);
        Picasso.with(getActivity()).load(getString(R.string.moviedb_image_base_url) + mMovie.PosterPath).into(mThumbnail);
        mYear.setText(yearFormat.format(mMovie.ReleaseDate));
        mRating.setText(mMovie.VoteAverage.toString() + "/10");
        mOverview.setText(mMovie.Overview);

        refreshFavoriteButton();

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mIsFavorite == false) {

                    mFavorites.addFavorite(mMovie);
                    Toast.makeText(getActivity(), getString(R.string.added_to_favorites_toast), Toast.LENGTH_SHORT).show();
                    refreshFavoriteButton();

                } else {

                    mFavorites.deleteFavorite(mMovie);
                    Toast.makeText(getActivity(), getString(R.string.deleted_from_favorites_toast), Toast.LENGTH_SHORT).show();
                    refreshFavoriteButton();
                }
            }
        });

        mMovieDbApi.getTrailers(mMovie.Id, this);
        mMovieDbApi.getReviews(mMovie.Id, this);

        return view;
    }

    private void refreshFavoriteButton() {

        mIsFavorite = mFavorites.isFavorite(mMovie);
        mFavoriteButton.setText(mIsFavorite ? R.string.delete_from_favorites : R.string.add_to_favorites);
    }

    @Override
    public void getTrailersComplete(List<TrailerModel> trailers) {

        mTrailersAdapter.setTrailers(trailers);
    }

    @Override
    public void getReviewsComplete(List<ReviewModel> reviews) {

        mReviewsAdapter.setReviews(reviews);
    }
}
