package orienteering.orienteering;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jespe on 16-11-2016.
 */

public class GpsLocation implements LocationListener{
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 1;

    private GpsLocation gpsLocation;
    private LocationManager locationManager;
    private Activity mapsActivity;
    private GoogleMap googleMap;
    private Marker userMarker;

    private int interval = 5000;
    private Handler handler;
    private boolean gpsUpdating;
    private boolean points_created;

    public double lat = 0.0d;
    public double lon = 0.0d;

    private boolean show_default_point_of_interest;
    private int route_id = 0;

    public void setRouteId(int route_id){
        this.route_id = route_id;
    }

    public void setShowDefaultPointOfInterest(boolean show_default_point_of_interest){
        this.show_default_point_of_interest = show_default_point_of_interest;
    }

    public GpsLocation(Activity mapsActivity, GoogleMap googleMap) {
        gpsLocation = this;
        this.mapsActivity = mapsActivity;
        this.googleMap = googleMap;
    }

    public void start(){
        locationManager = (LocationManager)mapsActivity.getSystemService(Context.LOCATION_SERVICE);

        handler = new Handler();
        mStatusChecker.run();

        if (mapsActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(mapsActivity, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            if (gpsUpdating != true) {
                gpsUpdating = true;

                if (mapsActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(mapsActivity, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                            MY_PERMISSION_ACCESS_COARSE_LOCATION);
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, gpsLocation);
            }

            handler.postDelayed(mStatusChecker, interval);
        }
    };

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        lat = location.getLatitude();
        lon = location.getLongitude();

        if(!points_created){
            points_created = true;
            PlaceHandler place_handler = new PlaceHandler(mapsActivity, lat, lon, googleMap);
            Log.e("OKK", String.valueOf(this.route_id));
            if(this.route_id > 0){
                place_handler.getRoutePoints(this.route_id);
            }
            if(this.show_default_point_of_interest){
                place_handler.execute();
            }
        }

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(lat, lon);
        if (userMarker == null) {
            userMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("This is you!"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            
        } else {
            userMarker.setPosition(latLng);
        }

        Log.d("Geo_Location", "Latitude: " + lat + ", Longitude: " + lon);

        gpsUpdating = false;
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}
