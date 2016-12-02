package orienteering.orienteering;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import orienteering.orienteering.Models.User;
import orienteering.orienteering.Models.UserList;

public class MainActivity extends AppCompatActivity{
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }

        final OrienteeringApplication application = (OrienteeringApplication)getApplication();
        final SharedPreferences shared_preferences = application.getSharedPreferences();

        if (shared_preferences.getString("user", null) != null)
        {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        }

        final SharedPreferences.Editor shared_preferences_editor = shared_preferences.edit();
        final Activity activity = this;
        final EditText username = (EditText)findViewById(R.id.username);
        Button start_btn = (Button)findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().matches("")) {
                    Toast.makeText(activity, "Please enter a username", Toast.LENGTH_LONG).show();
                } else {
                    final HttpManager httpManager = new HttpManager(activity);
                    httpManager.pulldata(new DeserializeCallback() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                XStream xstream = new XStream();
                                xstream.alias("user", User.class);
                                xstream.alias("users", UserList.class);
                                xstream.addImplicitCollection(UserList.class, "users");
                                UserList userList = (UserList)xstream.fromXML(response);

                                if (userList != null && userList.getUsers() != null && userList.getUsers().size() == 1){
                                    Toast.makeText(activity, "This username is taken", Toast.LENGTH_LONG).show();
                                } else {
                                    httpManager.pulldata(new DeserializeCallback() {
                                        @Override
                                        public void onSuccess(String response) {
                                            try {
                                                XStream xstream = new XStream();
                                                xstream.alias("user", User.class);
                                                xstream.alias("users", UserList.class);
                                                xstream.addImplicitCollection(UserList.class, "users");
                                                UserList userList = (UserList) xstream.fromXML(response);

                                                if (userList.getUsers() != null && userList.getUsers().size() == 1) {
                                                    User user = userList.getUsers().get(0);
                                                    Gson gson = new Gson();
                                                    shared_preferences_editor.putString("user", gson.toJson(user));
                                                    shared_preferences_editor.commit();

                                                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show();
                                                }
                                            } catch (Exception e) {
                                                Log.e("OKK - Insert user", e.getMessage());
                                            }
                                        }
                                    }, new String[]{"get", "username"}, new String[]{"new_user", username.getText().toString()});
                                }
                            } catch (Exception e) {
                                Log.e("OKK - Get user if exist", e.getMessage());
                            }
                        }
                    }, new String[]{"get", "username"}, new String[]{"check_username", username.getText().toString()});
                }
            }
        });
    }
}
