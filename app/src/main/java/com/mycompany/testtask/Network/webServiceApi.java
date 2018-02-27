package com.mycompany.testtask.Network;


import com.mycompany.testtask.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Adminstrator on 2/23/2018.
 */

public interface webServiceApi {
    //declaration of url endpoints
      String usersEndPoint="/users";
    //get users' list from the web server through the url endpoint
    @GET(usersEndPoint)
    Call<List<User>> getUsers();






}
