package com.example.studyguru;

public class Assessment {
    private String question;

    private String answer_key;

    public Assessment(){

    }

    public Assessment(String question, String answer_key){
        this.question = question;
        this.answer_key = answer_key;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer_key() {
        return answer_key;
    }
}
