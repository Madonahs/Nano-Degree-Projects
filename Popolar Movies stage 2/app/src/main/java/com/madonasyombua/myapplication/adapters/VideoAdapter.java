package com.madonasyombua.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.madonasyombua.myapplication.R;
import com.madonasyombua.myapplication.model.Video;
import com.madonasyombua.myapplication.model.VideoResults;
import com.squareup.picasso.Picasso;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private final Context mContext;
    private final VideoResults mVideoResults;

    public VideoAdapter(Context context, VideoResults videoResults) {
        this.mContext = context;
        this.mVideoResults = videoResults;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_item, parent, false);

        return new VideoViewHolder(view);
    }

    /**
     * the on bind view holder
     * @param holder the video view holder
     * @param position position
     */

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        final Video video = mVideoResults.getResults().get(position);
        Picasso.with(mContext)
                .load(buildThumbnailUrl(video.getKey()))
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(holder.mIvVideoThumb);

        holder.mTvVideoTitle.setText(video.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=".concat(video.getKey())));
            mContext.startActivity(intent);
        });
    }

    /**
     * get item count
     * @return video result
     */
    @Override
    public int getItemCount() {
        return mVideoResults.getResults().size();
    }

    private String buildThumbnailUrl(String videoId) {
        return "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
    }

    /**
     * my video View holder inner class
     */
    class VideoViewHolder extends RecyclerView.ViewHolder {
        final ImageView mIvVideoThumb;
        final TextView mTvVideoTitle;

        VideoViewHolder(View itemView) {
            super(itemView);

            mIvVideoThumb = itemView.findViewById(R.id.cvVideo);
            mTvVideoTitle = itemView.findViewById(R.id.tvVideoTitle);
        }
    }
}
