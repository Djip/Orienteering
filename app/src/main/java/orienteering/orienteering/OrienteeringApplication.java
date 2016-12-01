package orienteering.orienteering;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by jespe on 01-12-2016.
 */

public class OrienteeringApplication extends Application {
    private SharedPreferences shared_preferences;

    @Override
    public void onCreate(){
        super.onCreate();

        setSharedPreferences(getSharedPreferences(getResources().getString(R.string.preference_name), MODE_PRIVATE));
    }

    public SharedPreferences getSharedPreferences()
    {
        return shared_preferences;
    }

    public void setSharedPreferences(SharedPreferences shared_preferences)
    {
        this.shared_preferences = shared_preferences;
    }
}
