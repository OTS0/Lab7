package Client;
import FromTask.*;
import Exception.*;
import Server.Answer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author Ilyakova Maria собственной персоной
 * @version 42
 * Класс, запускающий обработку команд пользователя и несколько автоматических протоколов работы с коллекцией
 */
public class App extends Object{

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
    public void start(ObjectInputStream in, ObjectOutputStream out) throws ClassNotFoundException, IOException {
        try {
            System.out.println("Hello!");
            Scanner console = new Scanner(System.in);
            while (!equalsPart(str, "exit")) {
                str = console.nextLine();
                Request request = command(str, console, true);
                out.writeObject(request);
                Answer answer = (Answer)in.readObject();
            }
        }
        catch (NoSuchElementException e) {
            System.out.println("Invalid input!");
            System.out.println("It is very bad.");
            System.out.println(":(");
            System.out.println("Goodbye");
            System.exit(0);
        } catch (IOException i) {
            System.out.println(i.getMessage());
        } catch (IdException | CollectionException e) {
            System.out.println("Collection problems\n");
        }
        System.out.println("Goodbye");
    }

    public static Request command(String str, Scanner in, boolean fromConsole) throws IOException, IdException, CollectionException {
        Request request = new Request();
        if (equalsPart(str, "help")) {
            request.setCommand("help");

        } else if (equalsPart(str, "info")) {
            request.setCommand("info");

        } else if (equalsPart(str, "show")) {
            request.setCommand("show");

        } else if (equalsPart(str, "add")) {
            request.setHuman(Command.add(in, fromConsole));
            request.setCommand("add");

        } else if (equalsPart(str, "update id")) {
            Command.updateId(in, str, fromConsole);
            request.setCommand("update id");

        } else if (equalsPart(str, "remove_by_id")) {
            request.setCommand("remove_by_id");
            request.setId(Command.removeById(str));

        } else if (equalsPart(str, "clear")) {
            request.setCommand("clear");

        }  else if (equalsPart(str, "execute_script")) {
            request.setCommand("execute_script");
            Command.executeScript(System.getenv("LABA6"));

        } else if (equalsPart(str, "head")) {
            request.setCommand("head");

        } else if (equalsPart(str, "remove_head")) {
            request.setCommand("remove_head");

        } else if (equalsPart(str, "add_if_min")) {
            request.setCommand("add_if_min");
            request.setHuman(Command.addIfMin(in, fromConsole));

        } else if (equalsPart(str, "remove_all_by_weapon_type")) {
            request.setCommand("remove_all_by_weapon_type");
            request.setWeapon(Command.removeAllByWeaponType(str));

        } else if (equalsPart(str, "filter_by_mood")) {
            request.setCommand("filter_by_mood");
            request.setMood(Command.filterByMood(str));

        } else if (equalsPart(str, "filter_contains_soundtrack_name")) {
            request.setCommand("filter_contains_soundtrack_name");
            request.setSoundtrackName(Command.filterContainsSoundtrackName(str));

        } else if (!equals(str, "") && (!equalsPart(str, "exit")) && (fromConsole)) {
            System.out.println("Invalid command. Enter \"help\" to see valid commands.");
        }
        return request;
    }
}





