package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ViewFaculty extends AppCompatActivity {
    TextView id,name,course,phone,email,password,code;
    RequestQueue requestQueue;
    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);
        config = new Config();
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        course = findViewById(R.id.course);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        code = findViewById(R.id.code);
        requestQueue = Volley.newRequestQueue(ViewFaculty.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "getDetails.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Json Admin",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() < 1)
                    {
                        Toast.makeText(ViewFaculty.this, "No records to show", Toast.LENGTH_SHORT).show();
                    }
                    JSONObject get = jsonArray.getJSONObject(0);
                    name.append(get.getString("Name"));
                    id.append(get.getString("FacID"));
                    course.append(get.getString("course"));
                    phone.append(get.getString("phone"));
                    email.append(get.getString("email"));
                    byte[] data = Base64.decode(get.getString("password"), Base64.DEFAULT);
                    String text = new String(data, "UTF-8");
                    password.append(text);
                    code.append(get.getString("course_code"));


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
                params.put("facid", getIntent().getExtras().getString("id"));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
