package orienteering.orienteering.Models;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jespe
 */
public class Answer {
    private int id;
    private int question_id;
    private String answer;
    private boolean correct;
    
    public Answer(int id, int question_id, String answer, boolean correct)
    {
        setId(id);
        setQuestionId(question_id);
        setAnswer(answer);
        setCorrect(correct);
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getQuestionId()
    {
        return this.question_id;
    }
    
    public void setQuestionId(int question_id)
    {
        this.question_id = question_id;
    }
    
    public String getAnswer()
    {
        return this.answer;
    }
    
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    
    public boolean getCorrect()
    {
        return this.correct;
    }
    
    public void setCorrect(boolean correct)
    {
        this.correct = correct;
    }
}
