package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import java.io.IOException;

public class Pdf extends AppCompatActivity {

    static WebView webView;
    static ProgressDialog progressDialog;
    static String url1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        progressDialog = new ProgressDialog(Pdf.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new myWebClient());
        WebSettings webSettings=webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        url1 = getIntent().getExtras().getString("url");
        new MyAsyncTask().execute();
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
    private static class MyAsyncTask extends AsyncTask<Void, Void, Document> {
        @Override
        protected Document doInBackground(Void... voids) {

            Document document = null;

            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            //webView.setWebViewClient(new WebViewClient());
            WebSettings webSettings=webView.getSettings();
            //webSettings.setBuiltInZoomControls(true);
            webView.loadUrl(url1);
            //webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            webSettings.setJavaScriptEnabled(true);
            progressDialog.dismiss();
        }
    }
}
