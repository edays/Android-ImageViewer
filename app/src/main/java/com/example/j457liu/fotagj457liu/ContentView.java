package com.example.j457liu.fotagj457liu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import java.util.Observable;
import java.util.Observer;

public class ContentView extends LinearLayout implements Observer {
    private Model model;
    public ImageView image;
    private RatingBar rb;
    final String urlAddress;

    /**
     * Constructor
     *
     * @param context Context
     * @param m       Model
     * @param url     Unique and primary url address
     */
    public ContentView(Context context, Model m, String url) {
        super(context);
        this.model = m;
        this.urlAddress = url;
        model.addObserver(this);

        // Get the XML view description and "inflate" it into the display
        View.inflate(context, R.layout.image_rating, this);

        // Set up image view
        image = (ImageView) findViewById(R.id.image_net);
        new ImageLoader(image).execute(url);    // async load bitmap from url to image view
        image.getAdjustViewBounds();            // auto adjust image view to fit size
        image.setOnClickListener(new OnClickListener() {    // listener
            @Override
            public void onClick(View v) {
                Log.d("happpy", "url image " + urlAddress);
                // Send intent to FullscreenActivity
                Context cont = getContext();
                Intent myIntent = new Intent(cont, FullscreenActivity.class);
                myIntent.putExtra("url address", urlAddress);
                cont.startActivity(myIntent);
                ((Activity) cont).finish();
            }
        });

        // Set up rating bar with default rating
        rb = (RatingBar) findViewById(R.id.rating);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser) { // listener
                if (!fromUser)
                    return;
                // Update rating in model
                model.setImageRatingByUrl(urlAddress, rating);
                if (rating < model.getFilterLevel()) {
                    // remove this veiw from main_view
                    model.main_view.removeView(ContentView.this);
                    model.setVisibilityByUrl(urlAddress, false);
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        // Update rating
        float f = model.getRatingByUrl(urlAddress);
        rb.setRating(f);
        if (f < model.getFilterLevel()) {
            if (model.getVisibilityByUrl(urlAddress)) {
                model.main_view.removeView(this);
                model.setVisibilityByUrl(urlAddress, false);
            }
        } else {
            if (!model.getVisibilityByUrl(urlAddress)) {
                model.main_view.addView(this);
                model.setVisibilityByUrl(urlAddress, true);
            }
        }
    }
}
