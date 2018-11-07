package com.example.dawidsopata.myapplication.background;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.dawidsopata.myapplication.Clientx;
import com.example.dawidsopata.myapplication.MapsActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackgroundService //extends IntentService
 {
//    static final String REST_SERVICE_URL = "http://127.0.0.1:8080/";
//
//    static final int TIMER_INTERVAL = 1000;
//
//    public BackgroundService() {
//        super("BackgroudService");
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(REST_SERVICE_URL)
//                .addConverterFactory((GsonConverterFactory.create()));
//
//        Retrofit retrofit = builder.build();
//        Clientx client = retrofit.create(Clientx.class);
//        Call<ResponseBody> call = client.getResult(123);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                System.out.println(response.body());
//                // Toast.makeText(RestActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                //Toast.makeText(RestActivity.this, "Error: ", Toast.LENGTH_SHORT).show();
//                System.out.println(t);
//            }
//        });
//

   // }







}
