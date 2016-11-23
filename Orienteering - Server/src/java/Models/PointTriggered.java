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
    private int point_of_interest_id;
    private int route_id;
    private Timestamp timestamp;
    
    public PointTriggered(int point_of_interest_id, int route_id, Timestamp timestamp)
    {
        setPointOfInterestId(point_of_interest_id);
        setRouteId(route_id);
        setTimestamp(timestamp);
    }
    
    public int getPointOfInterestId()
    {
        return this.point_of_interest_id;
    }
    
    public void setPointOfInterestId(int point_of_interest_id)
    {
        this.point_of_interest_id = point_of_interest_id;
    }
    
    public int getRouteId()
    {
        return this.route_id;
    }
    
    public void setRouteId(int route_id)
    {
        this.route_id = route_id;
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
