package Client;
import FromTask.*;
import Exception.*;
import Server.Answer;
import java.io.*;
import java.net.SocketException;
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
    Command commandManager;
    Parser parser;
    /**
     * Конструктор, пустой, как вы заметили
     */
    public App() {
        commandManager = new Command();
        IdHandler idHandler = new IdHandler();
        parser = new Parser(commandManager, idHandler);
    }


    public void start(ObjectInputStream in, ObjectOutputStream out) throws IOException, NoSuchElementException,ClassNotFoundException,SocketException, ClassNotFoundException {
        try {
            System.out.println("Hello!");
            try{
                parser.read(System.getenv("Laba6"), in, out);
            }
            catch (NullPointerException e){
                System.out.println("Enter command");
            }
            Scanner console = new Scanner(System.in);
            while (!equalsPart(str, "exit")) {
                str = communicate(in, out, console, true);
            }
        } catch (CollectionException e) {
            System.out.println("CollectionException");
            FileHandling.exit(in, out);
            e.printStackTrace();
        }finally {
            System.out.println("Goodbye");
        }
    }

    public String communicate(ObjectInputStream in, ObjectOutputStream out, Scanner scanner, Boolean fromFile) throws IOException, ClassNotFoundException, CollectionException, SocketException {

        if (!fromFile){
            System.out.println("Please, enter a new command");
        }
        str = scanner.nextLine();
        Request request = getRequest(str, scanner, fromFile);
        if (request == null) {
            FileHandling.exit(in, out);
        } else {
            str = FileHandling.communicate(request, in, out, this, str);
            Answer answer = (Answer) in.readObject();
            if (equals(request.getCommand(), "check id")) {
                System.out.println(answer.getAnswer());
                if (answer.getCheck()) {
                    answer = Command.updateId(in, out, scanner, request.getId());
                    System.out.println(answer.getAnswer());
                }
            } else if (answer.getCheck() != null) {
                System.out.println(answer.getAnswer());
            } else if (answer.getAnswer() != null) {
                System.out.println(answer.getAnswer());
            }

        }
        return str;
    }


    public static Request getRequest(String str, Scanner in, boolean fromConsole){

        Request request = new Request();
        try {
            if (equalsPart(str, "help")) {
                request.setCommand("help");

            } else if (equalsPart(str, "info")) {
                request.setCommand("info");

            } else if (equalsPart(str, "show")) {
                request.setCommand("show");

            } else if (equalsPart(str, "add")) {
                request.setCommand("add");
                HumanBeing human = Command.createHuman(in, fromConsole);
                if (human == null){
                    request.setCommand("script_exception");
                }
                request.setHuman(human);

            } else if (equalsPart(str, "update id")) {
                Integer num = Command.getIdForUpdate(str);
                request.setId(num);
                request.setCommand("check id");

            } else if (equalsPart(str, "remove_by_id")) {
                request.setCommand("remove_by_id");
                request.setId(Command.removeById(str));

            } else if (equalsPart(str, "clear")) {
                request.setCommand("clear");

            } else if (equalsPart(str, "execute_script")) {
                request.setCommand("execute_script");

            } else if (equalsPart(str, "head")) {
                request.setCommand("head");

            } else if (equalsPart(str, "remove_head")) {
                request.setCommand("remove_head");

            } else if (equalsPart(str, "add_if_min")) {
                request.setCommand("add_if_min");
                request.setHuman(Command.createHuman(in, fromConsole));

            } else if (equalsPart(str, "remove_all_by_weapon_type")) {
                request.setCommand("remove_all_by_weapon_type");
                request.setWeapon(Command.removeAllByWeaponType(str));

            } else if (equalsPart(str, "filter_by_mood")) {
                request.setCommand("filter_by_mood");
                request.setMood(Command.filterByMood(str));

            } else if (equalsPart(str, "filter_contains_soundtrack_name")) {
                request.setCommand("filter_contains_soundtrack_name");
                request.setSoundtrackName(Command.filterContainsSoundtrackName(str));

            } else if (equalsPart(str, "exit")) {
                request = null;
            } else if (fromConsole) {
                System.out.println("Invalid command. Enter \"help\" to see valid commands.");
                request.setCommand(str);
            } else {
                request.setCommand(str);
            }
        } catch (NumberFormatException g) {
            System.out.println("Invalid input");
            request.setCommand("exception");
        } catch (NullException n) {
            System.out.println(n.getMessage());
            request.setCommand("exception");
        } catch (TypeException t) {
            System.out.println(t.getMessage());
            request.setCommand("exception");
        } finally {
            return request;
        }
    }
}






