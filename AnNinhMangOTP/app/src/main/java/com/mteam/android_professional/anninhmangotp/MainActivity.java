package com.mteam.android_professional.anninhmangotp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private EditText edtEmail,edtPass,edtPhone,edtPassConfrim;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail=findViewById(R.id.edt_email);
        edtPhone=findViewById(R.id.edt_phone);
        edtPass=findViewById(R.id.edt_pass);
        edtPassConfrim=findViewById(R.id.edt_pass_confirm);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loadding");
        progressDialog.setMessage("Waiting.....");

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                register(edtEmail.getText().toString(),edtPass.getText().toString(),edtPhone.getText().toString());


            }
        });

    }


    public void register(final String email, final String password, final String phone){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String param="?email="+URLEncoder.encode(email)+"&password="+URLEncoder.encode(password)+"&phone="+URLEncoder.encode(phone);
                String url = Contants.SEVER_NAME+param;
                try {
                    URL murl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) murl.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(in);
                    String line = bufferedReader.readLine();
                    StringBuilder builder = new StringBuilder();
                    while (line != null) {
                        builder.append(line);
                        line = bufferedReader.readLine();
                    }
                    Log.d("", "doInBackground: "+builder.toString());
                    return builder.toString();
                }catch (Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String kq) {
                super.onPostExecute(kq);
                    Toast.makeText(MainActivity.this,kq,Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    if(kq.contains("Đăng ký thành công")){
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }


            }
        }.execute();

    }



}
