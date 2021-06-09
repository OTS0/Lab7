package Exception;

/**
 * @author i'MAXA
 * Класс-ошибка, вызываемый, если значение, которое мы пытаемся присвоить полю, недопустимого типа
 */
public class TypeException extends Exception{
    /** информация об ошибке*/
    String message;
    /** Конструктор принимает в качестве аргумента тип поля и генерирует информацию для пользователя о том, какой тип необходим*/
    public TypeException(String type){
        this.message = "Please, enter a " + type + " value";
    }

    /**
     * @return информацию об ошибке
     */
    public String getMessage(){
        return this.message;
    }
}
