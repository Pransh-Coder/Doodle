package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class GiveMarks extends AppCompatActivity {

    EditText MarksA1, MarksA2, MarksMid, MarksFin;
    Button postMarksBtn;

    RequestQueue requestQueue;

    String MA1, MA2, Mmid, Mfin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_marks);

        MarksA1 = findViewById(R.id.Assg1);
        MarksA2 = findViewById(R.id.Assg2);
        MarksMid = findViewById(R.id.Mid);
        MarksFin = findViewById(R.id.Fin);
        postMarksBtn = findViewById(R.id.PostMarksButton);

        requestQueue = Volley.newRequestQueue(this);
        final Intent intent = getIntent();
        final String ids = intent.getStringExtra("Student_id");  // getting ids from RecyclerAdapterAttendance

        postMarksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MA1 = MarksA1.getText().toString();
                MA2 = MarksA2.getText().toString();
                Mmid = MarksMid.getText().toString();
                Mfin = MarksFin.getText().toString();

                if (MarksA1.getText().length() == 0) {
                    MarksA1.setError("Title field is empty!");
                } else if (MarksA2.getText().length() == 0) {
                    MarksA2.setError("Question body is Empty!");
                } else if (MarksMid.getText().length() == 0) {
                    MarksMid.setError("Question body is Empty!");
                } else if (MarksFin.getText().length() == 0) {
                    MarksFin.setError("Question body is Empty!");
                }
                else {
                    postMarks(ids);
                    MarksA1.getText().clear();
                    MarksA2.getText().clear();
                    MarksMid.getText().clear();
                    MarksFin.getText().clear();
                }
            }
        });
    }

    private void postMarks(final String ids) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://paytmpay001.dx.am/api/raeces/studentMarks.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(GiveMarks.this, "" + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GiveMarks.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {             // we are sending this data so that at the database we can check whether we have the same data or not i.e for matching
                HashMap<String, String> map = new HashMap<>();
                map.put("a1", MA1);
                map.put("a2", MA2);
                map.put("mid", Mmid);
                map.put("final", Mfin);
                map.put("id", ids);                // id needs to come from intent
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
