/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class PointOfInterest {
    private int id;
    private double latitude;
    private double longitude;
    
    public PointOfInterest(){}

    public PointOfInterest(LatLng lat_lng)
    {
        setLatitude(lat_lng.latitude);
        setLongitude(lat_lng.longitude);
    }
    
    public PointOfInterest(int id, double latitude, double longitude)
    {
        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
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
}
