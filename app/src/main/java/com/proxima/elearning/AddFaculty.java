package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class AddFaculty extends AppCompatActivity {

    EditText edtName,edtCode,edtPhone,edtEmail,edtPassword;
    AutoCompleteTextView autoCourse;
    String [] course;
    RequestQueue requestQueue;
    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        edtName = findViewById(R.id.edtName);
        edtCode = findViewById(R.id.edtCode);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        autoCourse = findViewById(R.id.autoCourse);
        requestQueue = Volley.newRequestQueue(AddFaculty.this);
        config = new Config();
        course = new String[]{"Python","C & C++","CCC","HTML"};
        ArrayAdapter<String> adapter_color = new ArrayAdapter<String>(AddFaculty.this, android.R.layout.select_dialog_item, course);
        autoCourse.setThreshold(0);
        autoCourse.setAdapter(adapter_color);
        autoCourse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCourse.showDropDown();
                return false;
            }
        });

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "Faculty.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("Json Admin",response);
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("success");
                            if (status==1)
                            {
                                Toast.makeText(AddFaculty.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(AddFaculty.this, "Student not Added", Toast.LENGTH_SHORT).show();
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
                        params.put("email", edtEmail.getText().toString());
                        params.put("Name", edtName.getText().toString());
                        params.put("password",edtPassword.getText().toString());
                        params.put("course_code",edtCode.getText().toString());
                        params.put("phone",edtPhone.getText().toString());
                        params.put("course",autoCourse.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
                Intent intent = new Intent(AddFaculty.this,AdminControl.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
                finish();
            }
        });

    }
}
