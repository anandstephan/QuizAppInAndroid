package com.example.quizapp.helperClasses;

public class AddCategory {

    String categoryname,totalmarks,totalquestion,id;

    public AddCategory(){

    }

    public AddCategory(String id,String categoryname, String totalmarks, String totalquestion) {
        this.categoryname = categoryname;
        this.totalmarks = totalmarks;
        this.totalquestion = totalquestion;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getTotalmarks() {
        return totalmarks;
    }

    public void setTotalmarks(String totalmarks) {
        this.totalmarks = totalmarks;
    }

    public String getTotalquestion() {
        return totalquestion;
    }

    public void setTotalquestion(String totalquestion) {
        this.totalquestion = totalquestion;
    }
}
