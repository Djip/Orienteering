package orienteering.orienteering.Models;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class User {
    private int id;
    private String username;
    
    public User(int id, String username)
    {
        setId(id);
        setUsername(username);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
}
