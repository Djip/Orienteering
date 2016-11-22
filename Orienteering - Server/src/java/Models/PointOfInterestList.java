import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class PointOfInterestList {
    private List<PointOfInterest> point_of_interests;
    
    public PointOfInterestList()
    {
        setPointOfInterests(new ArrayList<PointOfInterest>());
    }
    
    public PointOfInterestList(ArrayList<PointOfInterest> point_of_interests)
    {
        setPointOfInterests(point_of_interests);
    }
    
    public void add(PointOfInterest user)
    {
        point_of_interests.add(user);
    }
    
    public List<PointOfInterest> getPointOfInterests()
    {
        return this.point_of_interests;
    }
    
    public void setPointOfInterests(ArrayList<PointOfInterest> point_of_interests)
    {
        this.point_of_interests = point_of_interests;
    }
}