package Exception;

/**
 * @author i'MAXA
 * Класс-ошибка, вызываемый, если строка пустая, хотя не должна быть таковой
 */
public class NullException extends Exception{
    /** информация об ошибке, какое именно значение не введено */
    String message;
    /** При создании объекта автоматически генерируется инфа об ошибке
     * В качестве аргумента указываем имя поля, значение которого не должно быть пустым
     */
    public NullException(String var){
        this.message = "Please, enter a " + var + " value";
    }
    public NullException(){}
    /**
     * @return информацию об ошибке
     */
    public String getMessage(){
        return this.message;
    }
}
