package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class QuestionDetails extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView q_title,q_body,asker_name;
    String title="",ques="",name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);

        q_title = findViewById(R.id.ques_title);
        q_body = findViewById(R.id.showQues);
        asker_name = findViewById(R.id.showName);

        requestQueue= Volley.newRequestQueue(this);
        final Intent intent = getIntent();           // Receiving data from RecyclerAdapterTopseller (id) in ShowData activity
        final String ids = intent.getStringExtra("id");

        showQuesDetails();
    }

    private void showQuesDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://paytmpay001.dx.am/api/raeces/Questions.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(QuestionDetails.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);  // converting string response into Jsonobject
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject data = jsonArray.getJSONObject(i);

                         title=data.getString("title");
                         ques=data.getString("question");
                         name=data.getString("name");

                        System.out.println(title);
                        //q_title.setText(""+title);
                        /*q_body.setText(""+ques);
                        asker_name.setText(""+name);*/

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QuestionDetails.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}
