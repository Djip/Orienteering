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
    private HttpManager httpManager;

    public QuestionHandler(Activity maps_activity){
        this.maps_activity = maps_activity;
        httpManager = new HttpManager(maps_activity);
    }

    public void getQuestionList(DeserializeCallback deserializeCallback){
        httpManager.pulldata(deserializeCallback, new String[]{"get"}, new String[]{"question_list"});
    }

    public void getAnswerByQuestionId(DeserializeCallback deserializeCallback, int question_id){
        httpManager.pulldata(deserializeCallback, new String[]{"get", "question_id"}, new String[]{"answer_list", String.valueOf(question_id)});
    }

}
