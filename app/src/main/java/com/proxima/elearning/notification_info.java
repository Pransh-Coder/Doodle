package com.proxima.elearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class notification_info extends AppCompatActivity {
    private TextView mTextMessage,tdate,tdata;
    int entry;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_back:
                    if (entry==1) {
                        Intent intent = new Intent(notification_info.this, Main2Activity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(notification_info.this, ArchivedNotification.class);
                        startActivity(intent);
                    }
                    return true;
                case R.id.navigation_help:
                    openWhatsApp();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_info);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        tdata=findViewById(R.id.tinfo);
        tdate=findViewById(R.id.tdate);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mTextMessage.setText(bundle.getString("title"));
        entry=bundle.getInt("entry");
        tdate.setText(bundle.getString("date"));
        tdata.setText(bundle.getString("data"));
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
