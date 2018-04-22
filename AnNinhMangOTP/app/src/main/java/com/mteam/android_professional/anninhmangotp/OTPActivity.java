package com.mteam.android_professional.anninhmangotp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Stealer Of Souls on 4/21/2018.
 */

public class OTPActivity extends AppCompatActivity {
    EditText edt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);

        final Intent intent=getIntent();
        String phone=intent.getStringExtra("PHONE");
        final int OTP=intent.getIntExtra("OTP",0);
        Log.d("", "onCreate: "+OTP);

        edt=findViewById(R.id.edt_phone_number);

        findViewById(R.id.btn_send_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt.getText().toString().equals(String.valueOf(OTP))){
                    Intent intent1=new Intent(OTPActivity.this,HomeActivity.class);
                    startActivity(intent1);
                }
            }
        });

    }
}
