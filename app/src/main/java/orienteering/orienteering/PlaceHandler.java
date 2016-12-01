package orienteering.orienteering;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thoughtworks.xstream.XStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import orienteering.orienteering.Models.MapsPointOfInterest;
import orienteering.orienteering.Models.MapsPointOfInterestList;
import orienteering.orienteering.Models.PointOfInterest;
import orienteering.orienteering.Models.PointOfInterestList;
import orienteering.orienteering.Models.User;
import orienteering.orienteering.Models.UserList;

public class PlaceHandler extends AsyncTask<Void, Void, MapsPointOfInterestList> {
    private Activity maps_activity;
    private GoogleMap map;
    private double lat;
    private double lon;

    public PlaceHandler(Activity maps_activity, double lat, double lon, GoogleMap map){
        this.maps_activity = maps_activity;
        this.map = map;
        this.lon = lon;
        this.lat = lat;
    }

    public void getRoutePoints(int route_id){
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

    private MapsPointOfInterestList getDefaultPoints(){
        String temp_url = "https://maps.googleapis.com/maps/api/place/nearbysearch/xml?";
        String api_key = "AIzaSyB7rHAkfvTQ9u5jQLDkP8ASbYxnZcYMIv8";
        MapsPointOfInterestList list = new MapsPointOfInterestList();

        try {
            URL url = new URL(temp_url + "location=" + lat + "," + lon + "&radius=10000&type=point_of_interest&key=" + api_key);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                InputStreamReader input_stream_reader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(input_stream_reader);
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                input_stream_reader.close();
                bufferedReader.close();

                final String xml_string = stringBuilder.toString();

                InputStream stream = new ByteArrayInputStream(xml_string.getBytes(StandardCharsets.UTF_8));
                XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser parser = xmlFactoryObject.newPullParser();
                parser.setInput(stream, null);

                double lat = 0;
                double lon = 0;
                String label = "";

                int event = parser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT)  {
                    String name = parser.getName();

                    switch (event){
                        case XmlPullParser.START_TAG:
                            switch (name){
                                case "name":
                                    label = parser.nextText();
                                break;
                                case "lat":
                                    String latitude = parser.nextText();
                                    lat = Double.parseDouble(latitude);
                                    break;
                                case "lng":
                                    String longitude = parser.nextText();
                                    lon = Double.parseDouble(longitude);
                                    break;
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            if(name.equals("result")){
                                MapsPointOfInterest m = new MapsPointOfInterest(lat, lon, label);
                                list.add(m);
                            }
                            break;
                    }
                    event = parser.next();
                }
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("OKK", e.getMessage(), e);
        }
        return list;
    }


    @Override
    protected MapsPointOfInterestList doInBackground(Void... Voids) {
        MapsPointOfInterestList list = getDefaultPoints();
        return list;
    }

    protected void onPostExecute(MapsPointOfInterestList list) {
        for (MapsPointOfInterest point : list.getPointOfInterests()){
            LatLng lat_lng = new LatLng(point.getLatitude(), point.getLongitude());
            map.addMarker(new MarkerOptions().position(lat_lng).title(point.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
    }
}
