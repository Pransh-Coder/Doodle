package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    String custid="", orderid="",mid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //geting values from MainActivity

        Intent intent = getIntent();
        orderid=intent.getExtras().getString("orderid");
        custid=intent.getExtras().getString("custid");
        //intent.getExtras().getString("url");

        mid = "CIronQ46939101612109";
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public class sendUserDetailTOServerdd  extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog progressDialog = new ProgressDialog(checksum.this);

        String url ="http://paytm.paytmpay001.dx.am/generateChecksum.php";
        // String varifyurl = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID\"+orderId;";
        // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String varifyurl="http://paytmpay001.dx.am/verifyChecksum.php";
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
            paramMap.put("TXN_AMOUNT", "1");
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
