package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostingMarks extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RequestQueue requestQueue;
    String Course;

    List<AttendanceModelClass> attendanceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_marks);

        requestQueue= Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.recyclerV);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        sharedpreferences = getApplication().getSharedPreferences("Login", MODE_PRIVATE);
        Course = sharedpreferences.getString("course", "");
        Toast.makeText(this, "" + Course, Toast.LENGTH_SHORT).show();

        studentDetails(Course);
    }

    private void studentDetails(final String Course)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://paytmpay001.dx.am/api/raeces/MarkAttendance.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1 =jsonArray.getJSONObject(i);

                        final AttendanceModelClass  attendance =new AttendanceModelClass();

                        attendance.setId(jsonObject1.getString("Stndid"));
                        attendance.setName(jsonObject1.getString("Name"));

                        attendanceList.add(attendance);
                    }
                    adapter = new RecyclerAdapterAttendance(PostingMarks.this, attendanceList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PostingMarks.this, ""+e, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostingMarks.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {             // we are sending this data so that at the database we can check whether we have the same data or not i.e for matching
                HashMap<String,String> map=new HashMap<>();
                map.put("course",Course);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
