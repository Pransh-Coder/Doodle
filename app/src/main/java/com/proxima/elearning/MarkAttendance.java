package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarkAttendance extends AppCompatActivity {

    ListView listView;
    ArrayList<GetAttendance> getAttendances;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        listView = findViewById(R.id.listView);
        final Config config = new Config();
        getAttendances = new ArrayList<>();
        findViewById(R.id.btnAddDay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue requestQueue = Volley.newRequestQueue(MarkAttendance.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "cou.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.e("Json Admin",response);



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Admin",error.toString());

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("course", sharedPreferences.getString("course",""));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MarkAttendance.this);
        sharedPreferences = getSharedPreferences("Login",0);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "MarkAttendance.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Json Admin",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        getAttendances.add(new GetAttendance(jsonObject1.getString("Stndid"),jsonObject1.getString("Name")));
                    }
                    if (getAttendances != null)
                    {
                        AttendanceAdapter attendanceAdapter = new AttendanceAdapter(MarkAttendance.this,getAttendances);
                        listView.setAdapter(attendanceAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Admin",error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("course", sharedPreferences.getString("course",""));
                return params;
            }
        };
        requestQueue.add(stringRequest);
        AttendanceAdapter attendanceAdapter = new AttendanceAdapter(MarkAttendance.this,getAttendances);

    }
}
