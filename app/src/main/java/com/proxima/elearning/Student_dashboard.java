package com.proxima.elearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Student_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        Button btnLogout;
        SharedPreferences sharedPreferences;
        DrawerLayout drawer;
        NavigationView navigationView;
        androidx.appcompat.app.ActionBarDrawerToggle toggle;

        TextView name;
        Button logout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View ab = navigationView.getHeaderView(0);
        Menu show = navigationView.getMenu();
        name = (TextView) ab.findViewById(R.id.name_user);
        logout = ab.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        sharedPreferences = getSharedPreferences("Login",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(Student_dashboard.this,Login.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(0, 0);
        finish();
        }
        });

        findViewById(R.id.Attendance).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(Student_dashboard.this,Attendance.class);
        startActivityForResult(intent,0);
        overridePendingTransition(0,0);
        }
        });

        findViewById(R.id.deatails).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(Student_dashboard.this,AboutStudent.class);
        startActivityForResult(intent,0);
        overridePendingTransition(0,0);
        }
        });

        findViewById(R.id.fees).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(Student_dashboard.this,FeeDetails.class);
        startActivityForResult(intent,0);
        overridePendingTransition(0,0);
        }
        });

        findViewById(R.id.aboutdoddle).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(Student_dashboard.this,AboutApp.class);
        startActivityForResult(intent,0);
        overridePendingTransition(0,0);
        }
        });

        findViewById(R.id.notes).setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent = new Intent(Student_dashboard.this,Notes.class);
        startActivityForResult(intent,0);
        overridePendingTransition(0,0);
        }
        });

        findViewById(R.id.discussion).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(Student_dashboard.this,Discussion_forum.class);
                        startActivityForResult(intent,0);
                        overridePendingTransition(0,0);
                }
        });

        findViewById(R.id.learning).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(Student_dashboard.this,StudyMaterial.class);
                        startActivityForResult(intent,0);
                        overridePendingTransition(0,0);
                }
        });

        findViewById(R.id.report).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(Student_dashboard.this,Report.class);
                        startActivityForResult(intent,0);
                        overridePendingTransition(0,0);
                }
        });

        findViewById(R.id.marks).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(Student_dashboard.this,Marks.class);
                        startActivityForResult(intent,0);
                        overridePendingTransition(0,0);
                }
        });

        findViewById(R.id.notification).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(Student_dashboard.this,Main2Activity.class);
                        startActivityForResult(intent,0);
                        overridePendingTransition(0,0);
                }
        });
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
        {
                if(menuItem.getItemId()==R.id.nav_complain)
                {
                        Intent intent = new Intent(this,Report.class);
                        startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.nav_contacu)
                {
                        openWhatsApp();
                }
                else if(menuItem.getItemId()==R.id.Tutorials)
                {
                        Intent intent = new Intent(this,Youtube_Videos.class);
                        startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.about)
                {
                        Intent intent = new Intent(this,AboutApp.class);
                        startActivity(intent);
                }
                else if(menuItem.getItemId()==R.id.social)
                {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://instagram.com"));
                        startActivity(intent);
                }
                else {
                        Toast.makeText(this, "We will be updating as soon as we deploy this app on Playstore!", Toast.LENGTH_SHORT).show();
                }
                return false;
        }

        public void openWhatsApp(){
                try {
                        String text = "Hello";// Replace with your message.

                        String toNumber = "919716838625"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
                        //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                        startActivity(intent);
                }
                catch (Exception e){
                        e.printStackTrace();
                }
        }

}
