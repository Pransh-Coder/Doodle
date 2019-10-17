package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

public class PaymentGateway extends AppCompatActivity {

    TextView txtName, txtRemaining;
    String varifyurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        final SharedPreferences sharedPreferences = getSharedPreferences("Login",0);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Button btn = (Button) findViewById(R.id.payment);
        txtName = (TextView) findViewById(R.id.name);
        txtRemaining = (TextView) findViewById(R.id.remaining);
        txtName.append(""+sharedPreferences.getString("name",""));
        txtRemaining.append(""+sharedPreferences.getInt("remaining",0));
        String uniqueID = UUID.randomUUID().toString();
        final String order=""+uniqueID;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentGateway.this, checksum.class);
                intent.putExtra("orderid", order);
                intent.putExtra("custid", sharedPreferences.getString("name","")+sharedPreferences.getString("StudentID",""));
                //intent.putExtra("url",varifyurl);
                startActivity(intent);
            }
        });
        if (ContextCompat.checkSelfPermission(PaymentGateway.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PaymentGateway.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
    }
}
