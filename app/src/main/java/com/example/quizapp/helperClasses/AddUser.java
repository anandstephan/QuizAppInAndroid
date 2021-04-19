package com.example.quizapp.helperClasses;

public class AddUser {
    String id,fname,lname,pno,clg;

    public AddUser(String id, String fname, String lname, String pno, String clg) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.pno = pno;
        this.clg = clg;
    }

    public AddUser(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getClg() {
        return clg;
    }

    public void setClg(String clg) {
        this.clg = clg;
    }
}
