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
    private final MutableLiveData<Boolean> isLoading=new MutableLiveData<>();
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
        isLoading.setValue(false);
    }

    public MutableLiveData<Boolean>getIsLoading()
    {
        return isLoading;
    }

    public MutableLiveData<NewsResponse>getNew(String sources,String key)
    {
        isLoading.setValue(true);
        final MutableLiveData<NewsResponse> newsData = new MutableLiveData<>();
        newsApi.getNews(sources,key).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call,
                                   Response<NewsResponse> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                newsData.setValue(null);
                isLoading.setValue(false);
            }
        });
        return newsData;
    }
}
