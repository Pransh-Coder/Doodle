package com.proxima.elearning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class ReportAdapter extends BaseAdapter {

    Context context;
    TextView by,subject,desc;
    Button mark;
    ArrayList<ReportData> reportData;
    LayoutInflater layoutInflater;
    Activity activity;
    Config config;
    public ReportAdapter(Context context, ArrayList<ReportData> reportData, Activity activity) {
        this.context = context;
        this.reportData = reportData;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        config = new Config();
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return reportData.size();
    }

    @Override
    public Object getItem(int position) {
        return reportData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.report_layout,null);
        by = convertView.findViewById(R.id.report_by);
        subject = convertView.findViewById(R.id.subject);
        desc = convertView.findViewById(R.id.desc);
        mark = convertView.findViewById(R.id.btnSolved);
        by.setText(reportData.get(position).getReport_by());
        subject.setText(reportData.get(position).getSubject());
        desc.setText(reportData.get(position).getDescription());
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "report_solve.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.e("Json Data",response);



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Data",error.toString());

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", reportData.get(position).getId());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
                activity.finish();
                activity.startActivityForResult(activity.getIntent(),0);
                activity.overridePendingTransition(0,0);
            }
        });

        return convertView;

    }
}
