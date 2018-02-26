package com.mycompany.testtask.UI;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mycompany.testtask.Network.ImageService;
import com.mycompany.testtask.R;
import com.mycompany.testtask.Model.User;
import com.mycompany.testtask.Network.imageServerApi;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Adminstrator on 2/24/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    //instance variable declaration
    private List<User> user;
    private List<Bitmap> avatars;

    //create constructor
    public Adapter(List<User>user){
        this.user=user;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {
        //bind views
        //obtain images from url using Glide API
        imageServerApi serverApi= ImageService
                .getResponse("https://avatars.io")
                .create(imageServerApi.class);
        Call<ResponseBody>call=serverApi.getImageInfo(user.get(position).getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Bitmap bitmap=BitmapFactory.decodeStream(response.body().byteStream());
                holder.userAvatar.setImageBitmap(bitmap);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        holder.name.setText(user.get(position).getName());
        holder.emailDescription.setText(user.get(position).getEmail());
        holder.CompanyInfo.setText(user.get(position).getCompany().getCatchPhrase());
    }

    @Override
    public int getItemCount() {
        return user.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //instance variable
        ImageView userAvatar;
        TextView name,
                emailDescription,
                CompanyInfo;
        //create class constructor
        public viewHolder(View viewItem){
            super(viewItem);
            //set click listener
            viewItem.setOnClickListener(this);
            userAvatar=(CircleImageView)itemView.findViewById(R.id.userAvatar);
            name=(TextView)itemView.findViewById(R.id.name);
            emailDescription=(TextView)itemView.findViewById(R.id.emailDescription);
            CompanyInfo=(TextView)itemView.findViewById(R.id.info);

        }
        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            //gather information of the currently clicked user
            String name=user.get(position).getName().trim();
            String email=user.get(position).getEmail().trim();
            String phone=user.get(position).getPhone().trim();
            if(phone.contains("x"))
                phone=phone.substring(0,phone.indexOf(" "));

            String website=user.get(position).getWebsite();
            String longitude=user.get(position).getAddress().getGeo().getLng();
            String latitude=user.get(position).getAddress().getGeo().getLat();
            //create an intent to userDetails activities
            Intent toUserDetails=new Intent(view.getContext(),userDetails.class);
            //pass all gathered information to the user details screen
            toUserDetails.putExtra("NAME",name);
            toUserDetails.putExtra("EMAIL",email);
            toUserDetails.putExtra("PHONE",phone);
            toUserDetails.putExtra("WEBSITE",website);
            toUserDetails.putExtra("LONGITUDE",longitude);
            toUserDetails.putExtra("LATITUDE",latitude);
            //start the activity
            try {
                view.getContext().startActivity(toUserDetails);
            }catch(ActivityNotFoundException e){
                Toast.makeText(view.getContext(),"user details not found!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}
