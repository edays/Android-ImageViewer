package com.example.j457liu.fotagj457liu;

// Picture data class
public class PictureData {
    private String url = "";    // Primary field.
    private float rating = 0;
    private boolean visible = true;

    /**
     * Constructor
     */
    PictureData(String url, float rating, boolean visible) {
        this.url = url;
        this.rating = rating;
        this.visible = visible;
    }

    /**
     * Set visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Get visibility
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Set rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Get rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Get url
     */
    public String getUrl() {
        return url;
    }
}
