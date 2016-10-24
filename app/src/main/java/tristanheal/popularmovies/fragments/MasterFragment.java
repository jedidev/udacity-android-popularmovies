package tristanheal.popularmovies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.adapters.ThumbnailsAdapter;
import tristanheal.popularmovies.enums.MovieSortMode;
import tristanheal.popularmovies.interfaces.IGetMoviesTaskCallback;
import tristanheal.popularmovies.interfaces.IMovieItemSelectCallback;
import tristanheal.popularmovies.models.MovieModel;
import tristanheal.popularmovies.services.Favorites;
import tristanheal.popularmovies.services.MovieDbApi;

public class MasterFragment extends Fragment implements IGetMoviesTaskCallback {

    private final Favorites mFavorites = Favorites.getInstance();

    private GridView mThumbnailsGrid;
    private ThumbnailsAdapter mThumbnailsAdapter;
    private MovieDbApi mMovieDbApi;

    public MasterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.master_fragment, container);


        mMovieDbApi = MovieDbApi.getInstance();
        mThumbnailsGrid = (GridView)view.findViewById(R.id.thumbnails_grid);
        mThumbnailsAdapter = new ThumbnailsAdapter((IMovieItemSelectCallback)getActivity());
        mThumbnailsAdapter.SetMovies(new ArrayList<MovieModel>());
        mThumbnailsGrid.setAdapter(mThumbnailsAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mMovieDbApi.getMovies(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.sort_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.popular:

                mMovieDbApi.setMovieSortMode(MovieSortMode.Popularity);
                break;

            case R.id.rating:

                mMovieDbApi.setMovieSortMode(MovieSortMode.Rating);
                break;

            case R.id.favorites:

                mMovieDbApi.setMovieSortMode(MovieSortMode.Favorite);
                break;

        }

        mMovieDbApi.getMovies(this);
        return true;
    }

    @Override
    public void getMoviesTaskComplete(List<MovieModel> movies) {

        mThumbnailsAdapter.SetMovies(movies);
    }
}
