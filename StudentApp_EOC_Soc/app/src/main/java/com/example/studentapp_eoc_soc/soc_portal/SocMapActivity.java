package com.example.studentapp_eoc_soc.soc_portal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.studentapp_eoc_soc.MainActivity;
import com.example.studentapp_eoc_soc.R;
import com.example.studentapp_eoc_soc.databinding.ActivitySocMapBinding;
import com.example.studentapp_eoc_soc.eating_on_campus.EocActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SocMapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    //variables necessary for google maps services and location tracking
    private GoogleMap map;
    private LocationManager locationManager;
    //setting minimum time and distance to pass before updating location
    private final long minTime = 30;
    private final float minDistance = 5;
    private static final int MY_PERMISSION_GPS = 1;
    double lat;
    double lon;

    // variables for the side menu drawer menu
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //variables needed to display username on screen
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // REFERENCE: https://www.youtube.com/watch?v=p0PoKEPI65o
        // This code is inspired by this youtube video but is edited quite substantially
        //variables for tracking location latitude and longitude
        com.example.studentapp_eoc_soc.databinding.ActivitySocMapBinding binding = ActivitySocMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpLocationTracking();

        name = findViewById(R.id.name);
        name.setText(String.format("Hello, %s!", MainActivity.user.getUserName()));

        // initialise side drawer menu
        loadSideMenu();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        // end reference
    }

    // reference: https://www.youtube.com/watch?v=fAXeq5F-CjI
    private void loadSideMenu() {
        drawerLayout = findViewById(R.id.drawer);
        navView = findViewById(R.id.navView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.menu_open, R.string.menu_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // return one level up rather than to the top level of app when selecting home (close menu)
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch(item.getItemId())
                {
                    // society map page -> home page
                    case R.id.home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        // close society map page & society portal
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // society map page -> society portal
                    case R.id.soc:
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    // society map page -> eating on campus
                    case R.id.food:
                        intent = new Intent(getApplicationContext(), EocActivity.class);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    public void setUpLocationTracking() {

        //making a new locationmanager for location services
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        //permission checking
        if (ContextCompat.checkSelfPermission(SocMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //if not granted, ask user for appropriate permissions
            ActivityCompat.requestPermissions(SocMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_GPS);
        }
        else {
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
        TextView textView2 = findViewById(R.id.text2);
        //geocoding coordinates to the address of the coordinates
        Geocoder geocoder = new Geocoder(SocMapActivity.this, Locale.getDefault());
        //using the distanceBetween method of locationlistener, to calculate the distance between
        //the current coordinates, and the known coordinates of the SU
        float[] results = new float[1];
        Location.distanceBetween(lat, lon,53.35444709347285,-6.279311777849542, results);
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
    public void onMapReady(@NonNull GoogleMap googleMap) {
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
