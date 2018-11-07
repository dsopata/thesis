package com.example.dawidsopata.myapplication;

import com.example.dawidsopata.myapplication.dto.LocationDto;
import com.example.dawidsopata.myapplication.dto.TargetLocationsDto;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Clientx {
    @GET("/result")
    Call<ResponseBody> getResult(@Query("number") int number);

    @GET("/getClientData")
    Call<TargetLocationsDto> getClientData(@Query("id") Long id);

    @POST("/sendCoordinates")
    Call<Boolean> sendCoordinates(@Query("driverLatitude") Double driverLatitude,
                                           @Query("driverLongitude") Double driverLongitude);
    @GET("/getMostRecentResults")
    Call<List<LocationDto>> getMostRecentResults();

    @GET("/startFindingWay")
    Call<ResponseBody> startAlgorithm(@Query("id") Long id);
}
