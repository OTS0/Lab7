package Exception;

/**
 * @author Ilyakova Maria
 * Класс-ошибка, если невозможно найти элемент коллекции с таким значением поля
 */
public class IndexNotFoundException extends Exception {
    /** сообщение об ошибке*/
    String message = "Item not found";

    /** получение сообщения о характере ошибки */
    public String getMessage(){
        return this.message;
    }
}
