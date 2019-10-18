package com.proxima.elearning;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        holder.webView.setWebViewClient(new myWebClient());
        WebSettings webSettings=holder.webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.loadData(videosList.get(position).getVideourl(),"text/html","utf-8");
//        holder.webView.loadUrl(videosList.get(position).getVideourl());
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        WebView webView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.Web_View);


            //webSettings.setBuiltInZoomControls(true);
        }
    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;

        }
    }
}
