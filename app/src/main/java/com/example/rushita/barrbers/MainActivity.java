package com.example.rushita.barrbers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.rushita.barrbers.Fragment.Apointment;
import com.example.rushita.barrbers.Fragment.Profile_Account;
import com.example.rushita.barrbers.Fragment.Searchlist;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    RelativeLayout location1;
    LocationRequest mLocationRequest;
    LocationManager locationManager;
    String refreshedToken;
    private int locationRequests = 0;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 11;
    boolean isGPSEnabled = false;
    GoogleApiClient mGoogleApiClient;


    final int[] ICONS = new int[]{
            R.mipmap.account,
            R.mipmap.map,
            R.mipmap.list,

    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        int priority = mLocationRequest.getPriority();
        buildGoogleApiClient();

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("value", "Permission already Granted, Now you can capture image.");

            } else {
                Log.e("", "=================request for permission1=====================");
                requestPermission();
            }
        } else {
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            public void onTabSelected(TabLayout.Tab tab) {
                int tab_position = tab.getPosition();
                Log.e("pos", "" + tab.getPosition());
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                viewPager.setCurrentItem(tab_position);
//                viewPager.setOffscreenPageLimit(0);
                if (tab.getPosition() == 0) {
                    location1.setBackgroundResource(R.drawable.round1);
//                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
//                    viewPager.setAdapter(viewPagerAdapter);

                    viewPager.setCurrentItem(0);
                    viewPager.setOffscreenPageLimit(1);
                } else if (tab.getPosition() == 1) {
                    location1.setBackgroundResource(R.drawable.round);
//                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
//                    viewPager.setAdapter(viewPagerAdapter);
                    viewPager.setCurrentItem(1);
                    viewPager.setOffscreenPageLimit(1);
                }
                else if (tab.getPosition() == 2) {
                    location1.setBackgroundResource(R.drawable.round1);
//                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
//                    viewPager.setAdapter(viewPagerAdapter);

                    viewPager.setCurrentItem(2);
                    viewPager.setOffscreenPageLimit(1);
                }

            }


            public void onTabUnselected(TabLayout.Tab tab) {

                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.colorLightGray);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                if (tab.getPosition() == 1) {
                    location1.setBackgroundResource(R.drawable.round1);


                }


            }


            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        Viewpager.setCurrentItem(1);
        Viewpager.setOffscreenPageLimit(0);
        location1 = (RelativeLayout) findViewById(R.id.location1);
        location1.setBackgroundResource(R.drawable.round);
        location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location1.setBackgroundResource(R.drawable.round);
                viewPager.setCurrentItem(1);

                }
        });

//        Viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//
//            }
//
//            public void onPageSelected(int position) {
//
//            }
//
//
//            public void onPageScrollStateChanged(int state) {
//
//            }
//
//        });


    }

    private void buildGoogleApiClient() {
        Log.e("Build Api Connected", "yes");
        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        if (location != null) {
            Log.e("HOME ACTIVITY-location ", "Latitude:" + latitude + ", Longitude:" + longitude);
//            GetCityCountry(latitude, longitude);
            //    editor.putString("Latitude", "" + location.getLatitude());
            //  editor.putString("Longitude", "" + location.getLongitude());
            //editor.commit();
//
            // We need to fetch the location only once
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        } else {
            Log.e("HOME ACTIVITY-location", "get location error");
        }


    }

    @SuppressLint("LongLogTag")
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Log.e("Build Api Connected OnStart", "yes");
        mGoogleApiClient.connect();
        Log.e("Build Api Connected OnStart Outer", "yes");
        if (mGoogleApiClient.isConnected()) {
            Log.e("Build Api Connected OnStart If", "yes");
            getLocation();
        }
    }

    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            getLocation();
        }
        //        if (mGoogleApiClient.isConnected() && I_City!=null) {
        //        getLocation();
        //        }
    }

    @SuppressLint("LongLogTag")
    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e("HOME ACTIVITY get location if", "yes");
            // Request permission for marshmallow
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE_LOCATION);
                return;
            }
        }


    }

    private static final int PERMISSION_REQUEST_CODE = 1;

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
//            Toast.makeText(Home_page.this, getResources().getString(R.string.image_permission), Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can call.");
                } else {
                    Log.e("value", "Permission Denied, You cannot call.");
                }
                break;

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;


        private ViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;

        }


        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    Log.e("pgr",""+position);
                    return new Profile_Account();
                case 1:
                    Log.e("pgr1",""+position);
                    return new Searchlist();
                case 2:
                    Log.e("pgr2",""+position);
                    return new Apointment();


                default:
                    return null;
            }
        }


        public int getCount() {
            return mNumOfTabs;

        }


    }
}
