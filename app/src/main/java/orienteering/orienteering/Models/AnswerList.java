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
public class AnswerList {
    private List<Answer> answers;
    
    public AnswerList()
    {
        answers = new ArrayList<Answer>();
    }
    
    public AnswerList(ArrayList<Answer> answers)
    {
        setAnswers(answers);
    }
    
    public void add(Answer answer)
    {
        answers.add(answer);
    }
    
    public List<Answer> getAnswers()
    {
        return this.answers;
    }
    
    public void setAnswers(ArrayList<Answer> answers)
    {
        this.answers = answers;
    }
}