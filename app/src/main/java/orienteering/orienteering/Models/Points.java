package orienteering.orienteering.Models;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class Points {
    private int user_id;
    private int route_id;
    private int points;
    
    public Points(int user_id, int route_id, int points)
    {
        setUserId(user_id);
        setRouteId(route_id);
        setPoints(points);
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
    
    public int getPoints()
    {
        return this.points;
    }
    
    public void setPoints(int points)
    {
        this.points = points;
    }
}
