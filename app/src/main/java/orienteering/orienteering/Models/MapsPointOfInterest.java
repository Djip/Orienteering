package orienteering.orienteering.Models;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class MapsPointOfInterest {

    private double latitude;
    private double longitude;
    private String title;

    public MapsPointOfInterest(double latitude, double longitude, String title)
    {

        setLatitude(latitude);
        setLongitude(longitude);
        setTitle(title);
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

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
