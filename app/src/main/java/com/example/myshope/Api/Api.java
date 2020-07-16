package com.example.myshope.Api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("createuser")
    Call<ResponseBody> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<ResponseBody> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
}
