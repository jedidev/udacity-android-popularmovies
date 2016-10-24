package tristanheal.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.models.ReviewModel;
import tristanheal.popularmovies.models.TrailerModel;
import tristanheal.popularmovies.viewholders.ReviewViewHolder;
import tristanheal.popularmovies.viewholders.TrailerViewHolder;

/**
 * Created by tsheal on 24/10/2016.
 */

public class ReviewsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ReviewModel> mReviews;

    public ReviewsAdapter(Context context) {

        mContext = context;
        mReviews = new ArrayList<>();
    }

    public void setReviews(List<ReviewModel> reviews) {

        mReviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.review_item, null);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        viewHolder.mAuthor = (TextView) view.findViewById(R.id.author);
        viewHolder.mContent = (TextView) view.findViewById(R.id.content);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ReviewViewHolder viewHolder = (ReviewViewHolder) holder;
        viewHolder.mAuthor.setText(mReviews.get(position).author);
        viewHolder.mContent.setText(mReviews.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }
}
