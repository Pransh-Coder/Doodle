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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    EditText oldPassword,newPassword,confirmPassword;
    Button btnChange;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        final RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
        sharedPreferences = getSharedPreferences("Login",0);
        final Config config = new Config();

        newPassword = findViewById(R.id.newPassword);
        oldPassword = findViewById(R.id.oldPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        btnChange = findViewById(R.id.btnConfirm);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (newPassword.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "changepassword.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.e("Json Student",response);
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("Status");
                                if (status.equals("Success"))
                                {
                                    Toast.makeText(ChangePassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ChangePassword.this,Student_dashboard.class);
                                    startActivityForResult(intent,0);
                                    overridePendingTransition(0,0);
                                }
                                else {
                                    Toast.makeText(ChangePassword.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error Student",error.toString());

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            Log.e("id",sharedPreferences.getString("StudentID","")+"abc");
                            params.put("id",sharedPreferences.getString("StudentID",""));
                            params.put("oldpassword", oldPassword.getText().toString());
                            params.put("newpassword",newPassword.getText().toString());
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }

                else {
                    Toast.makeText(ChangePassword.this, "New password and confirm password did not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
