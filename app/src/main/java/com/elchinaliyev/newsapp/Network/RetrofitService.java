package com.elchinaliyev.newsapp.Network;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
//http://newsapi.org/v2/sources?apiKey=9a6ef87028564b8795f336b2fa2519b1
    private static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public  static <S>S createService(Class<S>serviceClass)
    {
        return retrofit.create(serviceClass);
    }
}
