package com.best.zcdn.bean;

public class QABean {

    private int qaId;
    private String questions;
    private String answers;

    private String answer;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQaId() {
        return qaId;
    }

    public void setQaId(int qaId) {
        this.qaId = qaId;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
