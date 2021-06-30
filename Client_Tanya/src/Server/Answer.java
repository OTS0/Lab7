package Server;

import Client.Account;
import Client.Request;
import FromTask.HumanBeing;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Answer implements Serializable {
    private  String answer = null;
    private Boolean check = null;
    private Boolean difficult = false;
    private Integer index = null;
    private Boolean checkLogin = false;
    List<HumanBeing> collect = null;
    Request request = null;
    public Answer(){}

    public Answer(Request request){
        this.request = request;
    }
    public void setDifficult(Boolean difficult){
        this.difficult = difficult;
    }
    public String getAnswer() {
        return answer;
    }
    public Boolean getCheck(){
        return check;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }
    public void setCheck(Boolean check){
        this.check = check;
    }
    public Boolean getDifficult(){
        return this.difficult;
    }
    public Integer getIndex(){
        return this.index;
    }
    public void setIndex(Integer index){
        this.index = index;
    }
    public void setCollect(List<HumanBeing> collect){
        this.collect = collect;
    }
    public List<HumanBeing> getCollect(){
        return this.collect;
    }
    public void setCheckLogin(Boolean checkLogin) {
        this.checkLogin = checkLogin;
    }

    public Boolean getCheckLogin() {
        return checkLogin;
    }
}