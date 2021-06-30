package Exception;

/**
 * @author Ilyakova Maria
 * Класс-ошибка, выбрасывается, если пользователь пытается поместить неккоректные значения в коллекцию
 * Так же выбрасывается если коллекция пустая, а пользователь пытается получиться из нее информацию
 */

public class CollectionException extends Exception{
    /** сообщение о том, что коллекция пустая */
    String message = "Collection is empty";
    /** информация о том, какие именно проблемы возникли при работе с коллекцией */
    String info = "";

    /**
     * Конструктор, присваивающий полю info значение, указанное как аргумент конструктора
     * @param info
     */
    public CollectionException(String info){
        this.info = info;
    }

    /**
     *
     * @return message - информация о том, что коллекция пустая.
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * @return - возвращает информацию о более специфических проблемах, совершенных при работе с коллекцией
     */
    public String getInfo(){
        return "(" + this.info + ")";
    }

}
