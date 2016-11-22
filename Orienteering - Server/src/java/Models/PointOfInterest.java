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
    private String title;
    private int route_id;
    
    public PointOfInterest(int id, double latitude, double longitude, String title, int route_id)
    {
        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
        setTitle(title);
        setRouteId(route_id);
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
    
    public String getTitle()
    {
        return this.title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public int getRouteId()
    {
        return this.route_id;
    }
    
    public void setRouteId(int route_id)
    {
        this.route_id = route_id;
    }
}
