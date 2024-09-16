
package com.example.examen.api;

import com.example.examen.model.UserRes;
import com.example.examen.model.PaisRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/?results=20")
    Call<UserRes> getUsers();

    @GET("v3.1/name/{country}")
    Call<PaisRes> getCountryInfo(@Path("country") String country);
}