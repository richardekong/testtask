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

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.testtask.R;


public class userDetails extends AppCompatActivity implements OnMapReadyCallback {
    //instance variable declaration
    private TextView nameText;
    private TextView emailText;
    private TextView phoneText;
    private WebView webView;
    private Bundle passedDetails;
    private String webUrl;
    private GoogleMap mMap;
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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Double lng=Double.parseDouble(passedDetails.getString("LONGITUDE"));
        Double lat=Double.parseDouble(passedDetails.getString("LATITUDE"));
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(webView!=null){
            webView.destroy();
        }
    }
}
