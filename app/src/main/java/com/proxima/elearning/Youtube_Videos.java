package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Vector;

public class Youtube_Videos extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Vector<Videos> videosList = new Vector<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube__videos);

        recyclerView = findViewById(R.id.Recycler_View);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        videosList.add(new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/_uQrJ0TkZlc\" frameborder=\"0\" allowfullscreen></iframe>"));
        videosList.add(new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/00EbvyLlAJo\" frameborder=\"0\" allowfullscreen></iframe>"));
        videosList.add(new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/mU6anWqZJcc\" frameborder=\"0\" allowfullscreen></iframe>"));
        videosList.add(new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/vLnPwxZdW4Y\" frameborder=\"0\" allowfullscreen></iframe>"));
        videosList.add(new Videos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/aYSgpR5VGPM\" frameborder=\"0\" allowfullscreen></iframe>"));

        adapter=new RecyclerAdapterVideos(Youtube_Videos.this,videosList);
        recyclerView.setAdapter(adapter);

    }
}
