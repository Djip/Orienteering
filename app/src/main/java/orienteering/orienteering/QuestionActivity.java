package orienteering.orienteering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.thoughtworks.xstream.XStream;

import java.util.Random;

import orienteering.orienteering.Models.Answer;
import orienteering.orienteering.Models.AnswerList;
import orienteering.orienteering.Models.Category;
import orienteering.orienteering.Models.Question;
import orienteering.orienteering.Models.QuestionList;
import orienteering.orienteering.Models.Toughness;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        randomGenerator = new Random();
        http_manager = new HttpManager(this);
        question_text = (TextView)findViewById(R.id.question_textview);
        question_handler = new QuestionHandler(this);
        question_handler.getQuestionList(getQuestionListCallback);
        category = new Category(getIntent().getIntExtra("category_id", 0));
        toughness = new Toughness(getIntent().getIntExtra("toughness_id", 0));


        Button next = (Button)findViewById(R.id.answer_question);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RadioGroup radio_group = (RadioGroup)findViewById(R.id.answer_radiogroup);
                RadioButton checked_radio = (RadioButton)findViewById(radio_group.getCheckedRadioButtonId());
                String answer = checked_radio.getText().toString();
                Answer correct_answer = null;
                for (Answer a : answer_list.getAnswers()){
                    if (a.getCorrect()){
                        correct_answer = a;
                    }
                }
                TextView result = (TextView)findViewById(R.id.answer_result);
                if(correct_answer != null && answer == correct_answer.getAnswer()){
                    result.setText("Korrekt!");
                    //PERSONEN HAR SVARET RIGTIGT!
                } else {
                    result.setText("Forkert!");
                    //PERSONEN HAR SVARET FORKERT!
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
            } catch (Exception e) {
                Log.e("OKK", e.getMessage());
            }
        }
    };
}
