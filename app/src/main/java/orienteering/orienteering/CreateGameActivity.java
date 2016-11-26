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

public class CreateGameActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayAdapter<CharSequence> adapter;
    SeekBar seek_bar;
    TextView seek_bar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.subject_array, android.R.layout.simple_spinner_item);

        Spinner spinner = (Spinner) findViewById(R.id.subject_spinner_create);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(CreateGameActivity.this);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
