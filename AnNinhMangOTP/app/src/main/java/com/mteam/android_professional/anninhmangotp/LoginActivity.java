package com.mteam.android_professional.anninhmangotp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by Stealer Of Souls on 4/21/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPass, edtPhone, edtPassConfrim;
    private ProgressDialog progressDialog;
    private int otp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edtEmail = findViewById(R.id.edt_email_login);
        edtPass = findViewById(R.id.edt_pass_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loadding");
        progressDialog.setMessage("Waiting.....");


        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                login(edtEmail.getText().toString(), edtPass.getText().toString());
            }
        });
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                // sendMessage();
            }
        });
    }

    public void login(final String email, final String password) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String param="?email="+URLEncoder.encode(email)+"&pass="+URLEncoder.encode(password);

                String url = Contants.SEVER_NAME+param;
                Log.d("", "doInBackground: "+url);

                try {
                    URL murl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) murl.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                    InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(in);
                    String line = bufferedReader.readLine();
                    StringBuilder builder = new StringBuilder();
                    while (line != null) {
                        builder.append(line);
                        line = bufferedReader.readLine();
                    }
                    Log.d("xuanhoi", "doInBackground: "+builder.toString());
                    return builder.toString();
                }catch (Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String kq) {
                super.onPostExecute(kq);
                if(kq!=null){
                    try {
                        JSONObject jsonObject=new JSONObject(kq.toString());
                        sendMessage(jsonObject.getString("phone"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    public void sendMessage(final String phone) {
        Log.d("", "sendMessage: "+phone);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    // Construct data
                    otp=createOtp();
                    String apiKey = "apikey=" + "xKlLfVuT0tM-d0EGvBHPaHjdDkxf7AD9Zfel8LaSRs";
                    String message = "&message=" + "this is your message: "+otp;
                    String sender = "&sender=" + "XuanHoi";
                    String numbers = "&numbers=" +"84"+phone;
                    String data = apiKey + numbers + message + sender;
                    String url="https://api.txtlocal.com/send/?"+data;

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

                    //
                    Log.d("", "doInBackground: " + builder.toString());
                    return builder.toString();
                } catch (Exception e) {
                    System.out.println("Error SMS " + e);
                    return "Error " + e;
                }
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Log.d("send", "onPostExecute: "+s);
                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                intent.putExtra("PHONE",phone);
                intent.putExtra("OTP",otp);
                startActivity(intent);
            }
        }.execute();
    }

    private int createOtp() {
        Random random=new Random();
        return random.nextInt(100000);
    }
}
