package com.jidian.cosalon.migration.pos365.retrofitservice;

import com.jidian.cosalon.migration.pos365.domainpos365.Post365Categories;
import com.jidian.cosalon.migration.pos365.dto.BaseResponse;
import com.jidian.cosalon.migration.pos365.domainpos365.Pos365Branch;
import com.jidian.cosalon.migration.pos365.dto.LoginRequest;
import com.jidian.cosalon.migration.pos365.dto.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

import java.util.Map;

public interface Pos365RetrofitService {

    @POST("auth/credentials?format=json")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("branchs?format=json")
    Call<BaseResponse<Pos365Branch>> listBranchs(@HeaderMap Map<String, String> headers);

    @GET("categories?format=json")
    Call<BaseResponse<Post365Categories>> listCategories(@HeaderMap Map<String, String> headers);
}
