package com.elchinaliyev.newsapp.ViewModels;

import android.app.Application;

import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.Network.NewsRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends AndroidViewModel {
    private MutableLiveData<NewsResponse> newsResponseLiveData;
    private NewsRepository newsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        if(newsResponseLiveData!=null)
        {
            return;
        }
        newsRepository= NewsRepository.getInstance();
        newsResponseLiveData=newsRepository.getNew("us","9a6ef87028564b8795f336b2fa2519b1");

    }
    public LiveData<NewsResponse>getNews()
    {
        return newsResponseLiveData;
    }
}
