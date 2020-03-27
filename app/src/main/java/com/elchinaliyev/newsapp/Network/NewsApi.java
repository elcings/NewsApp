package com.elchinaliyev.newsapp.Network;

import com.elchinaliyev.newsapp.Model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("top-headlines")
    Call<NewsResponse>getNews(@Query("sources")String sources,@Query("apiKey") String apiKey);
}
