package tristanheal.popularmovies.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tsheal on 17/10/2016.
 */

public class MovieModel implements Serializable {

    public String PosterPath;
    public String OriginalTitle;
    public String Overview;
    public Double VoteAverage;
    public Date ReleaseDate;

}
