package orienteering.orienteering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thoughtworks.xstream.XStream;

import java.util.Random;

import orienteering.orienteering.Models.Answer;
import orienteering.orienteering.Models.AnswerList;
import orienteering.orienteering.Models.Category;
import orienteering.orienteering.Models.PointOfInterest;
import orienteering.orienteering.Models.PointOfInterestList;
import orienteering.orienteering.Models.Question;
import orienteering.orienteering.Models.QuestionList;
import orienteering.orienteering.Models.Toughness;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private GpsLocation gpsLocation;
    private Intent intent;
    private int toughness_id;
    private int category_id;
    private View point_popup_view;
    private TextView question_textview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        intent = getIntent();
        toughness_id = intent.getIntExtra("toughness", 0);
        category_id = intent.getIntExtra("category", 0);
        point_popup_view = getLayoutInflater().inflate(R.layout.info_window_layout, null);
        question_textview = (TextView) point_popup_view.findViewById(R.id.question_textview);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        intent = getIntent();
        gpsLocation = new GpsLocation(this, googleMap);
        gpsLocation.setRouteId(intent.getIntExtra("route_id", 0));
        gpsLocation.setShowDefaultPointOfInterest(intent.getBooleanExtra("show_default_point_of_interest", false));

        gpsLocation.start();
        final Activity activity = this;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getId().equals(gpsLocation.getUserMarker().getId())){

                } else {
                    Log.e("OKK", marker.getId() + "------" + gpsLocation.getUserMarker().getId());
                    LatLng lat_lng = marker.getPosition();
                    float[] distance = new float[1];
                    Location.distanceBetween(gpsLocation.lat, gpsLocation.lon, lat_lng.latitude, lat_lng.longitude, distance);
                    if (distance[0] > 2000) {
                        marker.setTitle("Du er: " + distance[0] + "m fra punktet - du skal tættere på!");
                        //question_textview.setText("Du er: " + distance[0] + "m fra punktet - du skal tættere på!");
                    } else {
                        Intent intent = new Intent(MapsActivity.this, QuestionActivity.class);
                        intent.putExtra("category_id", category_id);
                        intent.putExtra("toughness_id", toughness_id);
                        startActivity(intent);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}
