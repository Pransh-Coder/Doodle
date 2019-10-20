package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckReport extends AppCompatActivity {

    ListView listView;
    Config config;
    ReportAdapter reportAdapter;
    RequestQueue requestQueue;
    ArrayList<ReportData> reportData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_report);
        listView = findViewById(R.id.listView);
        config = new Config();
        reportData = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(CheckReport.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, config.baseUrl + "report_get.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("Array",response.toString());
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        reportData.add(new ReportData(jsonObject.getString("Subject"),jsonObject.getString("Description"),jsonObject.getString("Report_by"),jsonObject.getString("Solved"),jsonObject.getString("ID")));
                    }
                    if (reportData != null)
                    {
                        reportAdapter = new ReportAdapter(CheckReport.this,reportData,CheckReport.this);
                        listView.setAdapter(reportAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
