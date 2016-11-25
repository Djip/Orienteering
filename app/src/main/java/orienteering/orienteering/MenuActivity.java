package orienteering.orienteering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayAdapter<CharSequence> adapter;
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
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

