package com.mycompany.testtask.UI;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mycompany.testtask.R;
import com.mycompany.testtask.Model.User;
import com.mycompany.testtask.Network.WebService;
import com.mycompany.testtask.Network.webServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class userScreen extends AppCompatActivity {
    //instance variable declaration
    public static String usersBaseUrl = "http://jsonplaceholder.typicode.com";
    public static String avatarBaseUrl = "https://avatars.io";
    public static List<User> users;
    public static List<Bitmap> userAvatars;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private webServiceApi serviceApi;


    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.user_screen);
        //initialize recycle view
        recyclerView = (RecyclerView) findViewById(R.id.userRow);
        //initialize a LinearLayout and apply it to the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //create row divisions
        DividerItemDecoration divider=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        //implement webService interface to make request for user list
        webServiceApi serviceApi = WebService.getResponse(usersBaseUrl)
                .create(webServiceApi.class);
        //establish an asynchronous call to serviceApi
        Call<List<User>> call = serviceApi.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, final Response<List<User>> response) {
                //check for successful response
                if(response.isSuccessful()) {
                    //initialized the list of users from the response
                    users = response.body();
                    //initialize recycler view adapter
                    adapter = new Adapter(users);
                    //apply the adapter in the recycler view
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getApplication().getBaseContext(),response.message(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        //set row division
        recyclerView.addItemDecoration(divider);

    }
}