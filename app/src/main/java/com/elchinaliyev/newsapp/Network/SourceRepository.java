package com.elchinaliyev.newsapp.Network;

import android.util.Log;

import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.Model.WebSite;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceRepository {
    private final MutableLiveData<Boolean> isLoading=new MutableLiveData<>();
    private static SourceRepository sourceRepository ;
    SourceApi sourceApi;

    public static SourceRepository getInstance()
    {
        if(sourceRepository==null)
            sourceRepository=new SourceRepository();
        return sourceRepository;
    }

    public SourceRepository()
    {
        sourceApi=RetrofitService.createService(SourceApi.class);
        isLoading.setValue(false);
    }
    public MutableLiveData<Boolean>getIsLoading()
    {
        return isLoading;
    }
    public MutableLiveData<WebSite>getSource(String lang,String ApiKey)
    {
        isLoading.setValue(true);
        final MutableLiveData<WebSite> sourceData = new MutableLiveData<>();
        sourceApi.getSources(lang,ApiKey).enqueue(new Callback<WebSite>() {
            @Override
            public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                if(response.isSuccessful())
                {
                    sourceData.setValue(response.body());
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<WebSite> call, Throwable t) {
                Log.e("source",t.getLocalizedMessage());
            }
        });

        return sourceData;
    }
}
