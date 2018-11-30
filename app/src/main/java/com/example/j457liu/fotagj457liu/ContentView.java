package com.example.j457liu.fotagj457liu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

public class ContentView extends LinearLayout {
    private Model m;
    private ImageView image;
    private RatingBar rb;

    public ContentView(final Context context, Model model, final String url) {
        super(context);
        m = model;

        // Get the XML view description and "inflate" it into the display (like rendering)
        View.inflate(context, R.layout.image_rating, this);

        image = (ImageView) findViewById(R.id.image_net);
        new ImageLoader(image).execute(url);    // load image
        image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // shift to full screen
                    Intent myIntent = new Intent(context, FullscreenActivity.class);
                    myIntent.putExtra("fullscreen", url);
                    context.startActivity(myIntent);
                    ((Activity)context).finish();
                }
            });

        rb = (RatingBar) findViewById(R.id.rating);
        rb.setRating(model.getRatingByUrl(url));
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                m.setImageRatingByUrl(url, rating);
            }
        });
    }
}
