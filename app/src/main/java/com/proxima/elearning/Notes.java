package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notes extends AppCompatActivity {

    ListView listView;
    ImageView newNote;
    RequestQueue requestQueue;
    Config config;
    String studentId;
    SharedPreferences sharedPreferences;
    ArrayList<GetNotes> getNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        listView = findViewById(R.id.listView);
        config = new Config();
        getNotes = new ArrayList<GetNotes>();
        sharedPreferences = getSharedPreferences("Login",0);
        studentId =sharedPreferences.getString("StudentID","");
        findViewById(R.id.newNotes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notes.this,CreateNote.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        requestQueue = Volley.newRequestQueue(Notes.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "Notes.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("String Notes",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("Json Notes",jsonObject.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject notesData = jsonArray.getJSONObject(i);
                        String chapter = notesData.getString("Chapter");
                        String topic = notesData.getString("Topic");
                        String note = notesData.getString("Note");
                        getNotes.add(new GetNotes(chapter,topic,note));
                    }
                    if (getNotes!=null)
                    {
                        NotesAdapter notesAdapter = new NotesAdapter(Notes.this,getNotes);
                        listView.setAdapter(notesAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("stndid",studentId);
                return params;

            }
        };
        requestQueue.add(stringRequest);

    }
}
