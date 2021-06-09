package Exception;

/**
 * @author i'MAXA
 * Класс-ошибка, вызываемый, если значение поля выходят за допустимый диапазон
 */
public class SizeException extends Exception{
    /** информация об ошибке*/
    String message;
    /** Конструктор, принимающий в качестве аргумента максимальное значение поля*/
    public SizeException(int value){
        this.message = "Please enter a value less than  " + value;
    }

    /**
     * @return информацию об ошибке
     */
    public String getMessage(){
        return this.message;
    }
}
