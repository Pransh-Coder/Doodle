package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckPayments extends AppCompatActivity {
    ListView listView;
    Config config;
    PaymentAdapter reportAdapter;
    RequestQueue requestQueue;
    ArrayList<DataPayment> dataPayments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_payments);
        listView = findViewById(R.id.listView);
        config = new Config();
        dataPayments = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(CheckPayments.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, config.baseUrl + "PaymentDetails.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("Array",response.toString());
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        dataPayments.add(new DataPayment("Order ID :   "+jsonObject.getString("order_id"),"Student ID :   "+jsonObject.getString("student_id"),"Student Name :   "+jsonObject.getString("name"),"Amount :   "+jsonObject.getString("amount"),"Date :   "+jsonObject.getString("date"),"Transaction ID :  "+jsonObject.getString("txnid")));
                    }
                    if (dataPayments != null)
                    {
                        reportAdapter = new PaymentAdapter(CheckPayments.this,dataPayments,CheckPayments.this);
                        listView.setAdapter(reportAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
