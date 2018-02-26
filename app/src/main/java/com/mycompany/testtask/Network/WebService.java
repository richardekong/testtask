package com.mycompany.testtask.Network;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adminstrator on 2/23/2018.
 */
public class WebService {

    public static Retrofit retrofit=null;
    //obtain requests from base url
    public static Retrofit getResponse(String baseUrl){
        if (retrofit==null){
            //build up the retrofit object to access the webservice interface
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
            return retrofit;
    }
}
