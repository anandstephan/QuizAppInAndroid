package com.example.quizapp.helperClasses;

public class FinalResult {
    String score,notattempt,wrong,right;

    public FinalResult(){

    }
    public FinalResult(String score,String notattempt,String wrong,String right){
        this.score = score;
        this.notattempt = notattempt;
        this.wrong = wrong;
        this.right = right;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public String getScore() {
        return score;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public void setRightanswer(String score, String notattempt) {
        this.score = score;
        this.notattempt = notattempt;
    }


    public String getNotattempt() {
        return notattempt;
    }

    public void setNotattempt(String notattempt) {
        this.notattempt = notattempt;
    }
}
