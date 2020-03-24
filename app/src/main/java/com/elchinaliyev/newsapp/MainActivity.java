package com.elchinaliyev.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.elchinaliyev.newsapp.Adapter.NewsAdapter;
import com.elchinaliyev.newsapp.Model.Country;
import com.elchinaliyev.newsapp.Model.NewsArticle;
import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.ViewModels.NewsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<NewsArticle> articleArrayList = new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView rvHeadline;
    NewsViewModel newsViewModel;
    Spinner spinner;
    ArrayAdapter<Country> adapterSpinner;
    String[] country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        List<Country>countryList=new ArrayList<>();
        countryList.add(new Country("France","fr"));
        countryList.add(new Country("USA","us"));
        countryList.add(new Country("Turkey","tr"));
        countryList.add(new Country("Russia","ru"));
        adapterSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countryList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);


        newsViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(NewsViewModel.class);
        setupRecyclerView();
        newsViewModel.getNews().observe(this, new Observer<NewsResponse>() {
            @Override
            public void onChanged(NewsResponse newsResponse) {
                List<NewsArticle> newsArticles = newsResponse.getArticles();
                articleArrayList.addAll(newsArticles);
                newsAdapter.notifyDataSetChanged();
            }
        });
        newsAdapter.setOnclickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsArticle response) {
                Log.d("Elcin", response.getSource().getName());
                Intent detail = new Intent(MainActivity.this, DetailsActivity.class);
                detail.putExtra("content", response.getDescription());
                detail.putExtra("imageUtl", response.getUrlToImage());
                detail.putExtra("title", response.getTitle());
                detail.putExtra("date", response.getPublishedAt());
                detail.putExtra("author", response.getAuthor());
                detail.putExtra("link", response.getUrl());
                startActivity(detail);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Country con=(Country) parent.getSelectedItem();
        Toast.makeText(MainActivity.this, con.getShortName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void init() {
        spinner = findViewById(R.id.spinnerCounrty);
        spinner.setOnItemSelectedListener(this);
        rvHeadline = findViewById(R.id.recView);
    }

    private void setupRecyclerView() {
        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(MainActivity.this, articleArrayList);
            rvHeadline.setLayoutManager(new LinearLayoutManager(this));
            rvHeadline.setAdapter(newsAdapter);
            rvHeadline.setItemAnimator(new DefaultItemAnimator());
            rvHeadline.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }
    }


}
