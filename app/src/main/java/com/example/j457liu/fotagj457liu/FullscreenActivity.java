package com.example.j457liu.fotagj457liu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

// Full screen activity class
public class FullscreenActivity extends AppCompatActivity {
    private Model model;
    private ContentViewFullScreen contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        // Set model
        model = Model.getInstance();

        // Retrieve information from intent sender
        Intent myIntent = getIntent();
        String url = myIntent.getStringExtra("url address");

        // Set content view
        contentView = new ContentViewFullScreen(this, model, url,
                model.getRatingByUrl(url));
        ViewGroup layout = (ViewGroup) findViewById(R.id.fullscreen_layout);
        layout.addView(contentView);
    }
}
