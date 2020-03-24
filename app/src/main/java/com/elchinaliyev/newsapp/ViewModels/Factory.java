package com.elchinaliyev.newsapp.ViewModels;

import android.app.Application;

import com.elchinaliyev.newsapp.MainActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class Factory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    private final Application mApplication;
    private final String country;


    public Factory(@NonNull Application application, String country) {
        mApplication = application;
        this.country = country;
    }

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsViewModel(mApplication, country);
    }
}
