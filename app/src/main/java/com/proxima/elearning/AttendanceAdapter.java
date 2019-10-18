package com.proxima.elearning;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceAdapter extends BaseAdapter {
    Context context;
    TextView name;
    Button mark;
    ArrayList<GetAttendance> getAttendances;
    Config config;
    LayoutInflater layoutInflater;
    public AttendanceAdapter(Context context, ArrayList<GetAttendance> getAttendances) {
        this.context = context;
        this.getAttendances = getAttendances;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return getAttendances.size();
    }

    @Override
    public Object getItem(int position) {
        return getAttendances.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.mark_attendance,null);
        name = convertView.findViewById(R.id.txtName);
        mark = convertView.findViewById(R.id.btnMark);
        config = new Config();
        name.setText(getAttendances.get(position).getName());
        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "stu.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                            Log.e("Json Admin",response);



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
                        params.put("student", getAttendances.get(position).getId());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        return convertView;
    }
}
