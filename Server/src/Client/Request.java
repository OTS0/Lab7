package Client;

import FromTask.*;

import java.io.Serializable;

public class Request implements Serializable {
    String command = null;
    HumanBeing human = null;
    Integer id = null;
    WeaponType weaponType = null;
    Mood mood = null;
    String soundtrackName = null;
    String path = null;
    String login = null;
    String passwd = null;
    public Request() {
    }

    public Request(Integer id, HumanBeing human, String path, String command, WeaponType WeaponType, String SoundTrackName){
        this.id=id;
        this.human=human;
        this.command=command;
        this.weaponType = WeaponType;
        this.soundtrackName=SoundTrackName;
    }
    public void setAccount(Account account) {
        this.login = account.getLogin();
        this.passwd = account.getPasswd();
    }
    public String getLogin(){
        return this.login;
    }
    public String getPasswd(){
        return this.passwd;
    }
    public String getCommand(){
        return this.command;
    }
    public FromTask.HumanBeing getHuman(){
        return this.human;
    }
    public int getId(){
        return this.id;
    }
    public WeaponType getWeapon(){
        return this.weaponType;
    }
    public Mood getMood(){
        return this.mood;
    }
    public String getSoundtrackName(){
        return this.soundtrackName;
    }
    public String getPath(){ return this.path;};


    public void setCommand(String command){
        this.command = command;
    }
    public void setHuman(HumanBeing human){
        this.human = human;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public void setWeapon(WeaponType weapon){
        this.weaponType = weapon;
    }
    public void setMood(Mood mood){
        this.mood = mood;
    }
    public void setSoundtrackName(String soundtrackName){
        this.soundtrackName = soundtrackName;
    }
    public void setPath(String path){this.path = path;}
}
