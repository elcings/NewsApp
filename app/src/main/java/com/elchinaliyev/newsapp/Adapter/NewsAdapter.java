package com.elchinaliyev.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elchinaliyev.newsapp.Common.ISO8601Parse;
import com.elchinaliyev.newsapp.DetailsActivity;
import com.elchinaliyev.newsapp.Model.NewsArticle;
import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.NewsActivity;
import com.elchinaliyev.newsapp.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {
    Context context;
    ArrayList<NewsArticle> articles;
    private OnItemClickListener listener;

    public NewsAdapter(Context context, ArrayList<NewsArticle> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(articles.get(position).getTitle()!=null) {
            if (articles.get(position).getTitle().length() > 0) {
                if (articles.get(position).getTitle().length() > 45)
                    holder.articleName.setText(articles.get(position).getTitle().substring(0, 45) + "...");
                else
                    holder.articleName.setText(articles.get(position).getTitle());
            }
        }
        if(articles.get(position).getUrlToImage()!=null) {
            if (articles.get(position).getUrlToImage().length() > 0) {
                Picasso.get().load(articles.get(position).getUrlToImage())
                        .placeholder((R.drawable.ic_launcher_background))
                        .error(R.drawable.ic_launcher_background).into(holder.ivNews);
            }
        }
        if(articles.get(position).getPublishedAt()!=null) {
            if (articles.get(position).getPublishedAt().length() > 0) {
                Date date = null;
                try {
                   date =ISO8601Parse.parse(articles.get(position).getPublishedAt());
                    Log.d("TAG"," DATE:"+date+"  "+articles.get(position).getPublishedAt());
                    holder.articleTime.setReferenceTime(date.getTime());

                } catch (ParseException e) {
                    Log.e("error",e.getLocalizedMessage()+" DATE:"+date+"  "+articles.get(position).getPublishedAt());
                    e.printStackTrace();
                }



            }
        }
    }

    @Override
    public int getItemCount() {
        if (articles != null)
            return articles.size();
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView articleName;
        CircleImageView ivNews;
        RelativeTimeTextView articleTime;


        public Holder(@NonNull View itemView) {
            super(itemView);
            articleName = itemView.findViewById(R.id.articleName);
            ivNews = itemView.findViewById(R.id.source_image);
            articleTime = itemView.findViewById(R.id.articleTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    Log.d("position",position+"");

                    if(position!=RecyclerView.NO_POSITION)
                    {
                        if(articles.get(position).getUrl()!=null) {
                            Log.d("LINK",articles.get(position).getUrl());
                            Intent intent = new Intent(context, DetailsActivity.class);
                            intent.putExtra("webUrl", articles.get(position).getUrl());
                            context.startActivity(intent);
                        }
                    }

                }
            });
        }
    }

   public interface OnItemClickListener {
       void onItemClick(NewsArticle response);
   }

     public void setOnclickListener(OnItemClickListener listener) {
         this.listener = listener;
     }
}
