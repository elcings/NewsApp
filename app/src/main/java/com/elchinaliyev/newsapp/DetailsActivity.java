package com.elchinaliyev.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView img;
    TextView title;
    TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        img =findViewById(R.id.imageDetail);
        title=findViewById(R.id.detailTitle);
        content=findViewById(R.id.content);
        Intent intent=getIntent();
        content.setText(intent.getStringExtra("content"));
       Picasso.get().load(intent.getStringExtra("imageUtl"))
               .placeholder((R.drawable.ic_launcher_background))
               .error(R.drawable.ic_launcher_background)
               .into(img); ;
        title.setText(intent.getStringExtra("title"));
    }
}
