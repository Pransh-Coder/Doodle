package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Login extends AppCompatActivity {

    CircleImageView profile_image_faculty , profile_image_student , profile_image_admin;
    TextView txt_who;
    EditText edtMail,edtPassword;
    int user = 0; //student = 0 faculty = 1 admin = 2
    Button btnAuth;
    Config config;
    RequestQueue requestQueue;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    int isLogin;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        profile_image_faculty = findViewById(R.id.profile_image_teacher);
        profile_image_student = findViewById(R.id.profile_image_student);
        profile_image_admin = findViewById(R.id.profile_image_admin);

        sharedPreferences = getSharedPreferences("Login",0);
        isLogin = sharedPreferences.getInt("isLogin",0);
        user = sharedPreferences.getInt("User",0);

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Logging in");

        edtMail = findViewById(R.id.edt_mail);
        edtPassword = findViewById(R.id.edt_password);

        txt_who = findViewById(R.id.txt_who);

        btnAuth = findViewById(R.id.btnAuth);

        linearLayout = findViewById(R.id.linearLayout);

        requestQueue = Volley.newRequestQueue(Login.this);

        config = new Config();

        if (isLogin == 1 && user == 0)
        {
            Intent intent = new Intent(Login.this,Student_dashboard.class);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();
        }

        if (isLogin == 1 && user == 1)
        {
            Intent intent = new Intent(Login.this,FacultyDashboard.class);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();
        }

        if (isLogin == 1 && user == 2)
        {
            Intent intent = new Intent(Login.this,AdminControl.class);
            startActivityForResult(intent, 0);
            overridePendingTransition(0, 0);
            finish();
        }

        profile_image_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_who.setText("Faculty Login");
                user = 1;
            }
        });

        profile_image_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_who.setText("Student Login");
                user = 0;
            }
        });

        profile_image_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_who.setText("Admin Login");
                user = 2;
            }
        });

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (user == 0)
                {
                    studentLogin();
                }
                else if (user == 1)
                {
                    facultyLogin();
                }
                else if (user == 2)
                {
                    adminLogin();
                }
                else {
                    Toast.makeText(Login.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void adminLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "Admin_login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    Log.e("Json Admin",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("Status");
                    if (status.equals("Success"))
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("isLogin",1);
                        editor.putInt("User",user);
                        editor.commit();
                        Intent intent = new Intent(Login.this,AdminControl.class);
                        startActivityForResult(intent, 0);
                        overridePendingTransition(0, 0);
                        finish();

                    }
                    else {
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "Wrong Username Or Password", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Admin",error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", edtMail.getText().toString());
                params.put("password",edtPassword.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void facultyLogin() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "facultyLogin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    Log.e("Json Student",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("Status");
                    if (status.equals("Success"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject student = jsonArray.getJSONObject(0);
                        String FacID = student.getString("FacID");
                        String name = student.getString("Name");
                        String courseCode = student.getString("course_code");
                        String phone = student.getString("phone");
                        String course = student.getString("course");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("isLogin",1);
                        editor.putInt("User",user);
                        editor.putString("FacultyID",FacID);
                        editor.putString("name",name);
                        editor.putString("course_code",courseCode);
                        editor.putString("phone",phone);
                        editor.putString("course",course);
                        editor.putString("email",edtMail.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(Login.this,FacultyDashboard.class);
                        startActivityForResult(intent, 0);
                        overridePendingTransition(0, 0);
                        finish();

                    }
                    else {
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "Wrong Username Or Password", Snackbar.LENGTH_LONG);
                        snackbar.show();
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
                params.put("email", edtMail.getText().toString());
                params.put("password",edtPassword.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void studentLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "Login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    Log.e("Json Student",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("Status");
                    if (status.equals("Success"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject student = jsonArray.getJSONObject(0);
                        String Stndid = student.getString("Stndid");
                        String name = student.getString("name");
                        String Class = student.getString("class");
                        String phone = student.getString("phone");
                        String course = student.getString("course");
                        int paid = student.getInt("paid");
                        int remaining = student.getInt("remaining");
                        String interest = student.getString("interest");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("isLogin",1);
                        editor.putInt("User",user);
                        editor.putString("StudentID",Stndid);
                        editor.putString("name",name);
                        editor.putString("Class",Class);
                        editor.putString("phone",phone);
                        editor.putString("course",course);
                        editor.putString("interest",interest);
                        editor.putString("email",edtMail.getText().toString());
                        editor.putInt("paid",paid);
                        editor.putInt("remaining",remaining);
                        editor.commit();
                        Intent intent = new Intent(Login.this,Student_dashboard.class);
                        startActivityForResult(intent, 0);
                        overridePendingTransition(0, 0);
                        finish();

                    }
                    else {
                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "Wrong Username Or Password", Snackbar.LENGTH_LONG);
                        snackbar.show();
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
                params.put("email", edtMail.getText().toString());
                params.put("password",edtPassword.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
