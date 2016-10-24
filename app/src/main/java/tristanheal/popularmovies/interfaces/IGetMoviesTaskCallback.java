package tristanheal.popularmovies.interfaces;

import java.util.List;

import tristanheal.popularmovies.models.MovieModel;

/**
 * Created by tsheal on 18/10/2016.
 */

public interface IGetMoviesTaskCallback {

    void getMoviesTaskComplete(List<MovieModel> movies);
}
