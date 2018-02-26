package com.mycompany.testtask.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import com.mycompany.testtask.R;

public class splashScreen extends AppCompatActivity {
    //instance variable declaration
    final int TIMEOUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //instantiate a handler to display the splash screen for 3 seconds
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                //create an intent
                Intent toUserScreen=new Intent(splashScreen.this,userScreen.class);
                //start the userScreen activity after 3 second or 3000 mini seconds
                startActivity(toUserScreen);
                //close the splash screen activity
                finish();
            }
        },TIMEOUT);
}
}