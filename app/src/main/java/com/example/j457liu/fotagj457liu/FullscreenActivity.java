package com.example.j457liu.fotagj457liu;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

public class FullscreenActivity extends AppCompatActivity {
    Model model;
    ContentViewFullScreen contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        model = Model.getInstance();
        Intent myIntent = getIntent();
        final String url = myIntent.getStringExtra("fullscreen");

        contentView = new ContentViewFullScreen(this, model, url);
        //Log.d("happpy", url);
        ViewGroup v = (ViewGroup) findViewById(R.id.fullscreen_layout);
        v.addView(contentView);
    }
}
