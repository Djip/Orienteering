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
    private List<Toughness> toughnessList;
    
    public ToughnessList()
    {
        toughnessList = new ArrayList<Toughness>();
    }
    
    public ToughnessList(ArrayList<Toughness> toughnessList)
    {
        setToughnessList(toughnessList);
    }
    
    public void add(Toughness toughness)
    {
        toughnessList.add(toughness);
    }
    
    public List<Toughness> getToughnessList()
    {
        return this.toughnessList;
    }
    
    public void setToughnessList(ArrayList<Toughness> toughnessList)
    {
        this.toughnessList = toughnessList;
    }
}