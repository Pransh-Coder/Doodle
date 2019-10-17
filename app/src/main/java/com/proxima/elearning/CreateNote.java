package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateNote extends AppCompatActivity {

    EditText edtChapter,edtTopic,edtNote;
    Button btnNewNote;
    Config config;
    String studentId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        edtChapter = findViewById(R.id.edtChapter);
        edtTopic = findViewById(R.id.edtTopic);
        edtNote = findViewById(R.id.edtNotes);

        config = new Config();

        btnNewNote = findViewById(R.id.btnNewNote);
        sharedPreferences = getSharedPreferences("Login",0);
        studentId = sharedPreferences.getString("StudentID","");

        btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();
            }
        });
    }

    private void newNote() {

        RequestQueue requestQueue = Volley.newRequestQueue(CreateNote.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "createnote.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Create note ",response);
                Toast.makeText(CreateNote.this, "Note has been created successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateNote.this,Notes.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
                finish();

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
                params.put("chapter",edtChapter.getText().toString());
                params.put("topic",edtTopic.getText().toString());
                params.put("note",edtNote.getText().toString());
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }
}
