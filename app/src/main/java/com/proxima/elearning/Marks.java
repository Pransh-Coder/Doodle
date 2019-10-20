package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Marks extends AppCompatActivity {

    SharedPreferences sharedPreference;
    RequestQueue requestQueue;
    String s_id;
    TextView ass1,ass2,midM,finalM,course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        requestQueue = Volley.newRequestQueue(this);
        ass1 = findViewById(R.id.A1);
        ass2=findViewById(R.id.A2);
        midM=findViewById(R.id.mid);
        course=findViewById(R.id.course);
        finalM=findViewById(R.id.finalMarks);


        sharedPreference = getApplication().getSharedPreferences("Login", MODE_PRIVATE);
        s_id = sharedPreference.getString("StudentID", "");
        System.out.println(s_id);
        Toast.makeText(this, "" + s_id, Toast.LENGTH_SHORT).show();
        Log.e("Marks",s_id);
        jsonParse();
    }

    public void JsonParse() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,"http://paytmpay001.dx.am/api/raeces/Marks.php",null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //textView.setText(response.toString());
                Toast.makeText(Marks.this, ""+response, Toast.LENGTH_SHORT).show();

                for(int i=0;i<response.length();i++)
                {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(i);
                        String s = jsonObject.getString("marksa1");
                        Toast.makeText(Marks.this, ""+s, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Marks.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("stndid ",s_id);
                Log.e("Map",s_id+" abc");
                return map;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    public  void jsonParse(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://paytmpay001.dx.am/api/raeces/Marks.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Response",response);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String a1=  jsonObject.getString("marksa1");
                        String mid = jsonObject.getString("marksmid");
                        String a2= jsonObject.getString("marksa2");
                        String f= jsonObject.getString("marksf");
                        String c= jsonObject.getString("course");

                        course.append(c);
                        ass1.append(" "+a1+"/10");
                        ass2.append(" "+a2+"/10");
                        midM.append(" "+mid+"/20");
                        finalM.append(" "+f+"/60");
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Marks.this, ""+e, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Marks.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("stndid",s_id);
                Log.e("Map",s_id+" abc");
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
}
