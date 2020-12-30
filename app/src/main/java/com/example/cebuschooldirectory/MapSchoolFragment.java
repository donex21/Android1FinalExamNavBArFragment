package com.example.cebuschooldirectory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapSchoolFragment extends Fragment {

    String title = "", vlat = "", vlong = "";

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            double Lat = Double.parseDouble(vlat);
            double Lng = Double.parseDouble(vlong);
            LatLng cebu = new LatLng(Lat, Lng);
            googleMap.addMarker(new MarkerOptions().position(cebu).title(title));
            googleMap.addCircle(new CircleOptions().center(cebu).radius(40).fillColor(Color.argb(10, 0, 30, 240))
                    .strokeColor(Color.argb(50, 0, 30, 240)).strokeWidth(2).zIndex(1));
            float zoomLevel = (float) 18.0;
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cebu, zoomLevel));

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_map_school, container, false);

        if(getArguments() != null){
            title = getArguments().getString("schoolname");
            vlat = getArguments().getString("schoollatitude");
            vlong = getArguments().getString("schoollongitude");
        }
        Toast.makeText(getActivity(), title +": "+ vlat +","+ vlong, Toast.LENGTH_SHORT).show();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}