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
public class MapsPointOfInterestList {
    private List<MapsPointOfInterest> point_of_interests;

    public MapsPointOfInterestList()
    {
        setPointOfInterests(new ArrayList<MapsPointOfInterest>());
    }

    public MapsPointOfInterestList(ArrayList<MapsPointOfInterest> point_of_interests)
    {
        setPointOfInterests(point_of_interests);
    }

    public void add(MapsPointOfInterest map)
    {
        point_of_interests.add(map);
    }

    public List<MapsPointOfInterest> getPointOfInterests()
    {
        return this.point_of_interests;
    }

    public void setPointOfInterests(ArrayList<MapsPointOfInterest> point_of_interests)
    {
        this.point_of_interests = point_of_interests;
    }
}