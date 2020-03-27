package com.elchinaliyev.newsapp.Network;

import com.elchinaliyev.newsapp.Model.IconBetterIdea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IconApi {
    @GET
    Call<IconBetterIdea>getIconUrl(@Url String url);
}
