package com.proxima.elearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnterId extends AppCompatActivity {

    int entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_id);
        final EditText editText = findViewById(R.id.id);
        entry = getIntent().getExtras().getInt("entry");
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entry == 0) {
                    Intent intent = new Intent(EnterId.this, ViewStudent.class);
                    intent.putExtra("id", editText.getText().toString());
                    startActivityForResult(intent, 0);
                    overridePendingTransition(0, 0);
                }
                else {
                    Intent intent = new Intent(EnterId.this, ViewFaculty.class);
                    intent.putExtra("id", editText.getText().toString());
                    startActivityForResult(intent, 0);
                    overridePendingTransition(0, 0);
                }
            }
        });
    }
}
