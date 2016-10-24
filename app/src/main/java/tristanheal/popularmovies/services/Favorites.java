package tristanheal.popularmovies.services;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tristanheal.popularmovies.models.MovieModel;

/**
 * Created by tsheal on 21/10/2016.
 */

public class Favorites {

    private static Favorites mInstance;

    public static Favorites getInstance() {

        if (mInstance == null) {

            mInstance = new Favorites();
        }
        return mInstance;
    }

    private Realm mRealm;

    private Favorites() {

        mRealm = Realm.getDefaultInstance();
    }

    public void addFavorite(final MovieModel movie) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (mRealm.where(MovieModel.class).equalTo("Id", movie.Id).count() < 1)
                    mRealm.copyToRealm(movie);
            }
        });
    }

    public void deleteFavorite(final MovieModel movie) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                MovieModel movieToDelete = mRealm.where(MovieModel.class).equalTo("Id", movie.Id).findFirst();
                movieToDelete.deleteFromRealm();
            }
        });
    }

    public List<MovieModel> getFavorites() {

        List<MovieModel> movies = new ArrayList<>();
        RealmResults<MovieModel> results = mRealm.where(MovieModel.class).findAll();

        for (MovieModel movie : results)
            movies.add(movie);

        return movies;
    }

    public long count() {

        return mRealm.where(MovieModel.class).count();
    }

    public boolean isFavorite(MovieModel movie) {

        return mRealm.where(MovieModel.class).equalTo("Id", movie.Id).count() > 0;
    }
}
