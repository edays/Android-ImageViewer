package com.example.j457liu.fotagj457liu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

public class ContentViewFullScreen extends RelativeLayout {
    private Model model;
    ImageView image;
    RatingBar rb;

    public ContentViewFullScreen(final Context context, Model m, final String url) {
        super(context);
        model = m;

        // Get the XML view description and "inflate" it into the display (like rendering)
        View.inflate(context, R.layout.image_rating_fullscreen, this);

        image = (ImageView) findViewById(R.id.imageFullscreen);
        new ImageLoader(image).execute(url);    // load image
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // shift to full screen
                Intent myIntent = new Intent(context, MainActivity.class);
                context.startActivity(myIntent);
                ((Activity) context).finish();
            }
        });

        rb = (RatingBar) findViewById(R.id.ratingFullscreen);
        rb.setRating(model.getRatingByUrl(url));
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.setImageRatingByUrl(url, rating);
            }
        });
    }
}
