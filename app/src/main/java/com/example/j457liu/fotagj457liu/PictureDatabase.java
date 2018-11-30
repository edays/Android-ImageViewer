package com.example.j457liu.fotagj457liu;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import java.util.List;

/*
 * DAO to access picture entity
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM picture")
    List<Picture> getAll();

    @Query("SELECT * FROM picture WHERE url = (:url)")
    Picture getByUrl(String url);

    @Query("SELECT url FROM picture")
    List<String> getUrls();

    @Query("SELECT url FROM picture WHERE rating > (:f)")
    List<String> getUrlByRatingHigherThan(float f);

    @Query("UPDATE picture SET rating = (:f) WHERE url = (:url)")
    void updateRating(String url, float f);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Picture picture);

    @Delete
    void delete(Picture picture);

    @Query("DELETE FROM picture")
    void deleteALL();
}

/*
 * Entity for Picture
 */
@Entity
class Picture {
    @PrimaryKey                         // Contains https links of picture reference
    @NonNull
    public String url;

    @ColumnInfo(name = "rating")        // Range[0,5]
    public float rating;

    // Get https link
    public String getUrl() {
        return url;
    }

    // Set https to url
    public void setUrl(String url) {
        this.url = url;
    }

    // Get rating for an image
    public float getRating() {
        return rating;
    }

    // Set rating for an image
    public void setRating(int rating) {
        this.rating = rating;
    }
}

/*
 * Database
 */
@Database(entities = {Picture.class}, version = 3, exportSchema = false)
abstract class PictureDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}