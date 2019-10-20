package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ViewStudent extends AppCompatActivity {
    TextView id,name,Class,course,phone,email,password,interest,attendance,paid,remaining;
    RequestQueue requestQueue;
    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        config = new Config();
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        Class = findViewById(R.id.Class);
        course = findViewById(R.id.course);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        interest = findViewById(R.id.interest);
        attendance = findViewById(R.id.Attendance);
        paid = findViewById(R.id.paid);
        remaining = findViewById(R.id.remaining);
        requestQueue = Volley.newRequestQueue(ViewStudent.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "getDetails.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Json Admin",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() < 1)
                    {
                        Toast.makeText(ViewStudent.this, "No records to show", Toast.LENGTH_SHORT).show();
                    }
                    JSONObject get = jsonArray.getJSONObject(0);
                    name.append(get.getString("Name"));
                    id.append(get.getString("Stndid"));
                    Class.append(get.getString("Class"));
                    course.append(get.getString("Course"));
                    phone.append(get.getString("Phone"));
                    email.append(get.getString("Email"));
                    byte[] data = Base64.decode(get.getString("Password"), Base64.DEFAULT);
                    String text = new String(data, "UTF-8");
                    password.append(text);
                    interest.append(get.getString("Interest"));
                    paid.append(get.getString("Paid"));
                    remaining.append(get.getString("Remaining"));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
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
                params.put("stndid", getIntent().getExtras().getString("id"));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
