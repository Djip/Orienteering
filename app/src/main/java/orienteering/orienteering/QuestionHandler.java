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

    public QuestionList getQuestionList(final Toughness toughness, final Category category){
        final QuestionList question_list = new QuestionList();
        HttpManager httpManager = new HttpManager(maps_activity);
        httpManager.pulldata(new DeserializeCallback() {
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
        }, new String[]{"get"}, new String[]{"question_list"});

        for (Question question : question_list.getQuestions())
        {
            Log.e("OKK", question.getQuestion());
            Log.e("OKK", "Test84");
        }
        return question_list;
    }

}
