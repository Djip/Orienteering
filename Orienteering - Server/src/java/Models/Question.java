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
    private String question;
    
    public Question(int id, int category_id, int toughness_id, int route_id, String question)
    {
        setId(id);
        setCategoryId(category_id);
        setToughnessId(toughness_id);
        setRouteId(route_id);
        setQuestion(question);
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
    
    public int getRouteId()
    {
        return this.route_id;
    }
    
    public void setRouteId(int route_id)
    {
        this.route_id = route_id;
    }
    
    public String getQuestion(){
        return this.question;
    }
    
    public void setQuestion(String question){
       this.question = question; 
    }
}
