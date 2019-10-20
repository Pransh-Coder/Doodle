package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

public class AdminControl extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("Login",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(AdminControl.this,Login.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        findViewById(R.id.btnAddStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this,AddStudent.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.btnAddFaculty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this,AddFaculty.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.btnSendNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this,Announcements.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.btnCheckReports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this,CheckReport.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.btnViewStudent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this,EnterId.class);
                intent.putExtra("entry",0);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
        findViewById(R.id.btnViewFaculty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this,EnterId.class);
                intent.putExtra("entry",1);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });

        findViewById(R.id.btnCheckPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControl.this,CheckPayments.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
            }
        });
    }
}
