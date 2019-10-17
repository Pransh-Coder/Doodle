package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StudyMaterial extends AppCompatActivity {

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material);
        findViewById(R.id.python).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMaterial.this,Pdf.class);
                url = "https://docs.google.com/gview?embedded=true&url=http://paytmpay001.dx.am/api/raeces/Notes/Python.pdf";
                intent.putExtra("url",url);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.cpp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMaterial.this,Pdf.class);
                url = "https://docs.google.com/gview?embedded=true&url=http://paytmpay001.dx.am/api/raeces/Notes/C_CPP.pdf";
                intent.putExtra("url",url);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.ccc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMaterial.this,Pdf.class);
                url = "https://docs.google.com/gview?embedded=true&url=http://paytmpay001.dx.am/api/raeces/Notes/CCC.pdf";
                intent.putExtra("url",url);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.html).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyMaterial.this,Pdf.class);
                url = "https://docs.google.com/gview?embedded=true&url=http://paytmpay001.dx.am/api/raeces/Notes/HTML.pdf";
                intent.putExtra("url",url);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
    }

}
