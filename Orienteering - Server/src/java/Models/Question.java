 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class Question {
    private int id;
    private int category_id;
    private int toughness_id;
    private int route_id;
    private int plus_point;
    private int minus_point;
    private String question;
    
    public Question(int id, int category_id, int toughness_id, String question, int plus_point, int minus_point, int route_id)
    {
        setId(id);
        setCategoryId(category_id);
        setToughnessId(toughness_id);
        setQuestion(question);
        setPlusPoint(plus_point);
        setMinusPoint(minus_point);
        setRouteId(route_id);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
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
    
    public String getQuestion(){
        return this.question;
    }
    
    public void setQuestion(String question)
    {
       this.question = question; 
    }
    
    public int getPlusPoint()
    {
        return this.plus_point;
    }
    
    public void setPlusPoint(int plus_point)
    {
        this.plus_point = plus_point;
    }
    
    public int getMinusPoint()
    {
        return this.minus_point;
    }
    
    public void setMinusPoint(int minus_point)
    {
        this.minus_point = minus_point;
    }
    
    public int getRouteId()
    {
        return this.route_id;
    }
    
    public void setRouteId(int route_id)
    {
        this.route_id = route_id;
    }
}
