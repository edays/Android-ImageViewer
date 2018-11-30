package com.example.j457liu.fotagj457liu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.InputStream;

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
    ImageView image;

    public ImageLoader(ImageView image) {
        this.image = image;
    }


    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bmap = null;

        try {
            InputStream in = new java.net.URL(url).openStream();
            bmap = BitmapFactory.decodeStream(in);
//            URL urlk = new URL(url);
//            InputStream in = urlk.openConnection().getInputStream();
//            bmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            // do nothing
        }
        return bmap;
    }

    protected void onPostExecute (Bitmap result) {
        image.setImageBitmap(result);
    }
}
