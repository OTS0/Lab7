/**
 * @author Ilyakova Maria
 * Класс, объект которого описывает местоположение соответствующего объекта HumanBeing
 * HumanBeing делегирует полномочия этому классу
 */
package FromTask;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Integer x; //Максимальное значение поля: 574, Поле не может быть null
    private Float y; //Поле не может быть null

    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }


    public void setCoordx(Integer x) {
        this.x = x;
    }

    public void setCoordy(Float y) {
        this.y = y;
    }

    public int getCoordx() {
        return this.x;
    }

    public float getCoordy() {
        y.toString().replace(",",".");
        return this.y;
    }
}
