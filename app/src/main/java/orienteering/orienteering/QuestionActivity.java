package orienteering.orienteering;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.text.Text;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.CGLIBMapper;

import java.sql.Timestamp;
import java.util.Random;

import orienteering.orienteering.Models.Answer;
import orienteering.orienteering.Models.AnswerList;
import orienteering.orienteering.Models.Category;
import orienteering.orienteering.Models.PointTriggered;
import orienteering.orienteering.Models.Question;
import orienteering.orienteering.Models.QuestionList;
import orienteering.orienteering.Models.Toughness;
import orienteering.orienteering.Models.User;

public class QuestionActivity extends AppCompatActivity {

    private Random randomGenerator;
    private Category category;
    private Toughness toughness;
    private final AnswerList answer_list = new AnswerList();
    private final QuestionList question_list = new QuestionList();
    private int question_id;
    private QuestionHandler question_handler;
    private TextView question_text;
    HttpManager http_manager;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    boolean got_answer = false;
    private Activity activity;
    Intent intent;
    private Question current_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        activity= this;
        randomGenerator = new Random();
        http_manager = new HttpManager(this);
        intent = getIntent();
        OrienteeringApplication orienteering_application = (OrienteeringApplication)getApplication();
        SharedPreferences shared_preferences = orienteering_application.getSharedPreferences();
        Gson gson = new Gson();
        User user = gson.fromJson(shared_preferences.getString("user", null), User.class);
        final int user_id = user.getId();
        question_text = (TextView)findViewById(R.id.question_textview);
        question_handler = new QuestionHandler(this);
        question_handler.getQuestionList(getQuestionListCallback);
        category = new Category(getIntent().getIntExtra("category_id", 0));
        toughness = new Toughness(getIntent().getIntExtra("toughness_id", 0));
        final PointsHandler points_handler = new PointsHandler(this);

        final DeserializeCallback changeUserPoints = new DeserializeCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    if(response.trim().equals("success")){
                        Log.e("OKK", "Changed points succesfully");
                    } else {
                        Log.e("OKK", "Error while trying to change points");
                    }
                } catch (Exception e) {
                    Log.e("OKK", e.getMessage());
                }
            }
        };

        final Button next = (Button)findViewById(R.id.answer_question);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (got_answer) {
                    PlaceHandler place_handler = new PlaceHandler(activity);
                    RadioGroup radio_group = (RadioGroup) findViewById(R.id.answer_radiogroup);
                    RadioButton checked_radio = (RadioButton) findViewById(radio_group.getCheckedRadioButtonId());
                    String answer = checked_radio.getText().toString();
                    Answer correct_answer = null;
                    Bundle bundle = intent.getBundleExtra("point");
                    LatLng marker_position = bundle.getParcelable("point");
                    PointTriggered point_triggered = new PointTriggered(user_id, intent.getIntExtra("route_id", 0), marker_position.latitude, marker_position.longitude, new Timestamp(System.currentTimeMillis()));
                    for (Answer a : answer_list.getAnswers()) {
                        if (a.getCorrect()) {
                            correct_answer = a;
                        }
                    }

                    if (correct_answer != null && answer == correct_answer.getAnswer()) {
                        Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG).show();
                        Log.e("OKK", intent.getIntExtra("route_id", 0) + " " + user_id + " " + current_question.getPlusPoint());
                        points_handler.changeUserPoints(changeUserPoints, intent.getIntExtra("route_id", 0), user_id, current_question.getPlusPoint());
                        current_question = null;
                        place_handler.setPointTriggered(point_triggered);
                        finish();
                        //PERSONEN HAR SVARET RIGTIGT!
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_LONG).show();
                        int minus_points = -1 * current_question.getMinusPoint();
                        points_handler.changeUserPoints(changeUserPoints, intent.getIntExtra("route_id", 0), user_id, minus_points);
                        current_question = null;
                        place_handler.setPointTriggered(point_triggered);
                        finish();
                        //PERSONEN HAR SVARET FORKERT!
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.choose_answer), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    DeserializeCallback getAnswerListCallback = new DeserializeCallback() {

        @Override
        public void onSuccess(String response) {
            try {
                XStream xstream = new XStream();
                xstream.alias("answer", Answer.class);
                xstream.alias("answers", AnswerList.class);
                xstream.addImplicitCollection(AnswerList.class, "answers");
                AnswerList answer_list_tmp = (AnswerList) xstream.fromXML(response);
                for (Answer answer : answer_list_tmp.getAnswers()) {
                    answer_list.add(answer);
                }

                answer1 = (RadioButton)findViewById(R.id.answer1);
                answer2 = (RadioButton)findViewById(R.id.answer2);
                answer3 = (RadioButton)findViewById(R.id.answer3);
                answer4 = (RadioButton)findViewById(R.id.answer4);

                answer1.setText(answer_list.getAnswers().get(0).getAnswer());
                answer2.setText(answer_list.getAnswers().get(1).getAnswer());
                answer3.setText(answer_list.getAnswers().get(2).getAnswer());
                answer4.setText(answer_list.getAnswers().get(3).getAnswer());

            } catch(Exception e){
                Log.e("OKK", e.getMessage());
            }
        }
    };

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

                int index = randomGenerator.nextInt(question_list.getQuestions().size());
                question_id = question_list.getQuestions().get(index).getId();
                question_handler.getAnswerByQuestionId(getAnswerListCallback, question_id);

                question_text.setText(question_list.getQuestions().get(index).getQuestion());
                current_question = question_list.getQuestions().get(index);
            } catch (Exception e) {
                Log.e("OKK", e.getMessage());
            }
        }
    };

        public void onRadioGroupClick(View view){
        final Button next = (Button)findViewById(R.id.answer_question);
        next.setBackground(getDrawable(R.drawable.rouded_button));
            got_answer = true;
    }
}
