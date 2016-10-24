package tristanheal.popularmovies.interfaces;

import java.util.List;

import tristanheal.popularmovies.models.ReviewModel;

/**
 * Created by tsheal on 23/10/2016.
 */

public interface IGetReviewsCallback {

    void getReviewsComplete(List<ReviewModel> movies);
}
