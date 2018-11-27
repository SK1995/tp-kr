package com.ksoldatov.kr.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ksoldatov.kr.R;

public class MapFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private com.google.android.gms.maps.MapFragment mapFragment;
    private Bundle args;
    public static String GEO_LAT = "GEO_LAT";
    public static String GEO_LONG = "GEO_LONG";
    private String geoLat;
    private String geoLong;
    private static View view;


    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        args = this.getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.map_fragment, container, false);
        } catch (InflateException e) {
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        geoLat = args.getString(GEO_LAT);
        geoLong = args.getString(GEO_LONG);

        android.app.FragmentManager manager = ((Activity) getContext()).getFragmentManager();
        mapFragment = (com.google.android.gms.maps.MapFragment) manager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (geoLat != null && geoLong != null) {
            map = googleMap;
            LatLng markerLoc = new LatLng(Double.parseDouble(geoLat), Double.parseDouble(geoLong));
            final CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(markerLoc)
                    .zoom(13)
                    .bearing(90)
                    .tilt(30)
                    .build();
            map.addMarker(new MarkerOptions().position(markerLoc));
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

}
