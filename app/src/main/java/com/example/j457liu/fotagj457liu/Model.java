package com.example.j457liu.fotagj457liu;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

// Singleton model class
class Model extends Observable {
    // singleton
    private static Model modelInstance = null;

    public static Model getInstance() {
        if (modelInstance == null) {
            modelInstance = new Model();
        }
        return modelInstance;
    }

    // Private Variables
    private List<PictureData> pList = new ArrayList<>();
    private float filterLevel = 0;
    public ViewGroup main_view = null;

    /**
     * Model Constructor:
     */
    private Model() {
        // intentionally empty
    }

    /**
     * Get Picture Data List
     */
    public List<PictureData> getPictureDataList() {
        return pList;
    }

    /**
     * Set Picture Data List
     */
    public void setPictureDataList(List<PictureData> pList) {
        this.pList = pList;
    }

    public void setImageRatingByUrl(String url, float rating) {
        for (PictureData p : pList) {
            if (p.getUrl().equals(url)) {
                p.setRating(rating);
            }
        }
    }

    /**
     * Get rating value from pList
     */
    public float getRatingByUrl(String url) {
        for (PictureData p : pList) {
            if (p.getUrl().equals(url))
                return p.getRating();
        }
        return 0;
    }

    /**
     * Set current filter level and notify observers
     */
    public void filter(float level) {
        for (PictureData p : pList) {

            if (p.getRating() < level) {
                p.setVisible(false);

            } else {
                p.setVisible(true);
            }
        }
        filterLevel = level;
        initObservers();
    }

    /**
     * Set filter level
     */
    public void setFilterLevel(float filterLevel) {
        this.filterLevel = filterLevel;
    }

    /**
     * Get filter level
     */
    public float getFilterLevel() {
        return filterLevel;
    }

    public void setVisibilityByUrl(String url, boolean visible) {
        for (PictureData p : pList) {
            if (p.getUrl().equals(url)) {
                p.setVisible(visible);
            }
        }
    }

    /**
     * Get visibility from pList
     */
    public boolean getVisibilityByUrl(String url) {
        for (PictureData p : pList) {
            if (p.getUrl().equals(url)) {
                return p.getVisible();
            }
        }
        return false;
    }

    /*********************** Override Obeserver Methods  *****************************************/
    /**
     * Helper method to make it easier to initialize all observers
     */
    public void initObservers() {
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }
}
