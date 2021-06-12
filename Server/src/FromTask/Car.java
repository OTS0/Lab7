/**
 * Класс, которому HumanBeing делегирует обязанности
 * Объект этого класс - машинка, принадлежащая объекту HumanBeing
 * @author i'MAXA
 */
package FromTask;

import java.io.Serializable;

public class Car implements Serializable {
    /** Имя машины */
    private String name; //Поле не может быть null
    /** Является ли машина крутой */
    private Boolean cool; //Поле может быть null

    public Car(){}
    public Car(String name, Boolean cool) {
        this.name = name;
        this.cool = cool;
    }

    /**
     * @param name - уже проверенная в классе HumanBeing строка, будущее значение поля name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * @param cool - уже проверенная в классе HumanBeing строка, будущее значение поля name
     */
    public void setCool(Boolean cool){
        this.cool = cool;
    }
    /**
     * @return name - получение имени машины
     * Метод нужен для класса HumanBeing, в котором содержится объект данного класса
     */
    public String getName(){
        return this.name;
    }
    /**
     * @return cool - получаем, крутая ли машина (boolean)
     * Метод нужен для класса HumanBeing, в котором содержится объект данного класса
     */
    public Boolean getCool(){
        return this.cool;
    }





}
