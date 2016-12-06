package orienteering.orienteering;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;

import orienteering.orienteering.Models.Answer;
import orienteering.orienteering.Models.AnswerList;
import orienteering.orienteering.Models.Category;
import orienteering.orienteering.Models.CategoryList;
import orienteering.orienteering.Models.Question;
import orienteering.orienteering.Models.Route;
import orienteering.orienteering.Models.RouteList;
import orienteering.orienteering.Models.Toughness;
import orienteering.orienteering.Models.ToughnessList;
import orienteering.orienteering.Models.User;
import orienteering.orienteering.Models.UserList;

public class CreateGameActivity extends AppCompatActivity {

    private Activity activity = this;
    private HttpManager httpManager;
    private RelativeLayout create_route_wrapper;
    private RelativeLayout question_wrapper;
    private Route route;
    private Question question;

    private EditText question_text;
    private EditText answer1_text;
    private EditText answer2_text;
    private EditText answer3_text;
    private EditText answer4_text;
    private RadioGroup correct_answer_grp;
    private EditText plus_points;
    private EditText minus_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        httpManager = new HttpManager(this);

        create_route_wrapper = (RelativeLayout)findViewById(R.id.create_route_wrapper);
        question_wrapper = (RelativeLayout)findViewById(R.id.question_wrapper);
        question_text = (EditText)findViewById(R.id.question_text);
        answer1_text = (EditText)findViewById(R.id.answer1_text);
        answer2_text = (EditText)findViewById(R.id.answer2_text);
        answer3_text = (EditText)findViewById(R.id.answer3_text);
        answer4_text = (EditText)findViewById(R.id.answer4_text);
        correct_answer_grp = (RadioGroup)findViewById(R.id.correct_answer_grp);
        plus_points = (EditText)findViewById(R.id.plus_points);
        minus_points = (EditText)findViewById(R.id.minus_points);

        getCategories();
        getToughness();
    }

    private void getCategories()
    {
        HttpManager httpManager = new HttpManager(this);
        httpManager.pulldata(categoryCallback, new String[]{"get"}, new String[]{"category_list"});
    }

    private void getToughness()
    {
        HttpManager httpManager = new HttpManager(this);
        httpManager.pulldata(toughnessCallback, new String[]{"get"}, new String[]{"toughness_list"});
    }

    public void continueToQuestions(View v)
    {
        OrienteeringApplication orienteering_application = (OrienteeringApplication)getApplication();
        SharedPreferences shared_preferences = orienteering_application.getSharedPreferences();
        Gson gson = new Gson();
        User user = gson.fromJson(shared_preferences.getString("user", null), User.class);

        int user_id = user.getId();

        EditText gamecode_text = (EditText)findViewById(R.id.gamecode);
        String gamecode = gamecode_text.getText().toString();

        Spinner subject_spinner = (Spinner)findViewById(R.id.choose_subject);
        int subject = subject_spinner.getSelectedItemPosition() + 1;

        Spinner toughness_spinner = (Spinner)findViewById(R.id.choose_toughness);
        int toughness = toughness_spinner.getSelectedItemPosition() + 1;

        EditText gametime_text = (EditText)findViewById(R.id.gametime);
        int gametime = Integer.valueOf(gametime_text.getText().toString()) * 60;

        RadioGroup default_points = (RadioGroup)findViewById(R.id.default_points_grp);
        int show_default_point_of_interest_index = default_points.indexOfChild(findViewById(default_points.getCheckedRadioButtonId()));
        boolean show_default_point_of_interest = false;
        if (show_default_point_of_interest_index == 0)
        {
            show_default_point_of_interest = true;
        }

        RadioGroup defined_questions = (RadioGroup)findViewById(R.id.defined_questions_grp);
        int show_defined_questions_index = defined_questions.indexOfChild(findViewById(defined_questions.getCheckedRadioButtonId()));
        boolean show_defined_questions = false;
        if (show_defined_questions_index == 0)
        {
            show_defined_questions = true;
        }

        Route route = new Route(gamecode, user_id, subject, toughness, gametime, show_default_point_of_interest, show_defined_questions);
        ArrayList routes = new ArrayList<Route>();
        routes.add(route);
        RouteList route_list = new RouteList(routes);

        try {
            XStream xstream = new XStream();
            xstream.alias("route", Route.class);
            xstream.alias("routes", RouteList.class);
            xstream.addImplicitCollection(RouteList.class, "routes");
            String routes_xml = xstream.toXML(route_list);
            routes_xml = routes_xml.replace("\n", "").replace("\r", "");

            httpManager.pulldata(continueToQuestionsCallback, new String[]{"get", "route"}, new String[]{"create_route", routes_xml});
        } catch (Exception e) {
            Log.e("OKK", e.getMessage());
        }
    }

    public void addQuestion(View v)
    {
        int correct_answer_index;
        if (correct_answer_grp.getCheckedRadioButtonId() != -1)
        {
            correct_answer_index = correct_answer_grp.indexOfChild(findViewById(correct_answer_grp.getCheckedRadioButtonId()));
        }
        else
        {
            correct_answer_index = -1;
        }

        if (question_text.getText().toString().equals("") ||
            answer1_text.getText().toString().equals("") ||
            answer2_text.getText().toString().equals("") ||
            answer3_text.getText().toString().equals("") ||
            answer4_text.getText().toString().equals("") ||
            correct_answer_index == -1 ||
            plus_points.getText().toString().equals("") ||
            minus_points.getText().toString().equals(""))
        {
            Toast.makeText(this, getResources().getString(R.string.add_question_error), Toast.LENGTH_LONG).show();
        }
        else
        {
            Question question = new Question();
            question.setCategoryId(route.getCategoryId());
            question.setToughnessId(route.getToughnessId());
            question.setQuestion(question_text.getText().toString());
            question.setPlusPoint(Integer.valueOf(plus_points.getText().toString()));
            question.setMinusPoint(Integer.valueOf(minus_points.getText().toString()));
            question.setRouteId(route.getId());

            try {
                XStream xstream = new XStream();
                xstream.alias("question", Question.class);
                String question_xml = xstream.toXML(question);
                question_xml = question_xml.replace("\n", "").replace("\r", "");

                httpManager.pulldata(createQuestionCallback, new String[]{"get", "question"}, new String[]{"create_question", question_xml});
            } catch (Exception e) {
                Log.e("OKK", e.getMessage());
            }
        }
    }

    private DeserializeCallback categoryCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            try {
                XStream xstream = new XStream();
                xstream.alias("category", Category.class);
                xstream.alias("categories", CategoryList.class);
                xstream.addImplicitCollection(CategoryList.class, "categories");
                CategoryList category_list = (CategoryList) xstream.fromXML(response);

                ArrayList<String> category_strings = new ArrayList<String>();
                for (Category category : category_list.getCategories())
                {
                    category_strings.add(category.getCategory());
                }

                Spinner spinner = (Spinner) findViewById(R.id.choose_subject);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_item, category_strings);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerArrayAdapter);
            }
            catch (Exception e)
            {
                Log.e("OKK", e.getMessage());
            }
        }
    };

    private DeserializeCallback toughnessCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            try {
                XStream xstream = new XStream();
                xstream.alias("toughness", Toughness.class);
                xstream.alias("toughness_list", ToughnessList.class);
                xstream.addImplicitCollection(ToughnessList.class, "toughness_list");
                ToughnessList toughness_list = (ToughnessList) xstream.fromXML(response);

                ArrayList<String> toughness_strings = new ArrayList<String>();
                for (Toughness toughness : toughness_list.getToughnessList())
                {
                    toughness_strings.add(toughness.getToughness());
                }

                Spinner spinner = (Spinner) findViewById(R.id.choose_toughness);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_item, toughness_strings);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerArrayAdapter);
            }
            catch (Exception e)
            {
                Log.e("OKK", e.getMessage());
            }
        }
    };

    private DeserializeCallback continueToQuestionsCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            try {
                XStream xstream = new XStream();
                xstream.alias("route", Route.class);
                xstream.alias("routes", RouteList.class);
                xstream.addImplicitCollection(RouteList.class, "routes");
                RouteList routeList = (RouteList)xstream.fromXML(response);

                route = routeList.getRoutes().get(0);

                create_route_wrapper.setVisibility(View.GONE);
                question_wrapper.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.e("OKK", e.getMessage());
            }
        }
    };

    private DeserializeCallback createQuestionCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            try {
                XStream xstream = new XStream();
                xstream.alias("question", Question.class);

                question = (Question)xstream.fromXML(response);

                ArrayList<String> answer_strings = new ArrayList<String>();
                answer_strings.add(answer1_text.getText().toString());
                answer_strings.add(answer2_text.getText().toString());
                answer_strings.add(answer3_text.getText().toString());
                answer_strings.add(answer4_text.getText().toString());

                int correct_answer_index;
                RadioGroup correct_answer_grp = (RadioGroup)findViewById(R.id.correct_answer_grp);
                correct_answer_index = correct_answer_grp.indexOfChild(findViewById(correct_answer_grp.getCheckedRadioButtonId()));

                ArrayList<Answer> answers = new ArrayList<Answer>();

                for (int i = 0; i < 4; i++)
                {
                    Answer answer = new Answer();
                    answer.setQuestionId(question.getId());
                    answer.setAnswer(answer_strings.get(i));
                    if(correct_answer_index == i)
                    {
                        answer.setCorrect(true);
                    }
                    else
                    {
                        answer.setCorrect(false);
                    }

                    answers.add(answer);
                }

                AnswerList answer_list = new AnswerList(answers);

                xstream = new XStream();
                xstream.alias("answer", Answer.class);
                xstream.alias("answers", AnswerList.class);
                xstream.addImplicitCollection(AnswerList.class, "answers");
                String answers_xml = xstream.toXML(answer_list);

                httpManager.pulldata(createAnswersCallback, new String[]{"get", "answers"}, new String[]{"create_answers", answers_xml});
            } catch (Exception e) {
                Log.e("OKK", e.getMessage());
            }
        }
    };

    private DeserializeCallback createAnswersCallback = new DeserializeCallback() {
        @Override
        public void onSuccess(String response) {
            if (response.equals("success"))
            {
                question_text.setText("");
                answer1_text.setText("");
                answer2_text.setText("");
                answer3_text.setText("");
                answer4_text.setText("");
                correct_answer_grp.clearCheck();
                plus_points.setText("");
                minus_points.setText("");

                Toast.makeText(activity, "Question is now added", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    };
}