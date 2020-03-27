package com.elchinaliyev.newsapp.ViewModels;

import android.app.Application;

import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.Model.WebSite;
import com.elchinaliyev.newsapp.Network.NewsRepository;
import com.elchinaliyev.newsapp.Network.RetrofitService;
import com.elchinaliyev.newsapp.Network.SourceRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SourceViewModel extends AndroidViewModel {

    private MutableLiveData<WebSite> sourceLiveData;
    private SourceRepository sourceRepository;
    private String lang;
    public SourceViewModel(@NonNull Application application,String lang) {
        super(application);
        this.lang=lang;
        sourceRepository=SourceRepository.getInstance();
        sourceLiveData=sourceRepository.getSource(lang,RetrofitService.API_KEY);
    }
    public LiveData<WebSite> getSource()
    {
        return sourceLiveData;
    }
    public LiveData<Boolean> getIsLoading(){
        LiveData<Boolean> isLoading=sourceRepository.getIsLoading();
        return isLoading;
    }

}
