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
public class PointsList {
    private List<Points> points_list;
    
    public PointsList()
    {
        points_list = new ArrayList<Points>();
    }
    
    public PointsList(ArrayList<Points> points_list)
    {
        setPointsList(points_list);
    }
    
    public void add(Points points)
    {
        points_list.add(points);
    }
    
    public List<Points> getPointsList()
    {
        return this.points_list;
    }
    
    public void setPointsList(ArrayList<Points> points_list)
    {
        this.points_list = points_list;
    }
}