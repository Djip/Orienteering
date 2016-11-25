package orienteering.orienteering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.android.gms.vision.text.Line;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
    }
}
