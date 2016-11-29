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
public class BattleTriggeredList {
    private List<BattleTriggered> battle_triggered_list;
    
    public BattleTriggeredList()
    {
        battle_triggered_list = new ArrayList<BattleTriggered>();
    }
    
    public BattleTriggeredList(ArrayList<BattleTriggered> battle_triggered_list)
    {
        setBattleTriggeredList(battle_triggered_list);
    }
    
    public void add(BattleTriggered battle_triggered)
    {
        battle_triggered_list.add(battle_triggered);
    }
    
    public List<BattleTriggered> getBattleTriggeredList()
    {
        return this.battle_triggered_list;
    }
    
    public void setBattleTriggeredList(ArrayList<BattleTriggered> battle_triggered_list)
    {
        this.battle_triggered_list = battle_triggered_list;
    }
}