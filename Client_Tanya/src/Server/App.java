package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import FromTask.*;
import Exception.*;
import Client.*;
/**
 * @author Ilyakova Maria собственной персоной
 * @version 42
 * Класс, запускающий обработку команд пользователя и несколько автоматических протоколов работы с коллекцией
 */
public class App extends Object {
    LinkedList<HumanBeing> collection;
    /** Поле введенная команда (изначально пуста) */
    String str = "";
    /** Поле время создания коллекции = время запуска приложения  */
    static String time;

    /**
     * Конструктор, пустой, как вы заметили
     */
    public App() {
    }


    /**
     * @param collection - коллекция объектов HumanBeing
     * @return переменную окружения
     */
    public String readFile(LinkedList<HumanBeing> collection){
        String path;
        path = System.getenv("LABA5json");
        Command.read(collection, path);
        return path;
    }

    /**
     * Метод, создающий коллекцию, сортирующий ее, запускающий цикл обработки команд пользователя
     */
    public void start(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, IdException, CollectionException, NullException, IndexNotFoundException {
        System.out.println("Hello");
        LinkedList<HumanBeing> collect= new LinkedList<>();
        this.collection = collect;
        Comparator3000 comparator = new Comparator3000();
        collection.sort(comparator);
        //определяем дату
        Date creation = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM.yyyy hh:mm:ss a");
        time = formatForDateNow.format(creation);
        Request request = (Request)in.readObject();
        Commands(collection, request);
    }
    /**
     * Класс, реализующий обработку введенных команд и вызывающий соответствующие методы
     * @param collection - коллекция HumanBeing
     * @throws CollectionException - выбрасывается, если в скрипте ошибки или коллекция пустая
     * @throws IdException - add, executeScript могут выбросить исключение
     * @throws NoSuchElementException
     */
    public static Answer Commands(LinkedList<HumanBeing> collection, Request request) throws CollectionException, IdException, NoSuchElementException, NullException, IndexNotFoundException {
        String command = request.getCommand();
        Answer answer = new Answer(request);
        if (Command.equalsPart(command, "help")) {
            answer.setAnswer(Command.help());
        } else if (Command.equalsPart(command, "info")) {
            answer.setAnswer(Command.info(collection, time));
        } else if (Command.equalsPart(command, "show")) {
            answer.setAnswer(Command.show(collection));
        } else if (Command.equalsPart(command, "add")) {
            answer.setCheck(Command.add(collection, request.getHuman()));
        } else if (Command.equalsPart(command, "update id")) {
            answer.setCheck(Command.updateId(collection, request.getHuman()));
        } else if (Command.equalsPart(command, "remove_by_id")) {
            answer.setCheck(Command.removeById(collection, request.getId()));
        } else if (Command.equalsPart(command, "clear")) {
            answer.setCheck(Command.clear(collection));
        } else if (Command.equalsPart(command, "execute_script")) {
            answer.setCheck(Command.executeScript(collection, command));
        } else if (Command.equalsPart(command, "head")) {
            answer.setAnswer(Command.head(collection));
        } else if (Command.equalsPart(command, "remove_head")) {
            answer.setCheck(Command.removeHead(collection));
        } else if (Command.equalsPart(command, "add_if_min")) {
            answer.setCheck(Command.addIfMin(collection, request.getHuman()));
        } else if (Command.equalsPart(command, "remove_all_by_weapon_type")) {
            answer.setCheck(Command.removeAllByWeaponType(collection, request.getWeapon()));
        } else if (Command.equalsPart(command, "filter_by_mood")) {
            answer.setAnswer(Command.filterByMood(collection, request.getMood()));
        } else if (Command.equalsPart(command, "filter_contains_soundtrack_name")) {
            answer.setAnswer(Command.filterContainsSoundtrackName(collection, request.getSoundtrackName()));
        } else if (Command.equalsPart(command, "exit")) {
        }
        return answer;
    }
}





