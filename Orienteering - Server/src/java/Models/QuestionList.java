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
public class QuestionList {
    private List<Question> questions;
    
    public QuestionList()
    {
        questions = new ArrayList<Question>();
    }
    
    public QuestionList(ArrayList<Question> questions)
    {
        setQuestions(questions);
    }
    
    public void add(Question question)
    {
        questions.add(question);
    }
    
    public List<Question> getQuestions()
    {
        return this.questions;
    }
    
    public void setQuestions(ArrayList<Question> questions)
    {
        this.questions = questions;
    }
}