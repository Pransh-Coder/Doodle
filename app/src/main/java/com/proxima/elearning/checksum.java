package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    String custid="", orderid="",mid="";
    RequestQueue requestQueue;
    Config config;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        requestQueue = Volley.newRequestQueue(checksum.this);
        config = new Config();
        //geting values from MainActivity

        sharedPreferences = getSharedPreferences("Login",0);
        Intent intent = getIntent();
        orderid=intent.getExtras().getString("orderid");
        custid=intent.getExtras().getString("custid");
        //intent.getExtras().getString("url");

        mid = "wQcpjW51803409595984";
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public class sendUserDetailTOServerdd  extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog progressDialog = new ProgressDialog(checksum.this);

        String url ="http://www.paytmpay001.dx.am/generateChecksum.php";
        // String varifyurl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID\"+orderId;";
        // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String varifyurl="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderid;
        String CHECKSUMHASH ="";

        // before executing the background task
        @Override
        protected void onPreExecute() {
            this.progressDialog.setMessage("Please Wait!");
            this.progressDialog.show();
        }

        @Override
        protected String doInBackground(ArrayList<String>... arrayLists) {
            JSONParser jsonParser = new JSONParser(checksum.this);
            String param =
                    "MID="+mid+
                            "&ORDER_ID="+orderid+
                            "&CUST_ID="+custid+"&CHANNEL_ID=WAP&TXN_AMOUNT=1&WEBSITE=DEFAULT"+
                            "&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail";



            JSONObject jsonObject =  jsonParser.makeHttpRequest(url,"POST",param);
            //Checksum with order id's status is recived [// yaha per checksum ke saht order id or status receive hoga..]
//            Log.e("Ckecksum result >>",jsonObject.toString());
            if(jsonObject!=null){
                Log.e("Ckecksum result >>",jsonObject.toString());

                try{
                    CHECKSUMHASH = jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("setup acc","Sign up result"+result);

            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            PaytmPGService Service = PaytmPGService.getProductionService();
            // when app is ready to publish use production service

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderid);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", ""+sharedPreferences.getInt("remaining",0));
            paramMap.put("WEBSITE", "DEFAULT");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(order,null);

            //Start paytm service call here
            Service.startPaymentTransaction(checksum.this,true,true,checksum.this);
        }
    }
    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        try {
            final JSONObject jsonObject = new JSONObject();
            Set<String> keys = bundle.keySet();
            for (String key : keys) {
                try {
                    // json.put(key, bundle.get(key)); see edit below
                    jsonObject.put(key, JSONObject.wrap(bundle.get(key)));
                } catch (JSONException e) {
                    //Handle exception here
                }
            }

            String success = jsonObject.getString("STATUS");
            Log.e("Status",success);
            if (success.equals("TXN_SUCCESS"))
            {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, config.baseUrl + "fees_post.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.e("Json Report",response);
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("Status");
                            if (status.equals("Success"))
                            {
                                Toast.makeText(checksum.this, "Payment Successful", Toast.LENGTH_SHORT).show();


                            }
                            else {
                                Toast.makeText(checksum.this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Report",error.toString());

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", sharedPreferences.getString("StudentID",""));
                        try {
                            params.put("order_id",jsonObject.getString("ORDERID"));
                            params.put("amount",jsonObject.getString("TXNAMOUNT"));
                            params.put("date",jsonObject.getString("TXNDATE"));
                            params.put("txnid",jsonObject.getString("TXNID"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        params.put("name",sharedPreferences.getString("name",""));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                int paid = sharedPreferences.getInt("paid",0);
                int amount = Integer.parseInt(jsonObject.getString("TXNAMOUNT"));
                editor.putInt("paid",paid+amount);
                editor.putInt("remaining",0);
                Intent intent = new Intent(checksum.this,FeeDetails.class);
                startActivityForResult(intent,0);
                overridePendingTransition(0,0);
                finish();

            }
            else {
                Toast.makeText(this, "Transaction Failure", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void networkNotAvailable() {

    }

    @Override
    public void clientAuthenticationFailed(String s) {

    }

    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  "+ s );
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  " );
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );
    }
}
