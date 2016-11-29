package orienteering.orienteering.Models;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class Category {
    private int id;
    private String category;
    
    public Category(int id, String category)
    {
        setId(id);
        setCategory(category);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getCategory()
    {
        return this.category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
}
