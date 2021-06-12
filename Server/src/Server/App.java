package Server;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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
    private static LinkedList<HumanBeing> collection= new LinkedList<>();
    private boolean exit = false;
    /** Поле введенная команда (изначально пуста) */
    String str = "";
    /** Поле время создания коллекции = время запуска приложения  */
    static String time;
    Answer answer = null;
    Request request = null;

    public App(){

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


    public Request read(SocketChannel channel, ObjectInputStream in){
        try {
            if (!channel.isOpen()){
                exit = true;
                return null;
            } else {
                try {
                    try {
                        request = (Request) in.readObject();
                    } catch (IOException i){
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
    public Answer write(SocketChannel channel, ObjectOutputStream out){
        try {
            if (out == null || !channel.isOpen()){
                exit = true;
                return null;
            } else {
                System.out.println("Отправляем: " + answer.getAnswer());
                if (channel.isOpen()) {
                    out.writeObject(answer);
                    return answer;
                }else {
                    return null;
                }
            }
        } catch(IOException i){
            i.printStackTrace();
            return null;
        }

    }
    /**
     * Метод, создающий коллекцию, сортирующий ее, запускающий цикл обработки команд пользователя
     */
    public void start() throws IdException, NullException {
        boolean check = false;
        Comparator3000 comparator = new Comparator3000();
        collection.sort(comparator);
        //определяем дату
        Date creation = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E dd.MM.yyyy hh:mm:ss a");
        time = formatForDateNow.format(creation);
        if  (!looseEquals(request.getCommand(), "exit")){
            answer = commands(collection, request);
        }
    }

    /**
     * Класс, реализующий обработку введенных команд и вызывающий соответствующие методы
     * @param collection - коллекция HumanBeing
     * @throws CollectionException - выбрасывается, если в скрипте ошибки или коллекция пустая
     * @throws IdException - add, executeScript могут выбросить исключение
     * @throws NoSuchElementException
     */
    public static Answer commands(LinkedList<HumanBeing> collection, Request request) throws IdException, NoSuchElementException, NullException {
        String command = request.getCommand();
        System.out.println("Запускаем работу для комманды " + command);
        Answer answer = new Answer(request);
        if (Command.equalsPart(command, "help")) {
            answer.setAnswer(Command.help());
        } else if (Command.equalsPart(command, "info")) {
            answer.setAnswer(Command.info(collection, time));
        } else if (Command.equalsPart(command, "show")) {
            answer.setAnswer(Command.show(collection));
        } else if (Command.equalsPart(command, "add")) {
            answer.setCheck(Command.add(collection, request.getHuman()));
            setAnswer(answer, "The object has been added to the collection", "");
        } else if (Command.equalsPart(command, "id update")) {
            System.out.println("UPDATE ID");
            answer.setCheck(Command.updateId(collection, request.getHuman()));
            setAnswer(answer, "Item has been updated", "Item has NO been updated");
        } else if (Command.equalsPart(command, "check id")) {
            System.out.println("CHECK");
            answer.setCheck(Command.existId(collection,request.getId()));
            setAnswer(answer, "Element with this id was found","Element with this id was not found");
        } else if (Command.equalsPart(command, "remove_by_id")) {
            answer.setCheck(Command.removeById(collection, request.getId()));
            setAnswer(answer, "Item with this ID removed", "Element with this id was not found");
        } else if (Command.equalsPart(command, "clear")) {
            answer.setCheck(Command.clear(collection));
            setAnswer(answer,"Collection cleared ", "");
        } else if (Command.equalsPart(command, "head")) {
            answer.setAnswer(Command.head(collection));
        } else if (Command.equalsPart(command, "remove_head")) {
            answer.setAnswer(Command.removeHead(collection));
        } else if (Command.equalsPart(command, "add_if_min")) {
            answer.setCheck(Command.addIfMin(collection, request.getHuman()));
        } else if (Command.equalsPart(command, "remove_all_by_weapon_type")) {
            answer.setCheck(Command.removeAllByWeaponType(collection, request.getWeapon()));
            answer.setCheck(Command.removeAllByWeaponType(collection, request.getWeapon()));
        } else if (Command.equalsPart(command, "filter_by_mood")) {
            answer.setAnswer(Command.filterByMood(collection, request.getMood()));
        } else if (Command.equalsPart(command, "filter_contains_soundtrack_name")) {
            answer.setAnswer(Command.filterContainsSoundtrackName(collection, request.getSoundtrackName()));
        } else if (Command.equalsPart(command, "read")){
            answer.setCheck(Command.read(collection, System.getenv("Read")));
            setAnswer(answer, "Collection loaded.", "Collection didn't load");
        } else if (Command.equalsPart(command, "exit")) {
            System.out.println(command);
            Command.id = 2;
            answer.setCollect(collection);
        }
        return answer;
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





