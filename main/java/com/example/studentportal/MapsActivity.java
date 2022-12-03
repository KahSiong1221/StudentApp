package com.example.studentportal;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.studentportal.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap map;
    private LocationManager locationManager;
    private long minTime = 30;
    private float minDistance = 5;
    private static final int MY_PERMISSION_GPS = 1;
    private ActivityMapsBinding binding;
    double lat;
    double lon;
    TextView name;
    String username = "Alex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpLocationTracking();
        name = (TextView) findViewById(R.id.name);
        name.setText("Hello, "+username+"!");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void setUpLocationTracking() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_GPS);
        }
        else
        {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
        }
    }

    public void onLocationChanged(Location location)
    {
        lat = location.getLatitude();
        lon = location.getLongitude();
        TextView textView2 = (TextView) findViewById(R.id.text2);
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        float[] results = new float[1];
        location.distanceBetween(lat, lon,53.35444709347285,-6.279311777849542, results);
        int distance = (int) results[0];
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String addressName ="Your location at "
                    + addresses.get(0).getAddressLine(0)+
                    " is " + distance + "m from the SU! Come visit!";
            textView2.setText(addressName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_GPS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Please Allow Permissions!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(13.0f);
        map.setMaxZoomPreference(14.0f);
        LatLng SU = new LatLng(53.35444709347285, -6.279311777849542);
        map.addMarker(new MarkerOptions().position(SU).title("TU Dublin Students Union"));
        map.moveCamera(CameraUpdateFactory.newLatLng(SU));

    }


}