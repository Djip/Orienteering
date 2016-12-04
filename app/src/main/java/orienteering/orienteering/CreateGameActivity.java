package orienteering.orienteering;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;

import orienteering.orienteering.Models.Category;
import orienteering.orienteering.Models.CategoryList;
import orienteering.orienteering.Models.Toughness;
import orienteering.orienteering.Models.ToughnessList;

public class CreateGameActivity extends AppCompatActivity {

    private final Activity activity = this;
    private ArrayAdapter<CharSequence> adapter;
    private SeekBar seek_bar;
    private TextView seek_bar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

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

                Spinner spinner = (Spinner) findViewById(R.id.subject_spinner_create);
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

                Spinner spinner = (Spinner) findViewById(R.id.toughness_spinner_create);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(activity, R.layout.spinner_item, toughness_strings);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerArrayAdapter);
            }
            catch (Exception e)
            {
                Log.e("OKK", e.getMessage());
            }
            /*
            final SeekBar seek_bar = (SeekBar) findViewById(R.id.seek_bar_create);
            final TextView seek_bar_text = (TextView) findViewById(R.id.seek_bar_text_create);
            SeekBar.OnSeekBarChangeListener custom_seeker = new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    try {
                        seek_bar_text.setText(String.valueOf(seek_bar.getProgress() + 1));
                    } catch (Exception e) {
                        Log.e("OKK", e.getMessage());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

            seek_bar.setOnSeekBarChangeListener(custom_seeker);
            */
        }
    };
}
