package com.elchinaliyev.newsapp.ViewModels;

import android.app.Application;

import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.Network.NewsRepository;
import com.elchinaliyev.newsapp.Network.RetrofitService;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends AndroidViewModel {
    private MutableLiveData<NewsResponse> newsResponseLiveData;
    private NewsRepository newsRepository;
    String sources;

    public NewsViewModel(@NonNull Application application,String sources) {
        super(application);
        this.sources=sources;
        if(newsResponseLiveData!=null)
        {
            return;
        }
        newsRepository= NewsRepository.getInstance();
        newsResponseLiveData=newsRepository.getNew(sources, RetrofitService.API_KEY);

    }
    public LiveData<NewsResponse>getNews()
    {
        return newsResponseLiveData;
    }
    public LiveData<Boolean> getIsLoading(){
        LiveData<Boolean> isLoading=newsRepository.getIsLoading();
        return isLoading;
    }
}
