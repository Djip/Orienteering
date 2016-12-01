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
public class Route {
    private int id;
    private String code;
    private int user_id;
    private int category_id;
    private int toughness_id;
    private int gametime;
    private boolean show_default_point_of_interest;

    public Route(int id, String code, int user_id, int category_id, int toughness_id, int gametime, boolean show_default_point_of_interest)
    {
        setId(id);
        setCode(code);
        setUserId(user_id);
        setToughnessId(toughness_id);
        setCategoryId(category_id);
        setGametime(gametime);
        setShowDefaultPointOfInterest(show_default_point_of_interest);
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public int getUserId()
    {
        return this.user_id;
    }

    public void setUserId(int user_id)
    {
        this.user_id = user_id;
    }
    public int getCategoryId()
    {
        return this.category_id;
    }

    public void setCategoryId(int category_id)
    {
        this.category_id = category_id;
    }
    public int getToughnessId()
    {
        return this.toughness_id;
    }

    public void setToughnessId(int toughness_id)
    {
        this.toughness_id = toughness_id;
    }

    public int getGametime()
    {
        return this.gametime;
    }

    public void setGametime(int gametime)
    {
        this.gametime = gametime;
    }

    public boolean getShowDefaultPointOfInterest()
    {
        return this.show_default_point_of_interest;
    }

    public void setShowDefaultPointOfInterest(boolean show_default_point_of_interest)
    {
        this.show_default_point_of_interest = show_default_point_of_interest;
    }
}
