package com.example.j457liu.fotagj457liu;

import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.view.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

// MainActivity class
public class MainActivity extends AppCompatActivity implements Observer {
    private Model model;
    private DrawerLayout mDrawerLayout;
    private ViewGroup main_view;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int orientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

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

        // Retrieve main_view if already in model
        if (model.main_view != null) {


            int childCount = model.main_view.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                View v = model.main_view.getChildAt(0);
                model.main_view.removeView(v);
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // set contentView layout width 1/2 screen
                    LinearLayout.LayoutParams paramsLinear = new LinearLayout.LayoutParams(
                            width / 2, ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    v.setLayoutParams(paramsLinear);

                    // set image layout center in contentView and leave some blank space
                    RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(
                            width * 2 / 5, height / 2
                    );
                    paramsRelative.addRule(RelativeLayout.CENTER_IN_PARENT,
                            RelativeLayout.TRUE);
                    ((ContentView) v).image.setLayoutParams(paramsRelative);
                } else {
                    LinearLayout.LayoutParams paramsLinear = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    v.setLayoutParams(paramsLinear);

                    // set image layout center in contentView and leave some blank space
                    RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(
                            width * 2 / 3, height / 2
                    );
                    paramsRelative.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    ((ContentView) v).image.setLayoutParams(paramsRelative);
                }
                main_view.addView(v);
            }
        }
        model.main_view = main_view;
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
        spinner = (Spinner) navigationView.getMenu()
                .findItem(R.id.filter).getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rating, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setId(View.generateViewId());
        if (model.getFilterLevel() == 0) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(6 - (int) model.getFilterLevel());
        }
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // set filter level
                if (pos == 0) {
                    model.setFilterLevel(0);
                } else {
                    model.setFilterLevel(6 - pos);
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
     * Method to load contents
     */
    public void loadImage() {
        // call clear first
        clear();
        List<PictureData> pList = new ArrayList<>();

        // get urls from xlm resource
        String[] array = getResources().getStringArray(R.array.urls);

        // remove all views already on the view -> allow load without clear
        main_view.removeAllViews();

        int orientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        for (String s : array) {
            PictureData p = new PictureData(s, 0f, true);
            pList.add(p);
            ContentView contentView = new ContentView(this, model, s);
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {   // landscape
                // set contentView layout width 1/2 screen
                LinearLayout.LayoutParams paramsLinear = new LinearLayout.LayoutParams(
                        width / 2, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                contentView.setLayoutParams(paramsLinear);

                // set image layout center in contentView and leave some blank space
                RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(
                        width * 2 / 5, height / 2
                );
                paramsRelative.addRule(RelativeLayout.CENTER_IN_PARENT,
                        RelativeLayout.TRUE);
                contentView.image.setLayoutParams(paramsRelative);
            } else {
                LinearLayout.LayoutParams paramsLinear = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                contentView.setLayoutParams(paramsLinear);

                // set image layout center in contentView and leave some blank space
                RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(
                        width * 2 / 3, height / 2
                );
                paramsRelative.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                contentView.image.setLayoutParams(paramsRelative);
            }

            main_view.addView(contentView);
        }
        model.setPictureDataList(pList);

        // reset filter item
        spinner.setSelection(0);
        model.setFilterLevel(0);
    }

    /**
     * Method to clear all contents
     */
    public void clear() {
        main_view.removeAllViews();
        model.setPictureDataList(new ArrayList<PictureData>()); // optional
        model.setFilterLevel(0);
        model.deleteObservers();
        model.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        // intentionally empty
    }
}