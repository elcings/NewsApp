package com.elchinaliyev.newsapp.Network;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IconService {
    private static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://i.olsh.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public  static <S>S createService(Class<S>serviceClass)
    {
        return retrofit.create(serviceClass);
    }
}
