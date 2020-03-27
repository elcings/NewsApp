package com.elchinaliyev.newsapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory;

public class SourceFactory extends NewInstanceFactory {
    @NonNull
    private final Application mApplication;
    private final String lang;


    public SourceFactory(@NonNull Application application, String lang) {
        mApplication = application;
        this.lang = lang;
    }

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SourceViewModel(mApplication, lang);
    }
}
