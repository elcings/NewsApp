package com.elchinaliyev.newsapp.Network;

import com.elchinaliyev.newsapp.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SourceApi {
    @GET("sources")
    Call<WebSite> getSources(@Query("language") String lang,@Query("apiKey") String apiKey);
}
