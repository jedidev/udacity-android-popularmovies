package tristanheal.popularmovies;

import android.app.Application;
import android.content.Context;

/**
 * Created by tsheal on 19/10/2016.
 */

public class PopularMovies extends Application {

    private static Context context;

    public void onCreate() {

        super.onCreate();
        PopularMovies.context = getApplicationContext();
    }

    public static Context getAppContext() {

        return PopularMovies.context;
    }
}
