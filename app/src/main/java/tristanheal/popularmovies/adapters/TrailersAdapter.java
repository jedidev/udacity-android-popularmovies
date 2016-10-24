package tristanheal.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tristanheal.popularmovies.R;
import tristanheal.popularmovies.models.TrailerModel;
import tristanheal.popularmovies.viewholders.TrailerViewHolder;

/**
 * Created by tsheal on 24/10/2016.
 */

public class TrailersAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<TrailerModel> mTrailers;

    public TrailersAdapter(Context context) {

        mContext = context;
        mTrailers = new ArrayList<>();
    }

    public void setTrailers(List<TrailerModel> trailers) {

        mTrailers = trailers;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.trailer_item, null);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        viewHolder.mTrailerName = (TextView)view.findViewById(R.id.trailer_name);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        TrailerViewHolder viewHolder = (TrailerViewHolder)holder;
        viewHolder.mTrailerName.setText(mTrailers.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int p = position;

                String path = mContext.getString(R.string.youtube_watch_relative_url, mTrailers.get(p).source);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }
}
