package com.mycompany.testtask.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adminstrator on 2/25/2018.
 */

public class ImageService {
    public static Retrofit retrofit=null;
    //obtain requests from base url
    public static Retrofit getResponse(String baseUrl){
        if (retrofit==null){
            //build up the retrofit object to access the webservice interface
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit;
    }


}

