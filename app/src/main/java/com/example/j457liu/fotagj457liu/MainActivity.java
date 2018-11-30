package com.example.j457liu.fotagj457liu;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TableRow;

import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {
    Model model;
    DrawerLayout mDrawerLayout;
    ViewGroup main_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_view = (ViewGroup) findViewById(R.id.scroll_view_layout);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Set model
        model = Model.getInstance();
        model.addObserver(this);
        model.initObservers();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if (menuItem.getItemId() != R.id.filter)
                            mDrawerLayout.closeDrawers();
                        if (menuItem.getItemId() == R.id.load) {
                            loadImage();
                        }
                        if (menuItem.getItemId() == R.id.clear) {
                            clear();
                        }
                        return true;
                    }
                });

        // set filter Spinner
        Spinner spinner = (Spinner) navigationView.getMenu()
                .findItem(R.id.filter).getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rating, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // set model filter level
                switch (pos) {
                    case 0: {
                        model.setFilter(0);
                        break;
                    }
                    case 1: {
                        model.setFilter(5);
                        break;
                    }
                    case 2: {
                        model.setFilter(4);
                        break;
                    }
                    case 3: {
                        model.setFilter(3);
                        break;
                    }
                    case 4: {
                        model.setFilter(2);
                        break;
                    }
                    case 5: {
                        model.setFilter(1);
                        break;
                    }
                    default: {
                        break;
                    }

                }

                mDrawerLayout.closeDrawers();
                model.initObservers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.deleteObserver(this);
    }

    /**
     * Method to load image from urlList
     */
    public void loadImage() {
        // pass urls in resource file to model
        String[] array = getResources().getStringArray(R.array.urls);
        List<PictureData> pList = new ArrayList<>();
        for (String s : array) {
            PictureData p = new PictureData(s, 0f, true);
            pList.add(p);
            model.setPictureDataList(pList);
        }

        // notify canvas to start loading
        model.initObservers();
    }

    /**
     * Method to clear all the image from urlList
     */
    public void clear() {
        List<PictureData> pList = new ArrayList<>();
        model.setPictureDataList(pList);
        model.initObservers();
    }

    @Override
    public void update(Observable o, Object arg) {
        main_view.removeAllViews();
        List<PictureData> pList = model.getPictureDataList();

        for (PictureData p : pList) {
            if (p.getVisible()) {
                ContentView contentView = new ContentView(this, model, p.getUrl());
//                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        1.0f
//                );
//                contentView.setLayoutParams(param);
                main_view.addView(contentView);
            }
        }
    }
}