package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FeeDetails extends AppCompatActivity {

    Button payFee;
    TextView txtPaid,txtRemain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_details);
        txtPaid = findViewById(R.id.txtPaid);
        txtRemain = findViewById(R.id.txtRemain);
        SharedPreferences sharedPreferences = getSharedPreferences("Login",0);
        txtPaid.setText("Rs."+sharedPreferences.getInt("paid",0)+"");
        txtRemain.setText("Rs."+sharedPreferences.getInt("remaining",0)+"");

        payFee = findViewById(R.id.pay_fees);

        payFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeeDetails.this,PaymentGateway.class);
                startActivity(intent);
            }
        });
    }
}
