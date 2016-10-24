package tristanheal.popularmovies;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import tristanheal.popularmovies.enums.MovieSortMode;

/**
 * Created by tsheal on 19/10/2016.
 */

public class PopularMovies extends Application {

    private static Context mContext;

    public void onCreate() {

        super.onCreate();
        Realm.init(this);
        PopularMovies.mContext = getApplicationContext();
    }

    public static Context getAppContext() {

        return PopularMovies.mContext;
    }
}
