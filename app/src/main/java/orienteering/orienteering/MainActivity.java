package orienteering.orienteering;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.thoughtworks.xstream.XStream;

import orienteering.orienteering.Models.User;
import orienteering.orienteering.Models.UserList;

public class MainActivity extends AppCompatActivity implements DeserializeCallback{
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }

        Button start_btn = (Button)findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        HttpManager httpManager = new HttpManager(this);
        httpManager.pulldata(this, new String[]{"get"}, new String[]{"userList"});
    }


    @Override
    public void onSuccess(String response) {
        try {
            XStream xstream = new XStream();
            xstream.alias("user", User.class);
            xstream.alias("users", UserList.class);
            xstream.addImplicitCollection(UserList.class, "users");
            UserList userList = (UserList)xstream.fromXML(response);

            for (User test : userList.getUsers())
            {
                Log.d("OKK", test.getUsername());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
