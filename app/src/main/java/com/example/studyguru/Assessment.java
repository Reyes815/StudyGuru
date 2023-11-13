package com.example.studyguru;

import java.util.ArrayList;

public class Assessment {
    private String question;

    private String answer_key;
    private String answer;
    public Assessment(){

    }

    public Assessment(String question, String answer_key){
        this.question = question;
        this.answer_key = answer_key;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }
    public String getAnswer(){
        return answer;
    }
    public String getQuestion() {
        return question;
    }

    public String getAnswer_key() {
        return answer_key;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "question='" + question + '\'' +
                ", answer_key='" + answer_key + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
