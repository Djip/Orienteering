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
public class BattleTriggered {
    private int user_id;
    private double distance;
    private Timestamp timestamp;
    
    public BattleTriggered(int user_id, double distance, Timestamp timestamp)
    {
        setUserId(user_id);
        setDistance(distance);
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
    
    public double getDistance()
    {
        return this.distance;
    }
    
    public void setDistance(double distance)
    {
        this.distance = distance;
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
