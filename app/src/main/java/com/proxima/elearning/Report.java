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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Report extends AppCompatActivity {

    EditText edtSubject,edtDesc;

    Config config;

    Button btnUnknown;

    SharedPreferences sharedPreferences;

    String name;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        sharedPreferences = getSharedPreferences("Login",0);

        name = sharedPreferences.getString("name","");
        edtDesc = findViewById(R.id.edtDesc);
        edtSubject = findViewById(R.id.edtSubject);

        requestQueue = Volley.newRequestQueue(Report.this);

        btnUnknown = findViewById(R.id.btnUnknown);

        config = new Config();

        btnUnknown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnUnknown.getText().toString().equals("ANONYMOUS"))
                {
                    name = "ANONYMOUS";
                    btnUnknown.setText("PUBLIC");
                }
                else {
                    name = sharedPreferences.getString("name","");
                    btnUnknown.setText("ANONYMOUS");
                }
            }
        });

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "report_post.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("Json Report",response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("Status");
                            if (status.equals("Success"))
                            {
                                Toast.makeText(Report.this, "Successfully Reported", Toast.LENGTH_SHORT).show();


                            }
                            else {
                                Toast.makeText(Report.this, "Report Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Report",error.toString());

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("subject", edtSubject.getText().toString());
                        params.put("desc",edtDesc.getText().toString());
                        params.put("name",name);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
                Intent intent = new Intent(Report.this,Student_dashboard.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });

    }
}
