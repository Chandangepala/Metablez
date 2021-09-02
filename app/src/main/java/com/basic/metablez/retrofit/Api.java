package com.basic.metablez.retrofit;

import com.basic.metablez.modelResponse.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

   // @Headers("Content-Type: application/json")
   // @Headers({"Content-Type: application/json"})
    //@Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("register")
    @Headers({
            "Content-Type: application/x-www-form-urlencoded;charset=utf-8",
            "Accept: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    Call<RegisterModel> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("role_id") String role_id,
            @Field("phone_number") String phone_number,
            @Field("password") String password
    );
}
