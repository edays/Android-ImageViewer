package com.example.j457liu.fotagj457liu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

// Content view class for full screen
// @Note: may implement Observer for new features
public class ContentViewFullScreen extends RelativeLayout {
    private Model model;
    private ImageView image;
    private RatingBar rb;
    private String url;
    private float rate;

    /**
     * Constructor
     *
     * @param context    Context
     * @param m          Model
     * @param urlAddress Url address
     * @param rating     Rating used to set the rating bar
     */
    public ContentViewFullScreen(Context context, Model m, String urlAddress, float rating) {
        super(context);
        this.model = m;
        this.rate = rating;
        this.url = urlAddress;

        // Get the XML view description and "inflate" it into the display
        View.inflate(context, R.layout.image_rating_fullscreen, this);

        // Set up image view
        image = (ImageView) findViewById(R.id.imageFullscreen);
        new ImageLoader(image).execute(url);   // async load bitmap from url to image
        image.setOnClickListener(new OnClickListener() {  // listener
            @Override
            public void onClick(View v) {
                // send intent to MainActivity.class
                Context cont = getContext();
                Intent myIntent = new Intent(cont, MainActivity.class);
                cont.startActivity(myIntent);
                ((Activity) cont).finish();
            }
        });

        // Set up rating bar
        rb = (RatingBar) findViewById(R.id.ratingFullscreen);
        rb.setRating(rate);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser) {
                // Update rating in model
                model.setImageRatingByUrl(url, rating);
                model.initObservers();
            }
        });
    }
}
