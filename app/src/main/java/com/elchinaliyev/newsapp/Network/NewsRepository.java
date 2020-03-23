package com.elchinaliyev.newsapp.Network;

import android.util.Log;

import com.elchinaliyev.newsapp.Model.NewsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private static NewsRepository newsRepository;
    NewsApi newsApi;


    public static NewsRepository getInstance()
    {
        if(newsRepository==null)
            newsRepository=new NewsRepository();
        return newsRepository;
    }

    public NewsRepository()
    {
        newsApi=RetrofitService.createService(NewsApi.class);
    }

    public MutableLiveData<NewsResponse>getNew(String country,String key)
    {
        final MutableLiveData<NewsResponse> newsData = new MutableLiveData<>();
        newsApi.getNews(country,key).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call,
                                   Response<NewsResponse> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }
}
