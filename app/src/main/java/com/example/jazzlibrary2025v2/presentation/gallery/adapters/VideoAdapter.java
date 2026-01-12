package com.example.jazzlibrary2025v2.presentation.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jazzlibrary2025v2.R;
import com.example.jazzlibrary2025v2.domain.model.Video;
import com.example.jazzlibrary2025v2.presentation.gallery.GalleryFragment;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private final List<Video> videoList;
    private final Context context;
    private final LifecycleOwner lifecycleOwner;

    private boolean isLoading = false;
    private final int ITEMS_PER_PAGE = 5;

    public VideoAdapter(Context context, List<Video> videoList, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.videoList = videoList;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == videoList.size() && isLoading) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.youtubeplayer_recycler_list_item, parent, false);
            return new VideoViewHolder(view, lifecycleOwner);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
            VideoViewHolder videoHolder = (VideoViewHolder) holder;
            Video video = videoList.get(position);
            videoHolder.bind(video);
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size() + (isLoading ? 1 : 0);
    }

    public void addLoadingFooter() {
        isLoading = true;
        notifyItemInserted(videoList.size());
    }


    public void removeLoadingFooter() {
        isLoading = false;
        notifyItemRemoved(videoList.size());
    }

    public void addVideos(List<Video> newVideos) {
        int startPosition = videoList.size();
        videoList.addAll(newVideos);
        notifyItemRangeInserted(startPosition, newVideos.size());
    }

    public boolean isLastPage(int totalItems) {
        return videoList.size() >= totalItems;
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void updateVideos(List<Video> newVideos) {
        videoList.clear();
        videoList.addAll(newVideos);
        notifyDataSetChanged();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        private final YouTubePlayerView youTubePlayerView;
        private final TextView videoTitle;
        private final TextView videoArtist;

        private final ImageView thumbnailImage;
        private final ImageButton playButton;
        private YouTubePlayer youTubePlayer;
        private boolean isPlayerReady = false;




        public VideoViewHolder(@NonNull View itemView, LifecycleOwner lifecycleOwner) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            videoTitle = itemView.findViewById(R.id.video_title);
            videoArtist = itemView.findViewById(R.id.video_artist);

            thumbnailImage = itemView.findViewById(R.id.thumbnail_image);
            playButton = itemView.findViewById(R.id.play_button);

            // Initialize YouTube Player (but don't load video yet)
            // Enable automatic lifecycle management
            lifecycleOwner.getLifecycle().addObserver(youTubePlayerView);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer player) {
                    youTubePlayer = player;
                    isPlayerReady = true;
                }
            });

            playButton.setOnClickListener(v -> {
                if (isPlayerReady && youTubePlayer != null) {
                    // Hide thumbnail and show player
                    thumbnailImage.setVisibility(View.INVISIBLE);
                    playButton.setVisibility(View.INVISIBLE);
                    youTubePlayerView.setVisibility(View.VISIBLE);

                    // Load and play the video
                    Video video = (Video) itemView.getTag();
                    youTubePlayer.loadVideo(video.getLocationId(), 0);
                }
            });

        }

        private Lifecycle getLifecycle() {
            return ((AppCompatActivity) itemView.getContext()).getLifecycle();
        }



        public void bind(Video video) {
            itemView.setTag(video);
            videoTitle.setText(video.getVideoName());
            //videoArtist.setText(video.getArtistName());

            String thumbnailUrl = "https://img.youtube.com/vi/" + video.getLocationId() + "/0.jpg";
            Glide.with(itemView.getContext())
                    .load(thumbnailUrl)
                    .into(thumbnailImage);

            thumbnailImage.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.VISIBLE);
            youTubePlayerView.setVisibility(View.INVISIBLE);

            if (youTubePlayer != null) {
                youTubePlayer.cueVideo(video.getLocationId(), 0);
            }
        }





    }
}