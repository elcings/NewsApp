package com.elchinaliyev.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView img;
    TextView title,content,date,author,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();
        Intent intent=getIntent();
        content.setText(intent.getStringExtra("content"));
       Picasso.get().load(intent.getStringExtra("imageUtl"))
               .placeholder((R.drawable.ic_launcher_background))
               .error(R.drawable.ic_launcher_background)
               .into(img); ;
        title.setText(intent.getStringExtra("title"));
        date.setText(intent.getStringExtra("date"));
        author.setText(intent.getStringExtra("author"));
        link.setMovementMethod(LinkMovementMethod.getInstance());
        String text=intent.getStringExtra("link");
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.N) {
            link.append(Html.fromHtml("<a href=\""+text+"\">More Detail</a>"));
        } else {
            link.append(Html.fromHtml("<a href=\""+text+"\">More Detail</a>",1));
        }
    }

    void init()
    {
        img =findViewById(R.id.imageDetail);
        title=findViewById(R.id.detailTitle);
        content=findViewById(R.id.content);
        date=findViewById(R.id.publishDate);
        author=findViewById(R.id.author);
        link=findViewById(R.id.link);
    }
}
