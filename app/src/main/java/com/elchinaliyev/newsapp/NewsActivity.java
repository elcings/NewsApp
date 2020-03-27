package com.elchinaliyev.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.elchinaliyev.newsapp.Adapter.NewsAdapter;
import com.elchinaliyev.newsapp.Adapter.SourceAdapter;
import com.elchinaliyev.newsapp.Model.NewsArticle;
import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.ViewModels.Factory;
import com.elchinaliyev.newsapp.ViewModels.NewsViewModel;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    ArrayList<NewsArticle> articleArrayList = new ArrayList<>();
    NewsAdapter newsAdapter;
    NewsViewModel newsViewModel;
    RecyclerView newsRec;
    KenBurnsView kenView;
    AlertDialog dialog;
    TextView title;
    TextView author;
    CircleImageView newsImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
        Intent intent=getIntent();
        String source= intent.getStringExtra("source");
      Log.d("ElcinSource", source);
        newsViewModel = new Factory(getApplication(), source).create(NewsViewModel.class);

        //setupRecyclerView();
          newsViewModel.getNews().observe(this, new Observer<NewsResponse>() {
                @Override
                public void onChanged(NewsResponse newsResponse) {
                    dialog.show();
                    List<NewsArticle> newsArticles = newsResponse.getArticles();
                    title.setText(newsResponse.getArticles().get(0).getTitle());
                    author.setText(newsResponse.getArticles().get(0).getAuthor());
                    if(newsResponse.getArticles().get(0).getUrlToImage().length()>0) {
                        Picasso.get().load(newsResponse.getArticles().get(0).getUrlToImage())
                                .placeholder((R.drawable.ic_launcher_background))
                                .error(R.drawable.ic_launcher_background).into(kenView);
                    }
                     articleArrayList.addAll(newsArticles);
                     setupRecyclerView();
                     dialog.dismiss();
                }
            });


          // newsAdapter.setOnclickListener(new NewsAdapter.OnItemClickListener() {
             //   @Override
             //   public void onItemClick(NewsArticle response) {
                   /* Log.d("Elcin", response.getSource().getName());
                    Intent detail = new Intent(NewsActivity.this, DetailsActivity.class);
                    detail.putExtra("content", response.getDescription());
                    detail.putExtra("imageUtl", response.getUrlToImage());
                    detail.putExtra("title", response.getTitle());
                    detail.putExtra("date", response.getPublishedAt());
                    detail.putExtra("author", response.getAuthor());
                    detail.putExtra("link", response.getUrl());
                    startActivity(detail);*/
             //   }
           // });
    }
    private void init() {
        newsRec = findViewById(R.id.newsRec);
        title=findViewById(R.id.topTitle);
        author=findViewById(R.id.topAuthor);
        kenView=findViewById(R.id.topImage);
        dialog = new SpotsDialog.Builder().setContext(this).build();
    }

    private void setupRecyclerView() {
        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(NewsActivity.this, articleArrayList);
            newsRec.setLayoutManager(new LinearLayoutManager(this));
            newsRec.setAdapter(newsAdapter);
            newsRec.setItemAnimator(new DefaultItemAnimator());
            newsRec.setNestedScrollingEnabled(true);
        } else {
            newsAdapter.notifyDataSetChanged();
        }

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
