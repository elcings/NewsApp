package com.elchinaliyev.newsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elchinaliyev.newsapp.Model.NewsArticle;
import com.elchinaliyev.newsapp.Model.NewsResponse;
import com.elchinaliyev.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        holder.tvName.setText(articles.get(position).getTitle());
        holder.tvDescription.setText(articles.get(position).getDescription());
        Picasso.get().load(articles.get(position).getUrlToImage())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background).into(holder.ivNews);
    }

    @Override
    public int getItemCount() {
        if (articles != null)
            return articles.size();
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDescription;
        CircleImageView ivNews;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDesc);
            ivNews = itemView.findViewById(R.id.ivNews);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null&&position!=RecyclerView.NO_POSITION)
                    listener.onItemClick(articles.get(position));
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
