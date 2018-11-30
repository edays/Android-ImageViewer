package com.example.j457liu.fotagj457liu;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


class Model extends Observable {
    // singleton
    private static Model modelInstance = null;

    public static Model getInstance() {
        if(modelInstance == null) {
            modelInstance = new Model();
        }
        return modelInstance;
    }

    // Private Variables
    private List<PictureData> pList = new ArrayList<>();
    private int scrollX = 0;
    private int scrollY = 0;

    /**
     * Model Constructor:
     */
    private Model() {
        // intentionally empty
    }

    public List<PictureData> getPictureDataList() {
        return pList;
    }

    public void setPictureDataList(List<PictureData> pList) {
        this.pList = pList;
    }

    public void setImageRatingByUrl(String url, float rating) {
        for(PictureData p : pList) {
            if(p.getUrl().equals(url)) {
                p.setRating(rating);
            }
        }
    }

    public float getRatingByUrl(String url) {
        for(PictureData p : pList) {
            if(p.getUrl().equals(url))
                return p.getRating();
        }
        return 0;
    }

    public void setFilter(float level) {
        for(PictureData p : pList) {

            if(p.getRating() < level) {
                p.setVisible(false);

            }
            else {
                p.setVisible(true);
            }
        }
    }

    public void setScrollXY(int scrollX, int scrollY) {
        this.scrollX = scrollX;
        this.scrollY = scrollY;
    }

    public int getScrollX() {
        return scrollX;
    }
    public int getScrollY() {
        return scrollY;
    }

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
