package orienteering.orienteering;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class CreateRoutePoints extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private HttpManager httpManager;
    private int route_id;
    private Marker marker;
    private ArrayList<Marker> markers = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route_points);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        route_id = getIntent().getIntExtra("route_id", 0);

        httpManager = new HttpManager(this);
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
    }

    public void saveMarker(View v)
    {
        httpManager.pulldata(saveMarkerCallback, new String[]{"get", "point_of_interest"}, new String[]{"create_point_of_interest", String.valueOf(route_id)});
    }

    private DeserializeCallback saveMarkerCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            if (response.equals("success"))
            {
                Toast.makeText(activity, getResources().getString(R.string.create_point_success), Toast.LENGTH_SHORT).show();

                markers.add(marker);
                marker = null;
            }
            else
            {
                Toast.makeText(activity, getResources().getString(R.string.create_point_error), Toast.LENGTH_SHORT).show();
            }
        }
    };
}
