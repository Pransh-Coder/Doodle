package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutStudent extends AppCompatActivity {

    TextView tvName,tvEmail,tvPhone,tvClass,tvCourse,tvInterest;
    SharedPreferences sharedPreferences;
    String name,email,phone,Class,course,interest;
    Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_student);
        tvClass = findViewById(R.id.tvClass);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvCourse = findViewById(R.id.tvCourse);
        tvInterest = findViewById(R.id.tvInterest);
        getData();
        tvName.setText(name);
        tvClass.setText(Class);
        tvCourse.setText(course);
        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvInterest.setText(interest);
        findViewById(R.id.change_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutStudent.this,ChangePassword.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
    }

    private void getData() {
        sharedPreferences = getSharedPreferences("Login",0);
        name=sharedPreferences.getString("name","");
        Class=sharedPreferences.getString("Class","");
        phone=sharedPreferences.getString("phone","");
        course=sharedPreferences.getString("course","");
        email=sharedPreferences.getString("email","");
        interest=sharedPreferences.getString("interest","");
    }

}
