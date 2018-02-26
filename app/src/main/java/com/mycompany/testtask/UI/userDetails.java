package com.mycompany.testtask.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.testtask.R;


public class userDetails extends AppCompatActivity {
    //instance variable declaration
    private TextView nameText;
    private TextView emailText;
    private TextView phoneText;
    private WebView webView;
    private Bundle passedDetails;
    private String webUrl;
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.user_details);
        //obtain user details from previous activity
        passedDetails=getIntent().getExtras();
        //instantiate widgets
        nameText=findViewById(R.id.nameDetail);
        emailText=findViewById(R.id.emailDetail);
        phoneText=findViewById(R.id.phoneDetail);
        webView=findViewById(R.id.website);
        //display user details from the previous activity
        nameText.setText(passedDetails.getString("NAME"));
        emailText.setText(passedDetails.getString("EMAIL"));
        phoneText.setText(passedDetails.getString("PHONE"));
        webUrl=passedDetails.getString("WEBSITE");
        //configure website to load javascript
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //set web client
        webView.setWebChromeClient(new WebChromeClient());
        //handle telephone calls to user
        phoneText.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                makePhoneCall(phoneText.getText().toString());
            }
        });
        emailText.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view){
                sendEmail(emailText.getText().toString());
            }
        });
        //load the website
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(webUrl);

            }
        });
    }

    public void sendEmail(String emailSend){
        try{
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto: "+emailSend));
            startActivity(Intent.createChooser(emailIntent, "Send email"));
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"Call unsuccessful", Toast.LENGTH_LONG).show();
        }

    }

    public void makePhoneCall(String PhoneNumber){
        Intent phoneCall=new Intent(Intent.ACTION_CALL);
        phoneCall.setData(Uri.parse("tel:"+PhoneNumber));
        try {
            startActivity(phoneCall);
        }
        catch (SecurityException e){
            Toast.makeText(getApplicationContext(),"Call unsuccessful", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(webView!=null){
            webView.destroy();
        }
    }
}
