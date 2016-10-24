package tristanheal.popularmovies.interfaces;

import java.util.List;
import tristanheal.popularmovies.models.TrailerModel;

/**
 * Created by tsheal on 23/10/2016.
 */

public interface IGetTrailersCallback {

    void getTrailersComplete(List<TrailerModel> movies);
}
