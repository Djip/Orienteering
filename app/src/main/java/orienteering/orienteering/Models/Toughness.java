package orienteering.orienteering.Models;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class Toughness {
    private int id;
    private String toughness;
    
    public Toughness(int id, String toughness)
    {
        setId(id);
        setToughness(toughness);
    }

    public Toughness(int id){
        setId(id);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getToughness()
    {
        return this.toughness;
    }
    
    public void setToughness(String toughness)
    {
        this.toughness = toughness;
    }
}
