package com.proxima.elearning;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentAdapter extends BaseAdapter {

    Context context;
    TextView ordid,stid,name,amount,date,txnid;
    ArrayList<DataPayment> dataPayments;
    LayoutInflater layoutInflater;
    Activity activity;
    Config config;
    public PaymentAdapter(Context context, ArrayList<DataPayment> dataPayments, Activity activity) {
        this.context = context;
        this.dataPayments = dataPayments;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        config = new Config();
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return dataPayments.size();
    }

    @Override
    public Object getItem(int position) {
        return dataPayments.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.payment_layout,null);
        ordid = convertView.findViewById(R.id.ordid);
        stid = convertView.findViewById(R.id.stid);
        name = convertView.findViewById(R.id.name);
        amount = convertView.findViewById(R.id.amount);
        date = convertView.findViewById(R.id.date);
        txnid = convertView.findViewById(R.id.txnid);
        ordid.setText(dataPayments.get(position).getOrderId());
        stid.setText(dataPayments.get(position).getStndid());
        name.setText(dataPayments.get(position).getName());
        amount.setText(dataPayments.get(position).getAmount());
        date.setText(dataPayments.get(position).getDate());
        txnid.setText(dataPayments.get(position).getTxnid());

        return convertView;

    }
}
