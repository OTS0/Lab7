package Server;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import FromTask.*;
import Exception.*;
import Client.*;
import org.postgresql.util.PSQLException;

/**
 * @author Ilyakova Maria собственной персоной (and OTS)
 * @version 42
 * Класс, запускающий обработку команд пользователя и несколько автоматических протоколов работы с коллекцией
 */
public class App extends Object {
    private static List<HumanBeing> collection = new CopyOnWriteArrayList<>();
    private boolean exit = false;
    /** Поле введенная команда (изначально пуста) */
    String str = "";
    /** Поле время создания коллекции = время запуска приложения  */
    static String time;
    Answer answer = null;
    Request request = null;

    public App(){
    }

    /**
     * prints the collection and calls the method addAll
     * @param humanBeings
     */
    public static void setCollection(List<HumanBeing> humanBeings) {
        //try {
            //PostgresQL ql = new PostgresQL();
            //Connection connection = ql.connectWithDataBase();
            //PostgresQL.deleteCollection(connection);
            if (humanBeings != null) {
                collection.addAll(humanBeings);
                System.out.println("\n ВЫВОД ЭЛЕМЕНТОВ КОЛЛЕКЦИИ humanBeings\n");
                System.out.println(Object.toString(humanBeings));
                System.out.println("\n ВЫВОД ЭЛЕМЕНТОВ КОЛЛЕКЦИИ collection\n");
                System.out.println(Object.toString(collection));
            }
        //}catch (SQLException s){
        //    System.out.println("SQLException in setCollection");
        //}
    }

    public boolean getExit() {
        return this.exit;
    }
    public Request getRequest(){
        return this.request;
    }
    public Answer getAnswer(){
        return this.answer;
    }

    /**
     * read customer request
     * @param channel
     * @param in
     * @return
     */
    public Request read(SocketChannel channel, ObjectInputStream in){
            try {
                if (!channel.isOpen()){
                    exit = true;
                    return null;
                } else {
                    try {
                        try {
                            System.out.println("Читаем...");
                            request = (Request) in.readObject();
                            System.out.println("Прочитали");
                        } catch (IOException i){
                            System.out.println("exit!!!");
                            exit = true;
                        }
                        if (request != null) {
                            if (equals(request.getCommand(), "exit")) {
                                channel.close();
                            }
                        }
                        return request;
                    }catch(EOFException | StreamCorruptedException ignored){
                        System.out.println("Read problem");
                        return null;
                    }
                }
            } catch(IOException | ClassNotFoundException i){
                System.out.println("Соединение с пользователем прервано");
                return null;
            }
    }

    /**
     * sends a response from the server to the client
     * @param channel
     * @param out
     * @return
     */
    public Answer write(SocketChannel channel, ObjectOutputStream out){
        try {
            if (answer == null){

            }
            try {
            if (equals(answer.getAnswer(), "Unable to connect to database") && channel.isOpen()){
                out.writeObject(answer);
            }}catch (NullPointerException ignored){}
            if (exit || out == null || !channel.isOpen()){
                exit = true;
                return null;
            } else {
                if (channel.isOpen()) {
                    out.writeObject(answer);
                    return answer;
                }else {
                    return null;
                }

            }

        }
        catch(IOException i){
            System.out.println("Соединение прервано");
            return null;
        }

    }
    /**
     * Метод, создающий коллекцию, сортирующий ее, запускающий цикл обработки команд пользователя
     */
    public void start(SocketChannel channel) throws IdException, NullException, IOException {
        boolean check = false;
        //определяем дату
        Date creation = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM.yyyy hh:mm:ss a");
        time = formatForDateNow.format(creation);
        if  (!looseEquals(request.getCommand(), "exit")){
            answer = commands(collection, request, channel);
        }
    }

    /**
     * Класс, реализующий обработку введенных команд и вызывающий соответствующие методы
     * @param collection - коллекция HumanBeing
     * @throws CollectionException - выбрасывается, если в скрипте ошибки или коллекция пустая
     * @throws IdException - add, executeScript могут выбросить исключение
     * @throws NoSuchElementException
     */
    public static Answer commands(List<HumanBeing> collection, Request request, SocketChannel channel) throws IdException, NoSuchElementException, NullException, IOException {

        String command = request.getCommand();
        System.out.println("Запускаем работу для комманды " + command);
        Answer answer = new Answer(request);
        PostgresQL ql=new PostgresQL();
        Connection connection = ql.connectWithDataBase();
        if (connection == null){
            answer.setAnswer("Unable to connect to database");
            return answer;
        }
        if (Command.equalsPart(command, "help")) {
            answer.setAnswer(Command.help());
        } else if (Command.equalsPart(command, "info")) {
            answer.setAnswer(Command.info(collection, time));
        } else if (Command.equalsPart(command, "show")) {
            answer.setAnswer(Command.show(collection));
        } else if (Command.equalsPart(command, "add")) {
            answer.setCheck(Command.add(collection, request.getHuman(),ql, connection));
            setAnswer(answer, "The object has been added to the collection", "The object has NoT been added to the collection");
        } else if (Command.looseEquals(command, "id update")) {
            answer.setCheck(Command.updateId(collection, request.getHuman(),ql));
            setAnswer(answer, "Item has been updated", "It's not your element, so you can't update one( \nBut thanks for your efforts");
        } else if (Command.equalsPart(command, "check id")) {
            answer.setCheck(Command.existId(collection,request.getId()));
            System.out.println(answer.getCheck());
            setAnswer(answer, "Element with this id was found","Element with this id was not found");
        } else if (Command.equalsPart(command, "id remove")) {
            answer.setCheck(Command.removeById(collection, request.getId(),ql,request.getLogin()));
            System.out.println(answer.getCheck());
            setAnswer(answer, "Item with this ID removed", "It's not your element, so you can't remove one(");
        } else if (Command.equalsPart(command, "clear")) {
            try {
                answer.setCheck(Command.clear(collection, ql, request.getLogin()));
            } catch (SQLException throwable) {
                answer.setCheck(false);
            }
            setAnswer(answer, "Collection cleared", "The collection has not changed. Maybe there aren't your objects");
        } else if (Command.equalsPart(command, "head")) {
            answer.setAnswer(Command.head(collection));
        } else if (Command.equalsPart(command, "remove_head")) {
            try {
                answer.setAnswer(Command.removeHead(collection,ql,request.getLogin()));
            } catch (SQLException t) {
                answer.setAnswer("The first item was not removed");
            }
        } else if (Command.equalsPart(command, "add_if_min")) {
            answer.setCheck(Command.addIfMin(collection, request.getHuman(),ql));
        } else if (Command.equalsPart(command, "remove_all_by_weapon_type")) {
            try {
                System.out.println(request.getWeapon());
                answer.setCheck(Command.removeAllByWeaponType(collection, request.getWeapon(),ql,request.getLogin()));
                if (answer.getCheck()==null){
                    answer.setAnswer("Sorry, you cannot delete objects that were not created by you");
                }
                else if (answer.getCheck()){
                    answer.setAnswer("Items with this weapon type removed");
                }
                else if(!answer.getCheck()){
                    answer.setAnswer("Items with this type of weapon don't found");
                }
                System.out.println(answer.getAnswer());
            } catch (SQLException throwables) {
                answer.setCheck(false);
            }
        } else if (Command.equalsPart(command, "filter_by_mood")) {
            answer.setAnswer(Command.filterByMood(collection, request.getMood()));
        } else if (Command.equalsPart(command, "filter_contains_soundtrack_name")) {
            answer.setAnswer(Command.filterContainsSoundtrackName(collection, request.getSoundtrackName()));
        } else if(Command.equalsPart(command,"registration")){
            try {
                PostgresQL.createAccount(request.getLogin(),request.getPasswd());
                answer.setAnswer("You have successfully registration!");
                answer.setCheckLogin(true);
            } catch(PSQLException login){
                answer.setAnswer("Login \"" + request.getLogin() + "\" is already taken!");
            }catch (SQLException throwable) {
                answer.setAnswer("There isn't connection to the users database");
            }
        }else if(Command.equalsPart(command,"log in")){
            login(request, answer, ql, connection);
        } else if (Command.equalsPart(command, "exit")) {
            System.out.println(command);
            answer.setAnswer("exit");
            answer.setCollect(collection);
        }
        return answer;
    }

    /**
     * Checks the correctness of the password, and changes the answer
     * @param request
     * @param answer
     * @param ql
     * @param connection
     */
    public static void login(Request request, Answer answer, PostgresQL ql, Connection connection){
        try {
            ResultSet users = connection.prepareStatement(String.format("SELECT * FROM users WHERE login = '%s';", request.getLogin())).executeQuery();
            answer.setCheck(false);
            answer.setCheckLogin(false);
            while(users.next()){
                String pas1 = users.getString("password");
                String pas2 = ql.hash(request.getPasswd());
                if (Object.equals(pas1, pas2)) {
                    answer.setCheck(true);
                    answer.setCheckLogin(true);
                    break;
                }
            }
            setAnswer(answer, "The password was entered correctly! You are logged in.", "Password entered incorrectly.");
        } catch(SQLException thr){
            answer.setCheck(false);
            answer.setAnswer("There isn't connection to the users database");
        }
    }

    /**
     * Метод, который изменяет ответ от сервера клиенту
     * @param answer
     * @param good
     * @param bad
     */
    public static void setAnswer(Answer answer, String good, String bad){
        if (answer.getCheck()){
            answer.setAnswer(good);
        } else{
            answer.setAnswer(bad);
        }
    }


}





