package br.com.android.udacity.filmesfamosos.moviesdetails.adapter;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.android.udacity.filmesfamosos.R;
import br.com.android.udacity.filmesfamosos.constant.DataAPI;
import br.com.android.udacity.filmesfamosos.models.ResultVideo;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private List<ResultVideo> mListTrailer;
    private Context mContext;

    public VideoAdapter(List<ResultVideo> listTrailer, Context context) {
        this.mListTrailer = listTrailer;
        this.mContext = context;

    }

    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_video, parent, false );
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder videoHolder, int i) {

        final ResultVideo video = mListTrailer.get(i);

        videoHolder.playVideo.setText(video.getName());

        videoHolder.playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               playVideo(video.getKey());
            }
        });
    }

    private void playVideo(String key) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(DataAPI.URL_YOUTUBE + key));
        try {
            mContext.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            mContext.startActivity(webIntent);
        }
    }

    @Override
    public int getItemCount() {
        return mListTrailer.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        private TextView playVideo;
        private VideoHolder(@NonNull View itemView) {
            super(itemView);
            playVideo = (TextView) itemView.findViewById(R.id.play_video);
        }

    }
}
