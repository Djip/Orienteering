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
    private List<Toughness> toughnesses;
    
    public ToughnessList()
    {
        toughnesses = new ArrayList<Toughness>();
    }
    
    public ToughnessList(ArrayList<Toughness> toughnesses)
    {
        setToughnesses(toughnesses);
    }
    
    public void add(Toughness toughness)
    {
        toughnesses.add(toughness);
    }
    
    public List<Toughness> getToughnesses()
    {
        return this.toughnesses;
    }
    
    public void setToughnesses(ArrayList<Toughness> toughnesses)
    {
        this.toughnesses = toughnesses;
    }
}