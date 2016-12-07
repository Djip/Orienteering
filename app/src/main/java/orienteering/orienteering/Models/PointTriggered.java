package orienteering.orienteering.Models;

import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class PointTriggered {
    private int route_id;
    private int user_id;
    private double latitude;
    private double longitude;
    private Timestamp timestamp;

    public PointTriggered(int user_id, int route_id, double latitude, double longitude, Timestamp timestamp)
    {
        setUserId(user_id);
        setRouteId(route_id);
        setLatitude(latitude);
        setLongitude(longitude);
        setTimestamp(timestamp);
    }

    public int getUserId()
    {
        return this.user_id;
    }

    public void setUserId(int user_id)
    {
        this.user_id = user_id;
    }

    public int getRouteId()
    {
        return this.route_id;
    }

    public void setRouteId(int route_id)
    {
        this.route_id = route_id;
    }
    public double getLatitude()
    {
        return this.latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    public double getLongitude()
    {
        return this.longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public Timestamp getTimestamp()
    {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp)
    {
        this.timestamp = timestamp;
    }
}


