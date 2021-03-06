package orienteering.orienteering;

import android.app.Activity;

/**
 * Created by Tobias on 06-12-2016.
 */

public class PointsHandler {

    /**
     * Created by Tobias on 04-12-2016.
     */

        private Activity maps_activity;
        private HttpManager httpManager;

        public PointsHandler(Activity maps_activity){
            this.maps_activity = maps_activity;
            httpManager = new HttpManager(maps_activity);
        }

        public void createEmptyPointEntry(DeserializeCallback deserializeCallback, int route_id, int user_id){
            httpManager.pulldata(deserializeCallback, new String[]{"get", "user_id", "route_id"}, new String[]{"empty_point_entry", String.valueOf(user_id), String.valueOf(route_id)});
        }

        public void changeUserPoints(DeserializeCallback deserializeCallback, int route_id, int user_id, int points){
            httpManager.pulldata(deserializeCallback, new String[]{"get", "route_id", "user_id", "points"}, new String[]{"change_user_points", String.valueOf(route_id), String.valueOf(user_id), String.valueOf(points)});
        }

    }

