package com.elchinaliyev.newsapp.Network;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static String BASE_URL="https://newsapi.org/v2/";
    public static String API_KEY="9a6ef87028564b8795f336b2fa2519b1";
    private static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public  static <S>S createService(Class<S>serviceClass)
    {
        return retrofit.create(serviceClass);
    }
}
