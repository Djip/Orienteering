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
public class CategoryList {
    private List<Category> categories;
    
    public CategoryList()
    {
        categories = new ArrayList<Category>();
    }
    
    public CategoryList(ArrayList<Category> categories)
    {
        setCategories(categories);
    }
    
    public void add(Category category)
    {
        categories.add(category);
    }
    
    public List<Category> getCategories()
    {
        return this.categories;
    }
    
    public void setCategories(ArrayList<Category> categories)
    {
        this.categories = categories;
    }
}