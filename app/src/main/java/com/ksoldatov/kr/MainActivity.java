package com.ksoldatov.kr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ksoldatov.kr.ui.HistoryFragment;
import com.ksoldatov.kr.ui.MapFragment;
import com.ksoldatov.kr.ui.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static String MAP_EXTRA = "MAP_EXTRA";
    public static String GEO_LAT = "GEO_LAT";
    public static String GEO_LONG = "GEO_LONG";

    private boolean refreshFragmant(int fragmentId, Bundle args) {
        Fragment selectedFragment = null;
        String fragmentName = null;
        switch (fragmentId) {
            case R.id.action_history:
                selectedFragment = HistoryFragment.newInstance();
                break;
            case R.id.action_map:
                selectedFragment = MapFragment.newInstance();
                break;
            case R.id.action_search:
                selectedFragment = SearchFragment.newInstance();
                break;
        }
        FragmentManager manager = getSupportFragmentManager();
        if (selectedFragment != null) {
            fragmentName = selectedFragment.getClass().getName();
            selectedFragment.setArguments(args);
        }
        boolean isBackStackEmpty = manager.popBackStackImmediate(fragmentName, 0);
        if (!isBackStackEmpty) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_layout, selectedFragment);
            ft.addToBackStack(fragmentName);
            ft.commit();
        }
        return true;
    }

    private void initInflate() {
        Fragment searchFragment = SearchFragment.newInstance();
        String searchFragmentClassName = searchFragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frame_layout, searchFragment);
        ft.addToBackStack(searchFragmentClassName);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check intent to open map
        Intent intent = getIntent();
        if (intent != null) {
            int mapFragmentId;
            String geoLat, geoLong;
            mapFragmentId = intent.getIntExtra(MAP_EXTRA, 0);
            geoLat = intent.getStringExtra(GEO_LAT);
            geoLong = intent.getStringExtra(GEO_LONG);
            if (mapFragmentId != 0) {
                Bundle args = new Bundle();
                args.putString(GEO_LAT, geoLat);
                args.putString(GEO_LONG, geoLong);
                refreshFragmant(mapFragmentId, args);
            }
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> refreshFragmant(item.getItemId(), new Bundle()));

        if (savedInstanceState == null && !intent.hasExtra(MAP_EXTRA)) {
            initInflate();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

}

