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

import orienteering.orienteering.Models.Category;
import orienteering.orienteering.Models.PointOfInterest;
import orienteering.orienteering.Models.PointOfInterestList;
import orienteering.orienteering.Models.Question;
import orienteering.orienteering.Models.QuestionList;
import orienteering.orienteering.Models.Toughness;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.InfoWindowAdapter {

    private final QuestionList question_list = new QuestionList();
    private GoogleMap mMap;
    private GpsLocation gpsLocation;
    private Intent intent;
    private Toughness toughness;
    private Category category;
    private Random randomGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        intent = getIntent();
        toughness = new Toughness(intent.getIntExtra("toughness", 0));
        category = new Category(intent.getIntExtra("category", 0));
        randomGenerator = new Random();
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
        QuestionHandler question_handler = new QuestionHandler(this, mMap);
        question_handler.getQuestionList(getQuestionListCallback);
        gpsLocation.start();
        final Activity activity = this;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getTitle() == "This is you!"){
                    // ingenting
                } else {
                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {
                            View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                            LatLng lat_lng = marker.getPosition();
                            final TextView question_popup = (TextView) v.findViewById(R.id.question_box);
                            float[] distance = new float[1];
                            Location.distanceBetween(gpsLocation.lat, gpsLocation.lon, lat_lng.latitude, lat_lng.longitude, distance);
                            if(distance[0] > 2000){
                                question_popup.setText("Du er: " + distance[0] + "m fra punktet - du skal tættere på!");
                            } else {
                                int index = randomGenerator.nextInt(question_list.getQuestions().size());
                                question_popup.setText("Spørgsmål:\n" + question_list.getQuestions().get(index).getQuestion());
                            }

                            return v;
                        }
                    });

                }
                return false;
            }
        });
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    DeserializeCallback getQuestionListCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            try {
                XStream xstream = new XStream();
                xstream.alias("question", Question.class);
                xstream.alias("questions", QuestionList.class);
                xstream.addImplicitCollection(QuestionList.class, "questions");
                QuestionList question_list_tmp = (QuestionList) xstream.fromXML(response);
                for (Question question : question_list_tmp.getQuestions())
                {
                    if (toughness.getId() == question.getToughnessId() && category.getId() == question.getCategoryId()){
                        question_list.add(question);
                    }

                }
                for (Question question : question_list.getQuestions())
                {
                    Log.e("OKK", question.getQuestion());
                    Log.e("OKK", "Test23");
                }
            } catch (Exception e) {
                Log.e("OKK", e.getMessage());
            }
        }
    };
}
