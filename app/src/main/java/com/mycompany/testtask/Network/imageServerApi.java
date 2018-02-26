package com.mycompany.testtask.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Adminstrator on 2/25/2018.
 */

public interface imageServerApi {
    //declare method to get twitter avatar from a specified endpoint
    String avatarEndPoint="/twitter/{id}";
    @GET(avatarEndPoint)
    Call<ResponseBody> getImageInfo(@Path("id") int userId);
}
