package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class PostingTotalMarks extends AppCompatActivity {

    EditText TMarksA1, TMarksA2, TMarksMid, TMarksFin;
    Button postBtn;
    SharedPreferences sharedPreference;
    RequestQueue requestQueue;
    String Course;

    String TMA1, TMA2, TMmid, TMfin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_total_marks);

        TMarksA1 = findViewById(R.id.MarksAss1);
        TMarksA2 = findViewById(R.id.MarksAss2);
        TMarksMid = findViewById(R.id.MarksMid);
        TMarksFin = findViewById(R.id.MarksFin);
        postBtn = findViewById(R.id.PostButton);

        requestQueue = Volley.newRequestQueue(this);

        sharedPreference = getApplication().getSharedPreferences("Login", MODE_PRIVATE);
        Course = sharedPreference.getString("course", "");
        //Toast.makeText(this, "" + Course, Toast.LENGTH_SHORT).show();

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TMA1 = TMarksA1.getText().toString();
                TMA2 = TMarksA2.getText().toString();
                TMmid = TMarksMid.getText().toString();
                TMfin = TMarksFin.getText().toString();

                if (TMarksA1.getText().length() == 0) {
                    TMarksA1.setError("Title field is empty!");
                } else if (TMarksA2.getText().length() == 0) {
                    TMarksA2.setError("Question body is Empty!");
                } else if (TMarksMid.getText().length() == 0) {
                    TMarksMid.setError("Question body is Empty!");
                } else if (TMarksFin.getText().length() == 0) {
                    TMarksFin.setError("Question body is Empty!");
                }
                else {
                    postTotalMarks();
                    TMarksA1.getText().clear();
                    TMarksA2.getText().clear();
                    TMarksMid.getText().clear();
                    TMarksFin.getText().clear();
                }
            }
        });
    }

    private void postTotalMarks() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://paytmpay001.dx.am/api/raeces/totalMarks.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(PostingTotalMarks.this, "" + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostingTotalMarks.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {             // we are sending this data so that at the database we can check whether we have the same data or not i.e for matching
                HashMap<String, String> map = new HashMap<>();
                map.put("a1", TMA1);
                map.put("a2", TMA2);
                map.put("mid", TMmid);
                map.put("final", TMfin);
                map.put("course", Course);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
