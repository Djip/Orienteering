package orienteering.orienteering;

import android.content.Intent;
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

import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.Text;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {
    ArrayAdapter<CharSequence> adapter;
    SeekBar seek_bar;
    TextView seek_bar_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.subject_array, android.R.layout.simple_spinner_item);



        final LinearLayout create_game_layout = (LinearLayout)findViewById(R.id.create_game_layout);
        final LinearLayout open_world_layout = (LinearLayout)findViewById(R.id.open_world_game_layout);
        final LinearLayout load_game_layout = (LinearLayout)findViewById(R.id.load_game_layout);

        RadioButton create_game = (RadioButton)findViewById(R.id.create_game_radio);
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

                final SeekBar seek_bar = (SeekBar) findViewById(R.id.seek_bar);
                final TextView seek_bar_text = (TextView) findViewById(R.id.seek_bar_text);
                final SeekBar.OnSeekBarChangeListener custom_seeker = new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        try {
                            //Toast.makeText(MenuActivity.this, seek_bar.getProgress()+"", Toast.LENGTH_SHORT).show();
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
        });

        RadioButton get_game = (RadioButton)findViewById(R.id.get_game_radio);
        get_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_game_layout.setVisibility(View.GONE);
                open_world_layout.setVisibility(View.GONE);
                load_game_layout.setVisibility(View.VISIBLE);
            }
        });

        Button next = (Button)findViewById(R.id.get_game);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seek_bar)
        Toast.makeText(MenuActivity.this, "hej", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

