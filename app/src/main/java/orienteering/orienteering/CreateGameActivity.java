package orienteering.orienteering;

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

public class CreateGameActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> adapter;
    SeekBar seek_bar;
    TextView seek_bar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        ArrayList<Category> categories = getCategories();
        ArrayList<String> categoryStrings = new ArrayList<String>();
        for (Category category : categories)
        {
            categoryStrings.add(category.getCategory());
        }

        Spinner spinner = (Spinner) findViewById(R.id.subject_spinner_create);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryStrings);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        final SeekBar seek_bar = (SeekBar) findViewById(R.id.seek_bar_create);
        final TextView seek_bar_text = (TextView) findViewById(R.id.seek_bar_text_create);
        final SeekBar.OnSeekBarChangeListener custom_seeker = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    seek_bar_text.setText(seek_bar.getProgress()+1+"");
                } catch (Exception e) {
                    Log.e("MYAPP", "exception", e);
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
    }

    private ArrayList<Category> getCategories()
    {
        final ArrayList<Category> categories = new ArrayList<Category>();
        boolean runPulldata = true;

        Log.d("OKK", String.valueOf(categories.size()));

        while (categories.size() == 0)
        {
            Log.d("OKK", "Erh");
            if (runPulldata)
            {
                runPulldata = false;

                HttpManager httpManager = new HttpManager(this);
                httpManager.pulldata(new DeserializeCallback() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            Log.d("OKK", "WAT");
                            XStream xstream = new XStream();
                            xstream.alias("category", Category.class);
                            xstream.alias("categories", CategoryList.class);
                            xstream.addImplicitCollection(CategoryList.class, "categories");
                            CategoryList categoryList = (CategoryList) xstream.fromXML(response);

                            for (Category category : categoryList.getCategories())
                            {
                                Log.d("OKK", category.getCategory());
                                categories.add(category);
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e("OKK", e.getMessage());
                        }
                    }
                }, new String[]{"get"}, new String[]{"categoryList"});
            }
        }

        Log.d("OKK", "Return" + String.valueOf(categories.size()));

        return categories;
    }
}
