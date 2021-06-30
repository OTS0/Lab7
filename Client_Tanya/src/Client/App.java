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
    Account account = null;
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

    /**
     * The main work of the client
     * @param in
     * @param out
     * @throws IOException
     * @throws NoSuchElementException
     * @throws ClassNotFoundException
     * @throws SocketException
     * @throws ClassNotFoundException
     */
    public void start(ObjectInputStream in, ObjectOutputStream out) throws IOException, NoSuchElementException,ClassNotFoundException,SocketException, ClassNotFoundException {
        try {
            System.out.println("Hello!");
            Scanner console = new Scanner(System.in);
            Boolean register;
            while(account == null) {
                register = Account.entry(console);
                if (register==null){
                    System.out.println("Sorry.");
                }else {
                    if (!register) {
                        this.account = reqister(console, out, in);
                    } else {
                        this.account = login(console, out, in);
                    }
                }
            }
            System.out.println("Please, enter a new command");
            System.out.println("Please, enter a new command");
            while (!equalsPart(str, "exit")){
                str = communicate(in, out, console, true);
            }
        } catch (CollectionException e) {
            FileHandling.exit(out);
        } finally {
            System.out.println("Goodbye");
        }
    }

    /**
     * Method, for log in, send to Server account (with login and password),
     * if there is such a user and the password matches
     * so create and return account with this password and login
     *
     * else return null
     * @param scanner
     * @param out
     * @param in
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Account login(Scanner scanner,ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        Request request = new Request();
        Account account = Account.login(scanner);
        request.setAccount(account);
        request.setCommand("log in");
        out.writeObject(request);
        Answer answer = (Answer) in.readObject();
        System.out.println(answer.getAnswer());
        if (!answer.getCheckLogin()){
            account=null;
        }
        return account;
    }

    /**
     * realizes log in or registration
     * @param scanner
     * @param out
     * @param in
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Account reqister(Scanner scanner, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException {
        Request request = new Request();
        Account account = Account.register(scanner);
        request.setAccount(account);
        request.setCommand("registration");
        out.writeObject(request);
        Answer answer = (Answer) in.readObject();
        if (!answer.getCheckLogin()){
            account=null;
        }
        return account;
    }

    /**
     * Method for communication Client and Server, get answer from Server
     * @param in
     * @param out
     * @param scanner
     * @param fromConsole
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws CollectionException
     * @throws SocketException
     */
    public String communicate(ObjectInputStream in, ObjectOutputStream out, Scanner scanner, Boolean fromConsole) throws IOException, ClassNotFoundException, CollectionException, SocketException {
        str = scanner.nextLine();
        Request request = getRequest(str, scanner, fromConsole);
        if (request == null){
            FileHandling.exit(out);
        } else {
            if (equals(request.getCommand(), "add")) {
                request.setAccount(account);
            }
            if (!equals(request.getCommand(), "Mistake") && !equals(request.getCommand(), "exception")) {
                str = FileHandling.communicate(request, in, out, this, str);
                if (str != null) {
                    Answer answer = (Answer) in.readObject();
                    if (answer.getAnswer() != null) {
                        if (equals(answer.getAnswer(), "Unable to connect to database")) {
                            System.out.println(answer.getAnswer());
                            return "";
                        }
                    }
                    if (equalsPart(request.getCommand(), "check id")) {
                        System.out.println(answer.getAnswer());
                        if (answer.getCheck()) {
                            System.out.println(request.getCommand());
                            if (request.getCommand().contains("update")) {
                                answer = Command.updateId(in, out, scanner, account, request.getId());
                                System.out.println(answer.getAnswer());
                            } else {
                                answer = Command.removeById(in, out, account, request.getId());
                                System.out.println(answer.getAnswer());

                            }
                            System.out.println("КОНЕЦ");
                        }
                    } else if (answer.getCheck() != null) {
                        System.out.println(answer.getAnswer());
                    } else if (answer.getAnswer() != null) {
                        System.out.println(answer.getAnswer());
                    }
                }
            }

        }
        if (request.getCommand() != null){
            if (equals(request.getCommand(), "execute_script")){
                System.out.println("The script was executed. Please, enter a new command");
            }
            else if (!request.getCommand().equals("exit") && fromConsole && !equals(request.getCommand(), "exception")) {
                System.out.println("Please, enter a new command");
            }
        }
        if (str == null){
            str = "";
        }
        return str;
    }

    /**
     * set and return request
     * (comparison with commands)
     * @param str
     * @param in
     * @param fromConsole
     * @return
     */
    public Request getRequest(String str, Scanner in, boolean fromConsole){
        Request request = new Request();
        request.setAccount(account);
        try {
            if (equalsPart(str, "help")) {
                request.setCommand("help");

            } else if (equalsPart(str, "info")) {
                request.setCommand("info");

            } else if (equalsPart(str, "show")) {
                request.setCommand("show");

            } else if (equalsPart(str, "add")) {
                request.setCommand("add");
                HumanBeing human = Command.createHuman(in, this.account, fromConsole);
                if (human == null){
                    request.setCommand("script_exception");
                }
                request.setHuman(human);

            } else if (equalsPart(str, "update id")) {
                Integer num = Command.getIdForUpdate(str);
                request.setId(num);
                request.setCommand("check id update");

            } else if (equalsPart(str, "remove_by_id")) {
                Integer num = Command.parseId(trimString(str, "remove_by_id"));
                request.setId(num);
                request.setCommand("check id remove");

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
                request.setHuman(Command.createHuman(in, account, fromConsole));

            } else if (equalsPart(str, "remove_all_by_weapon_type")) {
                request.setCommand("remove_all_by_weapon_type");
                request.setWeapon(Command.removeAllByWeaponType(str));

            } else if (equalsPart(str, "filter_by_mood")) {
                request.setCommand("filter_by_mood");
                request.setMood(Command.filterByMood(str));

            } else if (equalsPart(str, "filter_contains_soundtrack_name")) {
                request.setCommand("filter_contains_soundtrack_name");
                request.setSoundtrackName(Command.filterContainsSoundtrackName(str));

            } else if(equalsPart(str, "registration")){
                request.setCommand("registration");
            } else if (equalsPart(str, "exit")) {
                request = null;
            } else if (fromConsole) {
                System.out.println("\"" + str + "\"" + " is invalid command. Enter \"help\" to see valid commands.");
                request.setCommand("Mistake");
            } else {
                request.setCommand("Mistake");
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






