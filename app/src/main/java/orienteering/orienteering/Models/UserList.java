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
public class UserList {
    private List<User> users = null;
    
    public UserList()
    {
        users = new ArrayList<User>();
    }
    
    public UserList(ArrayList<User> users)
    {
        setUsers(users);
    }
    
    public void add(User user)
    {
        users.add(user);
    }
    
    public List<User> getUsers()
    {
        return this.users;
    }
    
    public void setUsers(ArrayList<User> users)
    {
        this.users = users;
    }
}