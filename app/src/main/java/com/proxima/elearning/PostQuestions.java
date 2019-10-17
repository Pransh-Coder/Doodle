package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PostQuestions extends AppCompatActivity {

    EditText ques_title,question_body,q_name,q_asked_by;
    Button submitQues;
    RequestQueue requestQueue;

    String qu_title,qu_body,qu_name,qu_asked_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_questions);

        requestQueue= Volley.newRequestQueue(this);

        ques_title = findViewById(R.id.q_title);
        question_body=findViewById(R.id.q_ques);
        q_name = findViewById(R.id.q_name);
        q_asked_by=findViewById(R.id.asked_by);
        submitQues = findViewById(R.id.submit_ques);

        submitQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //we have made this becoz hashmap takes 1 argument as string so we will use below values
                qu_title=ques_title.getText().toString();
                qu_body=question_body.getText().toString();
                qu_name=q_name.getText().toString();
                qu_asked_by=q_asked_by.getText().toString();

                if(ques_title.getText().length()==0)
                {
                  ques_title.setError("Title field is empty!");
                }
                else if(question_body.getText().length()==0)
                {
                    question_body.setError("Question body is Empty!");
                }
                else if(q_name.getText().length()==0)
                {
                    q_name.setError("Name field is empty!");
                }
                else if(q_asked_by.getText().length()==0)
                {
                    q_asked_by.setError("Question asked by whom field is empty!");
                }
                else{
                    postQues();
                }
            }
        });
    }

    private void postQues(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://paytmpay001.dx.am/api/raeces/Question_post.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PostQuestions.this, ""+response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostQuestions.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {             // we are sending this data so that at the database we can check whether we have the same data or not i.e for matching
                HashMap<String,String> map=new HashMap<>();
                map.put("title",qu_title);
                map.put("question",qu_body);
                map.put("name",qu_name);
                map.put("asked_by",qu_asked_by);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
