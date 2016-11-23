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
    private int gametime;
    
    public Route(int id, String code, int user_id, int gametime)
    {
        setId(id);
        setCode(code);
        setUserId(user_id);
        setGametime(gametime);
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
    
    public int getGametime()
    {
        return this.gametime;
    }
    
    public void setGametime(int gametime)
    {
        this.gametime = gametime;
    }
}
