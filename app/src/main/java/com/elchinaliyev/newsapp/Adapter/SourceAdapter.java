package com.elchinaliyev.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elchinaliyev.newsapp.MainActivity;
import com.elchinaliyev.newsapp.Model.IconBetterIdea;
import com.elchinaliyev.newsapp.Model.NewsArticle;
import com.elchinaliyev.newsapp.Model.Source;
import com.elchinaliyev.newsapp.Model.WebSite;
import com.elchinaliyev.newsapp.Network.IconApi;
import com.elchinaliyev.newsapp.Network.IconService;
import com.elchinaliyev.newsapp.NewsActivity;
import com.elchinaliyev.newsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {
    ArrayList<Source> sources;
    Context context;
    IconApi iconApi;


    public SourceAdapter(Context context, ArrayList<Source> sources) {
        this.sources = sources;
        this.context = context;
        iconApi = IconService.createService(IconApi.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        StringBuilder stringBuilder = new StringBuilder("https://i.olsh.me/allicons.json?url=");
        stringBuilder.append(sources.get(position).getUrl());
        iconApi.getIconUrl(stringBuilder.toString()).enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                if (response.isSuccessful()) {
                    if (response.body().getIcons().size() > 0) {
                        Picasso.get().load(response.body().getIcons().get(0).getUrl())
                                .placeholder(R.drawable.nophoto)
                                .error(R.drawable.nophoto)
                                .into(holder.img);
                    }
                    else
                    {
                        Log.d("Response","Not successful");
                    }
                }
            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {
                Log.e("SourceAdapterIcon", t.getLocalizedMessage());
            }
        });
        if (sources != null)
            holder.tvSourceName.setText(sources.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (sources != null)
            return sources.size();
        else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView tvSourceName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.source_image);
            tvSourceName = itemView.findViewById(R.id.source_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, NewsActivity.class);
                        intent.putExtra("source", sources.get(position).getId());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
