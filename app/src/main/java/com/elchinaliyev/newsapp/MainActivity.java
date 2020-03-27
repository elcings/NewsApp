package com.elchinaliyev.newsapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.elchinaliyev.newsapp.Adapter.NewsAdapter;
import com.elchinaliyev.newsapp.Adapter.SourceAdapter;
import com.elchinaliyev.newsapp.Model.Country;
import com.elchinaliyev.newsapp.Model.NewsArticle;
import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.Model.Source;
import com.elchinaliyev.newsapp.Model.WebSite;
import com.elchinaliyev.newsapp.ViewModels.Factory;
import com.elchinaliyev.newsapp.ViewModels.NewsViewModel;
import com.elchinaliyev.newsapp.ViewModels.SourceFactory;
import com.elchinaliyev.newsapp.ViewModels.SourceViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {


    ArrayList<Source> sources = new ArrayList<>();
    RecyclerView rvHeadline;
    SourceAdapter sourceAdapter;
    SourceViewModel sourceViewModel;
    AlertDialog dialog;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        init();
       // Paper.book().delete("cache");
        load(false);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load(true);
            }
        });
        sourceAdapter.setOnclickListener(new SourceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Source source) {
               Intent intent=new Intent(MainActivity.this,NewsActivity.class);
               intent.putExtra("source",source.getId());
               startActivity(intent);

            }
        });

    }

    private void init() {
        rvHeadline = findViewById(R.id.recView);
        swipe=findViewById(R.id.swipe);
        dialog = new SpotsDialog.Builder().setContext(this).build();
        rvHeadline.setLayoutManager(new LinearLayoutManager(this));
        rvHeadline.setItemAnimator(new DefaultItemAnimator());
        rvHeadline.setNestedScrollingEnabled(true);


    }

    private void setupRecyclerView() {

        sourceAdapter = new SourceAdapter(MainActivity.this, sources);
        sourceAdapter.notifyDataSetChanged();
        rvHeadline.setAdapter(sourceAdapter);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void load(Boolean isRefreshed)
    {
        if(!isRefreshed)
        {
          String cache=  Paper.book().read("cache");
          if(cache!=null&&!cache.isEmpty())
          {
              Log.d("Elcin",cache);
              WebSite site =new Gson().fromJson(cache,WebSite.class);
              List<Source> sourcesList=site.getSourceList();
              sources.addAll(sourcesList);
              Log.d("Elcin",sources.size()+"");
              setupRecyclerView();
          }
          else
          {
              if(isNetworkConnected()) {
                  sourceViewModel = new SourceFactory(getApplication(), "en").create(SourceViewModel.class);
                  sourceViewModel.getSource().observe(this, new Observer<WebSite>() {
                      @Override
                      public void onChanged(WebSite webSite) {
                          List<Source> sourcesList = webSite.getSourceList();
                          sources.addAll(sourcesList);
                          setupRecyclerView();
                          Paper.book().write("cache", new Gson().toJson(webSite));
                      }
                  });
                  sourceViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
                      @Override
                      public void onChanged(Boolean aBoolean) {
                          if (aBoolean) {
                              dialog.show();
                          } else {
                              dialog.dismiss();
                          }
                      }
                  });
              }
              else
              {
                  Toast.makeText(MainActivity.this,"Internet bağlantısını kontrol edin",Toast.LENGTH_LONG).show();
              }
          }
        }
        else
        {
            if(isNetworkConnected()) {
               //setupRecyclerView();
                sourceViewModel = new SourceFactory(getApplication(), "en").create(SourceViewModel.class);
                sourceViewModel.getSource().observe(this, new Observer<WebSite>() {
                    @Override
                    public void onChanged(WebSite webSite) {
                        List<Source> sourcesList = webSite.getSourceList();
                        sources.addAll(sourcesList);
                        setupRecyclerView();
                        Paper.book().write("cache", new Gson().toJson(webSite));
                        swipe.setRefreshing(false);
                    }
                });
                sourceViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            dialog.show();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(MainActivity.this,"Internet bağlantısını kontrol edin",Toast.LENGTH_LONG).show();
            }
        }
    }


}
