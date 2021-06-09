package Exception;

/**
 * @author Ilyakova Maria
 * Класс-ошибка, если коллекция пустая
 */
public class EmptyException extends Exception{
    /** Информация об ошибке */
    String message = "Collection is empty";

    /**
     * @return сообщение об ошибке
     */
    public String getMessage(){
        return this.message;
    }
}
