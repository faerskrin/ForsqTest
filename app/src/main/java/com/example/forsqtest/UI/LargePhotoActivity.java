package com.example.forsqtest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.forsqtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LargePhotoActivity extends AppCompatActivity {
    @BindView(R.id.large_photo)
    ImageView mImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("photo");

        Glide.with(mImage)
                .load(url)
                .apply(RequestOptions
                        .errorOf(R.drawable.ic_launcher_foreground)
                        .placeholder(R.drawable.ic_launcher_foreground))
                .into(mImage);
    }
}
