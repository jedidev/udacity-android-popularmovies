package tristanheal.popularmovies.models;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by tsheal on 17/10/2016.
 */

public class MovieModel extends RealmObject implements Serializable {

    public int Id;
    public String PosterPath;
    public String OriginalTitle;
    public String Overview;
    public Double VoteAverage;
    public Date ReleaseDate;

}
