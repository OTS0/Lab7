package Server;

import Client.Account;
import Client.Request;
import FromTask.HumanBeing;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author OTS and Ilyakova Maria
 * Class Answer
 * Class for working with a response from the server
 *
 */
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

    /**
     * Changes difficult
     * @param difficult
     */
    public void setDifficult(Boolean difficult){
        this.difficult = difficult;
    }

    /**
     * Returns answer (response from server)
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns check(for command)
     * @return check
     */
    public Boolean getCheck(){
        return check;
    }

    /**
     * Change answer
     * @param answer
     */
    public void setAnswer(String answer){
        this.answer = answer;
    }
    /**
     * Change check
     * @param check
     */
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

    /**
     * Change checkLogin(for for log in and command)
     * @param checkLogin
     */
    public void setCheckLogin(Boolean checkLogin) {
        this.checkLogin = checkLogin;
    }

    /**
     * Returns checkLogin
     * @return
     */
    public Boolean getCheckLogin() {
        return checkLogin;
    }
}