package com.example.rushita.barrbers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rushita.barrbers.Adapter.MyCustomPageAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viewpagerindicator.CirclePageIndicator;

import de.hdodenhof.circleimageview.CircleImageView;

public class Barbers extends AppCompatActivity implements OnMapReadyCallback,LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    int images[] = {R.mipmap.image2, R.mipmap.image3, R.mipmap.image2};
    MyCustomPageAdapter myCustomPagerAdapter;
    CircleImageView image;
    TextView txt_title, txt_mil, txt_will, txt_time;
    RelativeLayout location1;
    private GoogleMap mMap;
    private GoogleMap googleMap;
    Location location;
    LatLng latLng;
    LocationManager locationManager;
    GoogleApiClient mGoogleApiClient;
    MapView mMapView;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private static final int PERMISSION_REQUEST_CODE = 1;
    boolean isGPSEnabled;
    String lattitude, longitude, mprovider;
    double qlattitude, qlongitude;
    Marker currLocationMarker;
    LocationRequest mLocationRequest;
    double clattitude, clongitude;
    ImageView back;
    CirclePageIndicator pgindicator;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbers);
        ViewPager viewPager;
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        txt_title = (TextView) findViewById(R.id.txt_title);
       txt_mil = (TextView) findViewById(R.id.txt_mil);
      txt_will = (TextView)findViewById(R.id.txt_will);
       txt_time = (TextView)findViewById(R.id.txt_time);
       back=(ImageView)findViewById(R.id.back);
        pgindicator=(CirclePageIndicator)findViewById(R.id.pgindicator);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });

       String text1= getIntent().getStringExtra("title");
        txt_title.setText(text1);
        String text2=getIntent().getStringExtra("mil");
        txt_mil.setText(text2);
        String text3=getIntent().getStringExtra("wil");
        txt_will.setText(text3);
        String text4=getIntent().getStringExtra("tim");
        txt_time.setText(text4);
        myCustomPagerAdapter = new MyCustomPageAdapter(Barbers.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);
        mMapView=(MapView)findViewById(R.id.mMapView);
        locationManager = (LocationManager) Barbers.this.getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        Criteria criteria = new Criteria();
        mprovider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(Barbers.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Barbers.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


//        location = locationManager.getLastKnownLocation(mprovider);


            // needed to get the map to display immediately
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(2000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            int priority = mLocationRequest.getPriority();

            mprovider = locationManager.getBestProvider(criteria, false);

            if (!isGPSEnabled) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Barbers.this);
                // Setting Dialog Title
                alertDialog.setTitle(getResources().getString(R.string.gps_setting));
                // Setting Dialog Message
                alertDialog.setMessage(getResources().getString(R.string.enable_gps));
                // On pressing Settings button
                alertDialog.setPositiveButton(getResources().getString(R.string.setting), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myAppSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myAppSettings);

                    }
                });
                // on pressing cancel button
                alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialog.show();


            }

        }
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(Barbers.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(Barbers.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Barbers.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);
                buildGoogleApiClient();

                mGoogleApiClient.connect();
            }
        });
        if (isGPSEnabled) {
            Log.e("GPS Enabled ,", "" + isGPSEnabled);
            try {
                MapsInitializer.initialize(Barbers.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @SuppressLint("LongLogTag")
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    // For showing a move to my location button
                    if (ActivityCompat.checkSelfPermission(Barbers.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Barbers.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    // For showing a move to my location button
                    googleMap.setMyLocationEnabled(true);
                    buildGoogleApiClient();

                    mGoogleApiClient.connect();

//                    if (location != null) {
//                        Log.e("TAG", "GPS is on");
//                        // Log.e("USER DASHBORD not be null", "lattitude:" + location.getLongitude() + ",longitude:" + location.getLongitude());
//
//                    } else {
//
//                        //This is what you need:
//                        //  onLocationChanged(location);
//                    }
                    //

                }
            });
        }

    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(Barbers.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.e("Connect API ,", "connect");
    }
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        currLocationMarker = googleMap.addMarker(markerOptions);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


        currLocationMarker = googleMap.addMarker(markerOptions);
        Log.e("Latitude ,", "" + location.getLatitude());
        Log.e("Longitude ,", "" + location.getLongitude());
        //move map camera
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }



    public void onStatusChanged (String s,int i, Bundle bundle){

    }


    public void onProviderEnabled (String s){

    }


    public void onProviderDisabled (String s){

    }


    public void onConnected (Bundle bundle){
        Log.e("ON Connected ,", "Connect ");
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        if (ActivityCompat.checkSelfPermission(Barbers.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Barbers.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


//        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{
//                    android.Manifest.permission.ACCESS_FINE_LOCATION
//            }, 10);
//        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Log.e("Get Location ,", "Not Null ");

            //place marker at current position
            //mGoogleMap.clear();
            //Log.e("USER mLastLocation", "lattitude:" + mLastLocation.getLatitude() + ",longitude:" + mLastLocation.getLongitude());
            longitude = "" + mLastLocation.getLongitude();
            lattitude = "" + mLastLocation.getLatitude();
            qlongitude = mLastLocation.getLongitude();
            qlattitude = mLastLocation.getLatitude();
            Log.e("USER " +
                    "", "lattitude:" + qlattitude + ",longitude:" + qlongitude);

            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            currLocationMarker = googleMap.addMarker(markerOptions);
            Log.e("MAP FRAGMENT,", "mCurrLocationMarker");
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }




    public void onConnectionSuspended ( int i){

    }


    public void onConnectionFailed (ConnectionResult connectionResult){

    }
    public void onMapReady(GoogleMap googleMap) {

    }


}
