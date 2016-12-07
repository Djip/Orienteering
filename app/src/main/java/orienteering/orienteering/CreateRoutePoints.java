package orienteering.orienteering;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;

import orienteering.orienteering.Models.PointOfInterest;

public class CreateRoutePoints extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private HttpManager httpManager;
    private int route_id;
    private Marker marker;
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private Marker remove_marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route_points);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        activity = this;
        route_id = getIntent().getIntExtra("route_id", 0);
        httpManager = new HttpManager(this);

        Button done = (Button)findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker == null)
                {
                    marker = mMap.addMarker(new MarkerOptions().position(latLng));
                }
                else
                {
                    marker.setPosition(latLng);
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (markers.contains(marker))
                {
                    remove_marker = marker;
                }

                return false;
            }
        });
    }

    public void saveMarker(View v)
    {
        if (marker == null)
        {
            Toast.makeText(activity, getResources().getString(R.string.remove_point_error_no_point), Toast.LENGTH_SHORT).show();
        }
        else
        {
            XStream xstream = new XStream();
            xstream.alias("point_of_interest", PointOfInterest.class);
            PointOfInterest point_of_interest = new PointOfInterest(marker.getPosition());
            String point_of_interest_xml = xstream.toXML(point_of_interest);
            httpManager.pulldata(saveMarkerCallback, new String[]{"get", "point_of_interest", "route_id"}, new String[]{"create_point_of_interest", point_of_interest_xml, String.valueOf(route_id)});
        }
    }

    public void removeMarker(View v)
    {
        if (remove_marker == null)
        {
            Toast.makeText(activity, getResources().getString(R.string.remove_point_error_no_point), Toast.LENGTH_SHORT).show();
        }
        else
        {
            httpManager.pulldata(removeMarkerCallback, new String[]{"get", "latitude", "longitude", "route_id"}, new String[]{"remove_point_of_interest", String.valueOf(remove_marker.getPosition().latitude), String.valueOf(remove_marker.getPosition().longitude), String.valueOf(route_id)});
        }
    }

    private DeserializeCallback saveMarkerCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            if (response.trim().equals("success"))
            {
                Toast.makeText(activity, getResources().getString(R.string.create_point_success), Toast.LENGTH_SHORT).show();

                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                markers.add(marker);
                marker = null;
            }
            else
            {
                Toast.makeText(activity, getResources().getString(R.string.create_point_error), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private DeserializeCallback removeMarkerCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            if (response.trim().equals("success"))
            {
                Toast.makeText(activity, getResources().getString(R.string.remove_point_success), Toast.LENGTH_SHORT).show();

                markers.remove(remove_marker);
                remove_marker.remove();
                remove_marker = null;
            }
            else
            {
                Toast.makeText(activity, getResources().getString(R.string.remove_point_error), Toast.LENGTH_SHORT).show();
            }
        }
    };
}
