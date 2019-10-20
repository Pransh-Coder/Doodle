package com.proxima.elearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    SwipeMenuListView listView;
    DatabaseHelper myDb;
    int flag=0;
    boolean new_user=true;
    Config config;
    String id,title,message,created_at;
    ArrayList<notification_data> notificationData;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent=new Intent(Main2Activity.this,Student_dashboard.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_archive:
                    Cursor res =  myDb.getAllData();
                    if (res.getCount() == 0)
                    {
                        showMessage("Error","No Archived Notifications");

                    }
                    else {
                        Intent intent1=new Intent(Main2Activity.this,ArchivedNotification.class);
                        startActivity(intent1);
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
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        config = new Config();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        notificationData=new ArrayList<>();
        listView=findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getSharedPreferences("new_user",0);
        new_user = sharedPreferences.getBoolean("isNewUser",true);
        if ( new_user )
        {
            Calendar c = Calendar.getInstance();
            int month=c.get(Calendar.MONTH)+1;
            String sDate = c.get(Calendar.YEAR) + "-"
                    + month
                    + "-" + c.get(Calendar.DAY_OF_MONTH)+" "+c.get(Calendar.HOUR_OF_DAY)+
                    ":"+c.get(Calendar.MINUTE)+
                    ":"+c.get(Calendar.SECOND);

            notificationData.add(new notification_data("0",sDate,"Welcome At Doodle","We welcomes you to the Doodle an online e learning platform for Apeejay Stya University"));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isNewUser",false);
            editor.commit();
        }
        myDb= new DatabaseHelper(this);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "archive" item
                SwipeMenuItem archiveItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                archiveItem.setBackground(R.color.archive_green);
                // set item width
                archiveItem.setWidth((180));
                // set a icon
                archiveItem.setIcon(R.drawable.ic_archive_black_24dp);
                // add to menu
                menu.addMenuItem(archiveItem);
            }
        };
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.d("clicked on item", "onMenuItemClick: clicked item " + position+"  id:"+notificationData.get(position).getId());
                        String ID=notificationData.get(position).getId();
                        String Title=notificationData.get(position).getTitle();
                        String Message=notificationData.get(position).getMessage();
                        String Created_At=notificationData.get(position).getCreated_at();
                        Cursor res =  myDb.getAllData();
                        while (res.moveToNext())
                        {
                            if(ID.equals(res.getString(0)))
                            {
                                flag=-1;
                            }

                        }
                        if (flag==-1)
                        {
                            Toast.makeText(Main2Activity.this, "Notification Already Archived", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            boolean isInserted = myDb.insertData(ID, Title, Message, Created_At);
                            if (isInserted) {
                                Toast.makeText(Main2Activity.this, "Notification Archived Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Main2Activity.this, "Notification can't be archived", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    default:
                        Log.d("No","No item clicked");
                        break;
                }
                return false;
            }
        });

// set creator
        listView.setMenuCreator(creator);

        final RequestQueue queue= Volley.newRequestQueue(Main2Activity.this);
        final JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest(Request.Method.GET,config.baseUrl+"notification_get.php" , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("data");
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString("title");
                        message = jsonObject.getString("message");
                        created_at = jsonObject.getString("created_at");
                        id = jsonObject.getString("id");
                        notificationData.add(new notification_data(id,created_at,title,message));
                    }
                    Adapter_notification adapter_notification=new Adapter_notification(Main2Activity.this,Main2Activity.this,notificationData,listView);
                    listView.setAdapter(adapter_notification);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(jsonObjectRequest5);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Main2Activity.this,notification_info.class);
                intent.putExtra("data",notificationData.get(position).getMessage());
                intent.putExtra("title",notificationData.get(position).getTitle());
                intent.putExtra("date",notificationData.get(position).getCreated_at());
                intent.putExtra("entry",1);
                startActivity(intent);
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
