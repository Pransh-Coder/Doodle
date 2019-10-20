package com.proxima.elearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ArchivedNotification extends AppCompatActivity {
    SwipeMenuListView listView;
    DatabaseHelper myDb;
    String id,title,message,created_at;
    ArrayList<notification_data> notificationData;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_back:
                    Intent intent=new Intent(ArchivedNotification.this,Main2Activity.class);
                    startActivity(intent);
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
        setContentView(R.layout.activity_archived_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        listView=findViewById(R.id.listView);
        myDb=new DatabaseHelper(this);

        notificationData=new ArrayList<>();
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "archive" item
                SwipeMenuItem archiveItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                archiveItem.setBackground(R.color.white);
                // set item width
                archiveItem.setWidth((180));
                // set a icon
                archiveItem.setIcon(R.drawable.ic_delete_forever_red_24dp);
                // add to menu
                menu.addMenuItem(archiveItem);
            }
        };
        listView.setMenuCreator(creator);
        Cursor res =  myDb.getAllData();
        if (res.getCount()==0)
        {
            showMessage("Error","No Archived Notifications");
        }
        while (res.moveToNext())
        {
            notificationData.add(new notification_data(res.getString(0),res.getString(3),res.getString(1),res.getString(2)));

        }
        Adapter_notification adapter_notification=new Adapter_notification(ArchivedNotification.this,ArchivedNotification.this,notificationData,listView);
        listView.setAdapter(adapter_notification);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ArchivedNotification.this,notification_info.class);
                intent.putExtra("data",notificationData.get(position).getMessage());
                intent.putExtra("title",notificationData.get(position).getTitle());
                intent.putExtra("date",notificationData.get(position).getCreated_at());
                intent.putExtra("entry",2);
                startActivity(intent);
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.d("clicked on item", "onMenuItemClick: clicked item " + position+"  id:"+notificationData.get(position).getId());
                        boolean isInserted = myDb.insertData(notificationData.get(position).getId(),notificationData.get(position).getTitle(),notificationData.get(position).getMessage(),notificationData.get(position).getCreated_at());
                        if (isInserted)
                        {
                            Toast.makeText(ArchivedNotification.this, "Notification Deleted Successfully", Toast.LENGTH_SHORT).show();
                            myDb.deleteData(notificationData.get(position).getId());
                            finish();
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                        else {
                            Toast.makeText(ArchivedNotification.this, "Notification can't be deleted", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Log.d("No","No item clicked");
                        break;
                }
                return false;
            }
        });

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
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
