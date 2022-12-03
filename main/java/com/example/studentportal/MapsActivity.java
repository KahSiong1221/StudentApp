
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

    //variables necessary for google maps services and location tracking
    private GoogleMap map;
    private LocationManager locationManager;
    //setting minimum time and distance to pass before updating location
    private long minTime = 30;
    private float minDistance = 5;
    private static final int MY_PERMISSION_GPS = 1;
    //variables for tracking location latitude and longitude
    private ActivityMapsBinding binding;
    double lat;
    double lon;
    //variables needed to display username on screen
    TextView name;
    String username = "Alex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // REFERENCE: https://www.youtube.com/watch?v=p0PoKEPI65o
        // This code is inspired by this youtube video but is edited quite substantially
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpLocationTracking();
        name = (TextView) findViewById(R.id.name);
        name.setText("Hello, "+username+"!");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // end reference
    }

    public void setUpLocationTracking() {

        //making a new locationmanager for location services
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //permission checking
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            //if not granted, ask user for appropriate permissions
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_GPS);
        }
        else
        {
            //else, go ahead and start location tracking
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
        }
    }

    // runs when a location of the phone is changed
    public void onLocationChanged(Location location)
    {
        //getting current latitude and longitude
        lat = location.getLatitude();
        lon = location.getLongitude();
        TextView textView2 = (TextView) findViewById(R.id.text2);
        //geocoding coordinates to the address of the coordinates
        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
        //using the distanceBetween method of locationlistener, to calculate the distance between
        //the current coordinates, and the known coordinates of the SU
        float[] results = new float[1];
        location.distanceBetween(lat, lon,53.35444709347285,-6.279311777849542, results);
        int distance = (int) results[0];
        try {//Displaying the users address, and the distance from the SU in meters
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String addressName ="Your location at "
                    + addresses.get(0).getAddressLine(0)+
                    " is " + distance + "m from the SU! Come visit!";
            textView2.setText(addressName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // REFERENCE: https://www.youtube.com/watch?v=p0PoKEPI65o
    // This code is inspired by this youtube video but is edited quite substantially
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //get a google map object and set the zoom appropriately
        map = googleMap;
        map.setMinZoomPreference(13.0f);
        map.setMaxZoomPreference(14.0f);
        //set the SU coordinates as their known coordinates, using a LatLng variable
        LatLng SU = new LatLng(53.35444709347285, -6.279311777849542);
        //add a marker on the map at the SU coordinates
        map.addMarker(new MarkerOptions().position(SU).title("TU Dublin Students Union"));
        //set the camera to focus on the marker by default
        map.moveCamera(CameraUpdateFactory.newLatLng(SU));
    //end reference
    }


}