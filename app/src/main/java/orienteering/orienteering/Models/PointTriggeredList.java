package orienteering.orienteering.Models;

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
public class PointTriggeredList {
    private List<PointTriggered> point_triggered_list;
    
    public PointTriggeredList()
    {
        point_triggered_list = new ArrayList<PointTriggered>();
    }
    
    public PointTriggeredList(ArrayList<PointTriggered> point_triggered_list)
    {
        setPointTriggeredList(point_triggered_list);
    }
    
    public void add(PointTriggered point_triggered)
    {
        point_triggered_list.add(point_triggered);
    }
    
    public List<PointTriggered> getPointTriggeredList()
    {
        return this.point_triggered_list;
    }
    
    public void setPointTriggeredList(ArrayList<PointTriggered> point_triggered_list)
    {
        this.point_triggered_list = point_triggered_list;
    }
}