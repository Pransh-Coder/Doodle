package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDetails extends AppCompatActivity {

    RequestQueue requestQueue;
    TextView q_title,q_body,asker_name;
    String title="",ques="",name="";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Answer> answerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);

        q_title = findViewById(R.id.showTitle);
        q_body = findViewById(R.id.showQues);
        asker_name = findViewById(R.id.showName);

        recyclerView = findViewById(R.id.RecyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestQueue= Volley.newRequestQueue(this);
        final Intent intent = getIntent();           // Receiving data from RecyclerAdapterTopseller (id) in ShowData activity
        final String ids = intent.getStringExtra("id");

        showQuesDetails(ids);
    }

    private void showQuesDetails(final String ids) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://paytmpay001.dx.am/api/raeces/FetchAnswer.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(QuestionDetails.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);  // converting string response into Jsonobject
                    JSONArray jsonArray = jsonObject.getJSONArray("dataQuestion");
                    JSONArray jsonArrayAnswer = jsonObject.getJSONArray("dataAnswer");



                        JSONObject data = jsonArray.getJSONObject(0);

                         title=data.getString("title");
                         ques=data.getString("question");
                         name=data.getString("name");

                        System.out.println(title);
                        q_title.setText(""+title);
                        q_body.setText(""+ques);
                        asker_name.setText(""+name);

                        for (int i = 0 ; i < jsonArrayAnswer.length() ; i++)
                        {
                            JSONObject data1 = jsonArray.getJSONObject(i);

                            final Answer  answer =new Answer();

                            answer.setTitle(data1.getString("Answer_Title"));
                            answer.setAnswer(data1.getString("answer"));

                            answerList.add(answer);
                        }
                     if (answerList!=null) {
                         adapter = new RecyclerAdapterAnswers(QuestionDetails.this, answerList);
                         recyclerView.setAdapter(adapter);
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {             // we are sending this data so that at the database we can check whether we have the same data or not i.e for matching
                HashMap<String,String> map=new HashMap<>();
                map.put("question_id",ids);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
