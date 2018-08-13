package com.example.rushita.barrbers.Fragment;

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
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rushita.barrbers.Adapter.MyCustomPageAdapter;
import com.example.rushita.barrbers.Adapter.SearchListAdapter;
import com.example.rushita.barrbers.Barbers;
import com.example.rushita.barrbers.R;
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

import java.util.List;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;

public class Searchlist extends Fragment implements OnMapReadyCallback,LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener   {
    ListView listView,listView1;

    SearchListAdapter searchListAdapter;
    RelativeLayout listclick,mapclick;
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
    TextView text,txtlistwhite,txtlistgray,txtmapwhite,txtmapgray;
    ImageView listimagewhite,mapimagewhite,mapgrayimage,listimagegray;

    String[] title = new String[]{"Barbers-Shaves and Trims","Barbers-Shaves and Trims","Barbers-Shaves and Trims","Barbers-Shaves and Trims"};
    String[] mill = new  String[]{"2.8 miles away from you","2.8 miles away from you","2.8 miles away from you","2.8 miles away from you"};
    String[] will = new  String[]{"12 William.Road SE13 6FK","12 William.Road SE13 6FK","12 William.Road SE13 6FK","12 William.Road SE13 6FK"};
    String[] time1 = new String[]{"Open-9am to 6pm","Open-9am to 6pm","Open-9am to 6pm","Open-9am to 6pm"};

    String[] titl = new String[]{"Barbers-Shaves and Trims"};
    String[] mll = new  String[]{"2.8 miles away from you"};
    String[] wll = new  String[]{"12 William.Road SE13 6FK"};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seachlist, container, false);
        listView = (ListView) view.findViewById(R.id.listview);
        listclick=(RelativeLayout)view.findViewById(R.id.listclick);
        mapclick=(RelativeLayout)view.findViewById(R.id.mapclick);
        txtlistwhite = (TextView) view.findViewById(R.id.txtlistwhite);
        txtlistgray = (TextView) view.findViewById(R.id.txtlistgray);
        txtmapwhite = (TextView) view.findViewById(R.id.txtmapwhite);
        listimagegray = (ImageView) view.findViewById(R.id.listimagegray);
        text = (TextView) view.findViewById(R.id.text);
//        listView1=(ListView)view.findViewById(R.id.list1);
        listimagewhite = (ImageView) view.findViewById(R.id.listimagewhite);
        mapimagewhite = (ImageView) view.findViewById(R.id.mapimagewhite);
        mapgrayimage = (ImageView) view.findViewById(R.id.mapgrayimage);
        txtmapgray = (TextView) view.findViewById(R.id.txtmapgray);


        Log.e("list", "" + title.length);
        searchListAdapter = new SearchListAdapter(getActivity(), title, mill, will, time1 , new SearchListAdapter.ClickCallBack() {
            @Override
            public void listclick(int position) {
                Intent myintent1 = new Intent(getActivity(), Barbers.class);
                myintent1.putExtra("title",title[position]);
                myintent1.putExtra("mil",mill[position]);
                myintent1.putExtra("wil",will[position]);
                myintent1.putExtra("tim",time1[position]);
                startActivity(myintent1);
            }
        });
        listView.setAdapter(searchListAdapter);
        mMapView=(MapView)view.findViewById(R.id.mMapView);
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        Criteria criteria = new Criteria();
        mprovider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
                MapsInitializer.initialize(getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMapView.getMapAsync(new OnMapReadyCallback() {
                @SuppressLint("LongLogTag")
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    // For showing a move to my location button
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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

        listclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setVisibility(View.VISIBLE);
               mMapView.setVisibility(View.GONE);

                txtlistwhite.setVisibility(View.VISIBLE);
                txtlistgray.setVisibility(View.GONE);
                listimagewhite.setVisibility(View.VISIBLE);
                listimagegray.setVisibility(View.GONE);
                mapgrayimage.setVisibility(View.VISIBLE);
                txtmapgray.setVisibility(View.VISIBLE);


            }
        });
        mapclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

                txtmapwhite.setVisibility(View.VISIBLE);
                mapgrayimage.setVisibility(View.GONE);
                mapimagewhite.setVisibility(View.VISIBLE);
                txtmapgray.setVisibility(View.GONE);
                txtlistgray.setVisibility(View.VISIBLE);
                listimagegray.setVisibility(View.VISIBLE);



            }
        });

        return view;

    }
    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
