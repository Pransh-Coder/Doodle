package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Attendance extends AppCompatActivity {

    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    String stndid;
    Config config;
    int present,total;
    String strPresent,strTotal;
    private String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        config = new Config();
        sharedPreferences = getSharedPreferences("Login",0);
        requestQueue = Volley.newRequestQueue(Attendance.this);
        stndid = sharedPreferences.getString("StudentID","");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "studentattendance.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Json attendance",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    strPresent = jsonObject.getString("attendance");
                    strTotal = jsonObject.getString("Total");
                    course = jsonObject.getString("course");
                    present = Integer.parseInt(strPresent);
                    total = Integer.parseInt(strTotal);
                    Float percentage = (Float.valueOf(strPresent)/Float.valueOf(strTotal))*100;
                    TextView txtAtt,txtPer,txtCourse;
                    txtAtt = findViewById(R.id.txtatt);
                    txtCourse = findViewById(R.id.txtCourse);
                    txtPer = findViewById(R.id.txtper);
                    txtCourse.setText(course);
                    txtAtt.setText(strPresent+"/"+strTotal);
                    txtPer.setText(""+percentage+"%");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Attendance",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("stndid",stndid);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
