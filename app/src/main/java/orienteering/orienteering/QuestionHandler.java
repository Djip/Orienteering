package orienteering.orienteering;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;

import orienteering.orienteering.Models.Category;
import orienteering.orienteering.Models.PointOfInterestList;
import orienteering.orienteering.Models.Question;
import orienteering.orienteering.Models.QuestionList;
import orienteering.orienteering.Models.Toughness;

/**
 * Created by Tobias on 04-12-2016.
 */

public class QuestionHandler {

    private Activity maps_activity;
    private GoogleMap map;

    public QuestionHandler(Activity maps_activity, GoogleMap map){
        this.maps_activity = maps_activity;
        this.map = map;
    }

    public void getQuestionList(DeserializeCallback deserializeCallback){
        HttpManager httpManager = new HttpManager(maps_activity);
        httpManager.pulldata(deserializeCallback, new String[]{"get"}, new String[]{"question_list"});
    }

}
