package orienteering.orienteering;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thoughtworks.xstream.XStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import orienteering.orienteering.Models.PointOfInterest;
import orienteering.orienteering.Models.PointOfInterestList;
import orienteering.orienteering.Models.User;
import orienteering.orienteering.Models.UserList;

public class PlaceHandler {
    Activity maps_activity;
    GoogleMap map;

    public PlaceHandler(Activity maps_activity){
        this.maps_activity = maps_activity;
    }

    public void getRoutePoints(final GoogleMap map, int route_id){
        HttpManager httpManager = new HttpManager(maps_activity);
        httpManager.pulldata(new DeserializeCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    XStream xstream = new XStream();
                    xstream.alias("point_of_interest", PointOfInterest.class);
                    xstream.alias("point_of_interests", PointOfInterestList.class);
                    xstream.addImplicitCollection(PointOfInterestList.class, "point_of_interests");
                    PointOfInterestList point_of_interest_list = (PointOfInterestList)xstream.fromXML(response);
                    for (PointOfInterest point : point_of_interest_list.getPointOfInterests())
                    {
                        LatLng lat_lng = new LatLng(point.getLatitude(), point.getLongitude());
                        map.addMarker(new MarkerOptions().position(lat_lng).title(point.getTitle()));
                    }
                } catch (Exception e) {
                    Log.e("OKK", e.getMessage());
                }
            }
        }, new String[]{"get", "route_id"}, new String[]{"point_of_interest_list", String.valueOf(route_id)});
    }

    public void getDefaultPoints(final GoogleMap map, double lat, double lon){
        String temp_url = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?";
        String api_key = "AIzaSyB7rHAkfvTQ9u5jQLDkP8ASbYxnZcYMIv8";
        
        try {
            URL url = new URL(temp_url + "location=" + lat + "," + lon + "&type=point_of_interest&apiKey=" + api_key);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                Log.e("OKK", stringBuilder.toString());
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("OKK", e.getMessage(), e);
        }
    }
}
