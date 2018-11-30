package com.example.j457liu.fotagj457liu;

public class PictureData {
    private String url = new String("");
    private float rating = new Float(0);
    private boolean visible = new Boolean(true);

    PictureData(String url, float rating, boolean visible) {
        this.url = url;
        this.rating = rating;
        this.visible = visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public String getUrl() {
        return url;
    }
}
