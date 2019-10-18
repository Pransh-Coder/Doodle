package com.proxima.elearning;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Discussion_forum extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    FloatingActionButton addQuestions;

    RequestQueue requestQueue;
    List<Questions> questionsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_forum);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_actionbar));
        }
        addQuestions=findViewById(R.id.fab);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        requestQueue= Volley.newRequestQueue(this);

        showQuestions();
        addQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Discussion_forum.this, "Button Pressed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Discussion_forum.this,PostQuestions.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showQuestions(){
        StringRequest request = new StringRequest(Request.Method.GET, "http://paytmpay001.dx.am/api/raeces/Questions.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);  // converting string response into Jsonobject
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject data = jsonArray.getJSONObject(i);

                        final Questions  questions =new Questions();

                        questions.setQues_id(data.getString("question_id"));
                        questions.setQuesTitle(data.getString("title"));
                        questions.setQuestions(data.getString("question"));
                        questions.setAuth_of_ques(data.getString("name"));          //Auth - author of ques

                        String id = data.getString("question_id");
                        //Toast.makeText(Discussion_forum.this, ""+id, Toast.LENGTH_SHORT).show();

                        sharedPreferences=getSharedPreferences("questions_id",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id",id);
                        editor.commit();
                        questionsList.add(questions);
                    }
                    adapter = new RecyclerAdapterDiscussion_forum(Discussion_forum.this,questionsList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }

}
