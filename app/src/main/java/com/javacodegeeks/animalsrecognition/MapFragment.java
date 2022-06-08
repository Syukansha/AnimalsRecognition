package com.javacodegeeks.animalsrecognition;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MapFragment extends Fragment {
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String value1 = "3.2102";
    public static final String value2 = "101.7592";
    private Button zooNegara, zooMelaka, zooTaiping;
    SharedPreferences sharedpreferences;
    String val1, val2, loc;

    Location location;
    LocationManager locationManager;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        sharedpreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);


        /*locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location location = (Location) task.getResult();
            val1 = String.valueOf(location.getLatitude());
            val2 = String.valueOf(location.getLongitude());

            loc = val1+","+val2;
        }*/
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // method to get the location


        zooNegara = view.findViewById(R.id.negara);
        zooNegara.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("MissingPermission")
            public void onClick(View view) {
                // check if permissions are given
                if (checkPermissions()) {

                    // check if location is enabled
                    if (isLocationEnabled()) {

                        // getting last
                        // location from
                        // FusedLocationClient
                        // object

                        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    val1 = String.valueOf(location.getLatitude());
                                    val2 = String.valueOf(location.getLongitude());
                                    loc = val1 + "," + val2;
                                    DisplayTrackNegara(loc, "zoo negara");

                                }
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }

                }
                else {
                    // if permissions aren't available,
                    // request for permissions
                    requestPermissions();
                }
            }


            private void DisplayTrackNegara(String loc, String zoo_negara) {
                try {
                    Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + loc + "/Zoo Negara");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        zooMelaka = view.findViewById(R.id.melaka);
        zooMelaka.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("MissingPermission")
            public void onClick(View view) {
                // check if permissions are given
                if (checkPermissions()) {

                    // check if location is enabled
                    if (isLocationEnabled()) {

                        // getting last
                        // location from
                        // FusedLocationClient
                        // object

                        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    val1 = String.valueOf(location.getLatitude());
                                    val2 = String.valueOf(location.getLongitude());
                                    loc = val1 + "," + val2;
                                    DisplayTrackMelaka(loc, "zoo melaka");

                                }
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }

                }
                else {
                    // if permissions aren't available,
                    // request for permissions
                    requestPermissions();
                }
            }


            private void DisplayTrackMelaka(String loc, String zoo) {
                try {
                    Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + loc + "/"+zoo);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        zooTaiping = view.findViewById(R.id.taiping);
        zooTaiping.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("MissingPermission")
            public void onClick(View view) {
                // check if permissions are given
                if (checkPermissions()) {

                    // check if location is enabled
                    if (isLocationEnabled()) {

                        // getting last
                        // location from
                        // FusedLocationClient
                        // object

                        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    val1 = String.valueOf(location.getLatitude());
                                    val2 = String.valueOf(location.getLongitude());
                                    loc = val1 + "," + val2;
                                    DisplayTrackTaiping(loc, "zoo taiping");

                                }
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }

                }
                else {
                    // if permissions aren't available,
                    // request for permissions
                    requestPermissions();
                }
            }


            private void DisplayTrackTaiping(String loc, String zoo) {
                try {
                    Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + loc + "/"+zoo);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    intent.setPackage("com.google.android.apps.maps");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        return view;
    }




    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then

}