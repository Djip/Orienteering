package orienteering.orienteering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import java.sql.Timestamp;
import java.util.Random;

import orienteering.orienteering.Models.Answer;
import orienteering.orienteering.Models.AnswerList;
import orienteering.orienteering.Models.Category;
import orienteering.orienteering.Models.PointOfInterest;
import orienteering.orienteering.Models.PointOfInterestList;
import orienteering.orienteering.Models.PointTriggered;
import orienteering.orienteering.Models.PointTriggeredList;
import orienteering.orienteering.Models.Question;
import orienteering.orienteering.Models.QuestionList;
import orienteering.orienteering.Models.Toughness;
import orienteering.orienteering.Models.User;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    private GpsLocation gpsLocation;
    private Intent intent;
    private int toughness_id;
    private int category_id;
    private View point_popup_view;
    private TextView question_textview;
    private boolean can_be_used = true;
    private PointTriggeredList point_triggered_list;
    private User user;
    private long five_minutes = 1 * 60 * 1000;


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
        point_triggered_list = new PointTriggeredList();
        OrienteeringApplication orienteering_application = (OrienteeringApplication)getApplication();
        SharedPreferences shared_preferences = orienteering_application.getSharedPreferences();
        Gson gson = new Gson();
        user = gson.fromJson(shared_preferences.getString("user", null), User.class);
        point_triggered_list = new PointTriggeredList();
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
                can_be_used = true;
                marker.setTitle("");
                PointTriggered point_triggered = new PointTriggered(user.getId(), intent.getIntExtra("route_id", 0), marker.getPosition().latitude, marker.getPosition().longitude, new Timestamp(System.currentTimeMillis()));

                if(marker.getId().equals(gpsLocation.getUserMarker().getId())){

                } else {
                    LatLng lat_lng = marker.getPosition();
                    float[] distance = new float[1];
                    Location.distanceBetween(gpsLocation.lat, gpsLocation.lon, lat_lng.latitude, lat_lng.longitude, distance);
                    if (distance[0] > 15) {
                        marker.setTitle("Du er: " + distance[0] + "m fra punktet - du skal tættere på!");
                        //question_textview.setText("Du er: " + distance[0] + "m fra punktet - du skal tættere på!");
                    } else {
                        for (PointTriggered point_triggered_tmp : point_triggered_list.getPointTriggeredList()){
                            if(marker.getPosition().latitude == point_triggered_tmp.getLatitude() && marker.getPosition().longitude == point_triggered_tmp.getLongitude()){
                                Long now = System.currentTimeMillis();
                                if(point_triggered_tmp.getTimestamp().getTime() + five_minutes > now){
                                    can_be_used = false;
                                } else {
                                    can_be_used = true;
                                }
                            }
                        }
                        if (can_be_used){
                            point_triggered_list.add(point_triggered);
                            Intent intent = new Intent(MapsActivity.this, QuestionActivity.class);
                            intent.putExtra("category_id", category_id);
                            intent.putExtra("toughness_id", toughness_id);
                            intent.putExtra("route_id", getIntent().getIntExtra("route_id", 0));
                            Bundle marker_position = new Bundle();
                            marker_position.putParcelable("point", marker.getPosition());
                            intent.putExtra("point", marker_position);
                            startActivity(intent);
                        } else {
                            marker.setTitle("Du kan ikke benytte dette punkt endnu!");
                        }
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
