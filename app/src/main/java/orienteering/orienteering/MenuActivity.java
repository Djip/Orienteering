package orienteering.orienteering;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.Text;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import orienteering.orienteering.Models.PointOfInterest;
import orienteering.orienteering.Models.PointOfInterestList;
import orienteering.orienteering.Models.Question;
import orienteering.orienteering.Models.QuestionList;
import orienteering.orienteering.Models.Route;
import orienteering.orienteering.Models.RouteList;
import orienteering.orienteering.Models.User;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {
    ArrayAdapter<CharSequence> adapter;
    SeekBar seek_bar;
    TextView seek_bar_text;
    int toughness;
    int category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.subject_array, android.R.layout.simple_spinner_item);



        final LinearLayout create_game_layout = (LinearLayout)findViewById(R.id.create_game_layout);
        final LinearLayout open_world_layout = (LinearLayout)findViewById(R.id.open_world_game_layout);
        final LinearLayout load_game_layout = (LinearLayout)findViewById(R.id.load_game_layout);

        final RadioButton create_game = (RadioButton)findViewById(R.id.create_game_radio);
        create_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_game_layout.setVisibility(View.VISIBLE);
                open_world_layout.setVisibility(View.GONE);
                load_game_layout.setVisibility(View.GONE);
            }
        });

        RadioButton open_world = (RadioButton)findViewById(R.id.open_world_radio);
        open_world.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_game_layout.setVisibility(View.GONE);
                open_world_layout.setVisibility(View.VISIBLE);
                load_game_layout.setVisibility(View.GONE);

                Spinner spinner = (Spinner) findViewById(R.id.subject_spinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(MenuActivity.this);

                seek_bar = (SeekBar) findViewById(R.id.seek_bar);
                final TextView seek_bar_text = (TextView) findViewById(R.id.seek_bar_text);
                final SeekBar.OnSeekBarChangeListener custom_seeker = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        try {
                            //Toast.makeText(MenuActivity.this, seek_bar.getProgress()+"", Toast.LENGTH_SHORT).show();
                            seek_bar_text.setText(seek_bar.getProgress()+1+"");
                            toughness = seek_bar.getProgress()+1;
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
        });

        final RadioButton get_game = (RadioButton)findViewById(R.id.get_game_radio);
        get_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_game_layout.setVisibility(View.GONE);
                open_world_layout.setVisibility(View.GONE);
                load_game_layout.setVisibility(View.VISIBLE);
            }
        });

        final DeserializeCallback createEmptyPointsEntryCallback = new DeserializeCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    if(response.equals("success")){
                        Log.e("OKK", "Was created, or did create succesfully");
                    } else {
                        Log.e("OKK", "Error while trying to create empty entry");
                    }
                } catch (Exception e) {
                    Log.e("OKK", e.getMessage());
                }
            }
        };

        Button next = (Button)findViewById(R.id.get_game);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PointsHandler points_handler = new PointsHandler(MenuActivity.this);
                OrienteeringApplication orienteering_application = (OrienteeringApplication)getApplication();
                SharedPreferences shared_preferences = orienteering_application.getSharedPreferences();
                Gson gson = new Gson();
                User user = gson.fromJson(shared_preferences.getString("user", null), User.class);
                final int user_id = user.getId();

                if(create_game.isChecked()) {
                    Intent intent = new Intent(MenuActivity.this, CreateGameActivity.class);
                    startActivity(intent);
                } else if(get_game.isChecked()){
                    TextView load_game_edit_text = (TextView)findViewById(R.id.load_game_edit_text);
                    final Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                    String game_code = load_game_edit_text.getText().toString();
                    HttpManager httpManager = new HttpManager(MenuActivity.this);
                    httpManager.pulldata(new DeserializeCallback() {
                        @Override
                        public void onSuccess(String response) {
                            XStream xstream = new XStream();
                            xstream.alias("route", Route.class);
                            xstream.alias("routes", RouteList.class);
                            xstream.addImplicitCollection(RouteList.class, "routes");
                            RouteList routes = (RouteList)xstream.fromXML(response);
                            if(routes.getRoutes() != null && routes.getRoutes().size() == 1){
                                intent.putExtra("route_id", routes.getRoutes().get(0).getId());
                                intent.putExtra("show_default_point_of_interest", routes.getRoutes().get(0).getShowDefaultPointOfInterest());
                                startActivity(intent);
                            }

                            points_handler.createEmptyPointEntry(createEmptyPointsEntryCallback, routes.getRoutes().get(0).getId(), user_id);
                        }
                        }, new String[]{"get", "route_code"}, new String[]{"route", String.valueOf(game_code)});

                } else {
                    points_handler.createEmptyPointEntry(createEmptyPointsEntryCallback, 0, user_id);
                    Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                    intent.putExtra("show_default_point_of_interest", true);
                    intent.putExtra("toughness", toughness);
                    intent.putExtra("category", category);
                    startActivity(intent);
                }
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        this.category = position+1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

