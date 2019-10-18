package com.proxima.elearning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapterVideos extends RecyclerView.Adapter<RecyclerAdapterVideos.ViewHolder> {

    Context context;
    List<Videos> videosList;

    public RecyclerAdapterVideos(Context context, List<Videos> videosList) {
        this.context = context;
        this.videosList = videosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list,parent,false);
        return new RecyclerAdapterVideos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.video.loadData(videosList.get(position).getVideourl(),"text/html","utf-8");
        holder.video.loadUrl(videosList.get(position).getVideourl());
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{

        WebView video;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            video = itemView.findViewById(R.id.Web_View);
            video.getSettings().setJavaScriptEnabled(true);
            video.setWebChromeClient(new WebChromeClient());
        }
    }
}
