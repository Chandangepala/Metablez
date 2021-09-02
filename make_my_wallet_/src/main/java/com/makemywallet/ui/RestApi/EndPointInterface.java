package com.makemywallet.ui.RestApi;

import com.makemywallet.ui.AllResponse.LoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndPointInterface {

    @GET("app/login-validation")
    Call<LoginResponse> getLogin(@Query("username") String username, @Query("password") String password, @Query("mpin") String mpin);

}
