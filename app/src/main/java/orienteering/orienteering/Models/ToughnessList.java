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
public class ToughnessList {
    private List<Toughness> toughness_list;
    
    public ToughnessList()
    {
        toughness_list = new ArrayList<Toughness>();
    }
    
    public ToughnessList(ArrayList<Toughness> toughnessList)
    {
        setToughnessList(toughnessList);
    }
    
    public void add(Toughness toughness)
    {
        toughness_list.add(toughness);
    }
    
    public List<Toughness> getToughnessList()
    {
        return this.toughness_list;
    }
    
    public void setToughnessList(ArrayList<Toughness> toughness_list)
    {
        this.toughness_list = toughness_list;
    }
}