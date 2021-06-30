package Server;

import FromTask.*;
import Exception.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


/**
 * @author Ilyakova Maria
 * Абстрактный класс, реализующий базовые команды, которые выполняет консольное приложение
 */
public class Command extends Object {



    /**
     * Поиск индекса элемента коллекции по его id
     *
     * @param collection - лист, содержащий объекты класса HumanBeing
     * @param str        - id в строковом представлении
     * @return порядковый номер элемента в коллекции
     * @throws NumberFormatException  - если строка не может быть представлена в виде int
     * @throws NullException          - если строка null или ""
     * @throws IndexNotFoundException - если элемента с таким id нет в коллекции
     */
    public static int findId(List<HumanBeing> collection, String str) throws NumberFormatException, NullException, IndexNotFoundException {
        int num;
        int index = -1;
        if (Object.equals("", str)) {
            throw new NullException("command and ID'");
        }
        num = Integer.parseInt(str);
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId() == num) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new IndexNotFoundException();
        }
        return index;
    }

    /**
     * Check correctness id, if id less 0 so return false
     * @param collection
     * @param id
     * @return
     */
    public static boolean existId(List<HumanBeing> collection, Integer id) {
        int num = IdHandler.findById(collection, id);
        if (num < 0) {
            return false;
        } else {
            return true;
        }
    }



    /**
     * Преобразование строки в WeaponType
     *
     * @param type - исходная строка
     * @return значение WeaponType
     * @throws NullException - если строка пустая, null
     * @throws TypeException - если строка не может быть преобразовани в WeaponType
     */
    public static WeaponType getWeapon(String type) throws NullException, TypeException {
        WeaponType endType = null;
        if (looseEquals(type, "SHOTGUN")) {
            endType = WeaponType.SHOTGUN;
        } else if (looseEquals(type, "RIFLE")) {
            endType = WeaponType.RIFLE;
        } else if (looseEquals(type, "KNIFE")) {
            endType = WeaponType.KNIFE;
        } else if (looseEquals(type, "MACHINE_GUN")) {
            endType = WeaponType.MACHINE_GUN;
        } else if (looseEquals(type, "null")) {
            endType = null;
        } else if (equals(type, "")) {
            throw new NullException("weaponType");
        } else if (!looseEquals(type, "SHOTGUN") && !looseEquals(type, "RIFLE") && !looseEquals(type, "KNIFE") && !looseEquals(type, "MACHINE_GUN")) {
            throw new TypeException("command and WeaponType");
        }
        return endType;
    }

    /**
     * Преобразование строки в Mood
     *
     * @param type - исходная строка
     * @return соответствующий строке Mood
     * @throws NullException - если строка пустая, null
     * @throws TypeException - если строка не может быть преобразовани в Mood
     */
    public static Mood getMood(String type) throws NullException, TypeException {
        Mood endType = null;
        if (Command.looseEquals(type, "SADNESS")) {
            endType = Mood.SADNESS;
        } else if (Command.looseEquals(type, "SORROW")) {
            endType = Mood.SORROW;
        } else if (Command.looseEquals(type, "LONGING")) {
            endType = Mood.LONGING;
        } else if (Command.looseEquals(type, "GLOOM")) {
            endType = Mood.GLOOM;
        } else if (Command.looseEquals(type, "FRENZY")) {
            endType = Mood.FRENZY;
        } else if (Command.looseEquals(type, "null")) {
            endType = null;
        } else if (Command.looseEquals(type, "")) {
            throw new NullException("mood");
        } else if (!Command.looseEquals(type, "SADNESS") && !Command.looseEquals(type, "SORROW") && !Command.looseEquals(type, "LONGING") && !Command.looseEquals(type, "GLOOM") && !Command.looseEquals(type, "FRENZY")) {
            throw new TypeException("Mood");
        }
        return endType;
    }

    /**
     * Получение значения минимальной скорости из всех элементов коллекции
     *
     * @param collect - коллекция элементов класса HumanBeing
     * @return минимальная скорость
     */
    public static int getMin(List<HumanBeing> collect) {
        int speed = 968;
        for (int i = 0; i < collect.size(); i++) {
            if (collect.get(i).getImpactSpeed() < speed) {
                speed = collect.get(i).getImpactSpeed();
            }
        }
        return speed;
    }

    /**
     * Базовая команда
     * Вывод всех доступных пользователю команд
     */
    public static String help() {
        return "Вам доступны команды:"
                + "\nhelp : вывести справку по доступным командам"
                + "\ninfo : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)"
                + "\nshow : вывести в стандартный поток вывода все элементы коллекции в строковом представлении"
                + "\nadd {element} : добавить новый элемент в коллекцию"
                + "\nupdate id {element} : обновить значение элемента коллекции, id которого равен заданному"
                + "\nremove_by_id id : удалить элемент из коллекции по его id"
                + "\nclear : очистить коллекцию"
                + "\nexecute_script file_name : считать и исполнить скрипт из указанного файла."
                + "\nexit : завершить программу (без сохранения в файл)"
                + "\nhead : вывести первый элемент коллекции"
                + "\nremove_head : вывести первый элемент коллекции и удалить его"
                + "\nadd_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции"
                + "\nremove_all_by_weapon_type weaponType : удалить из коллекции все элементы, значение поля weaponType которого эквивалентно заданному"
                + "\nfilter_by_mood mood : вывести элементы, значение поля mood которых равно заданному"
                + "\nfilter_contains_soundtrack_name soundtrackName : вывести элементы, значение поля soundtrackName которых содержит заданную подстроку";
    }

    /**
     * Базовая команда
     * Добавление элемента в коллекцию
     *
     * @param collect - коллекция элементов класса HumanBeing
     * @throws CollectionException - если в коллекции из файла есть ошибки
     * @throws IdException         - если в файле содержатся элементы с одинаковым id
     */

    public static boolean add(List<HumanBeing> collect, HumanBeing human, PostgresQL ql, Connection connection) {
        // добавляем в коллекцию данные по умолчанию
        boolean check = false;
        if (ql.addHumanBeingToBase(human, connection)){
            human.setId(IdHandler.findGoodId(collect));
            collect.add(human);
            check = true;
        }
        return check;
    }

    /**
     * Базовая команда
     * Вывод информации о коллекции
     *
     * @param collection - коллекция элементов класса HumanBeing
     * @param time       - время создания коллекции в строковом представлении
     */
    public static String info(List<HumanBeing> collection, String time) {
        return collection.getClass() + "\nCreation time: " + time + "\nNumber of elements: " + collection.size();
    }

    /**
     * Базовая команда
     * Обновления значений полей элемента коллекции с данным id
     *
     * @param collection - коллекция элементов класса HumanBeing
     * @throws CollectionException - если скрипт вводит недопустимые значения полей или id
     */

    public static boolean updateId(List<HumanBeing> collection, HumanBeing human, PostgresQL ql) {
        boolean check = false;
        try {
            for (HumanBeing humanBeing : collection) {
            if (ql.checkLog(humanBeing, human.getLogin()) && humanBeing.getId() == human.getId()) {
                if (existId(collection, human.getId())) {
                    ql.updateHumanBeing(human);
                    collection.remove(IdHandler.findById(collection, human.getId()));
                    collection.add(human);
                }
                check = true;
            }
        }
        } catch (SQLException throwables) {
            System.out.println("\n\n\n SQLException!!!!!! \n\n\n");
        }
        return check;
    }

    /**
     * Базовая команда
     * Удаление первого элемента коллекции
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static String removeHead(List<HumanBeing> collection, PostgresQL ql,String login) throws SQLException {
        String str = "Collection is empty. Life is harsh. Dont be upset.";
        Optional<HumanBeing> human=collection.stream().findFirst();
        if (collection.size() > 0 ) {
            if (ql.checkLog(human.get(), login)){
                str = head(collection);
                collection.remove(human.get());
                ql.removeHumanBeing(human.get().getId());}
            else {
                str="Sorry, you cannot delete objects that were not created by you";
            }
        }
        return str;
    }

    /**
     * remove element by id from collection in date base and collection, which save in memory
     * @param collection
     * @param id
     * @param ql
     * @param login
     * @return
     */
    public static boolean removeById(List<HumanBeing> collection, Integer id,PostgresQL ql, String login) {
        boolean check = false;
        try {
            for(HumanBeing human:collection){
                Boolean check1 = ql.checkLog(human,login);
                if (check1 && human.getId()==id){
                    check=true;
                } }
            if(check){
                ql.removeHumanBeing(id);
                collection.remove(IdHandler.findById(collection, id));}
        } catch (SQLException s){

        }
        return check;
    }

    /**
     * remove all items that have a weapon type the same as this type
     * @param collection
     * @param type
     * @param ql
     * @param login
     * @return
     * @throws SQLException
     */
    public static Boolean removeAllByWeaponType(List<HumanBeing> collection, WeaponType type, PostgresQL ql,String login) throws SQLException {
        ArrayList<Integer> ID=new ArrayList<>();
        Boolean check=false;
        for (HumanBeing humanBeing : collection) {
            if (humanBeing.getWeaponType() == type ) {
                if (ql.checkLog(humanBeing, login)){
                System.out.println("Нашли элемент с таким WeaponType и логином.");
                ID.add(humanBeing.getId());
                check=true;
            }
            else{check=null;} }
        }
        for (Integer id:ID){
            ql.removeHumanBeing(id);
            collection.remove(IdHandler.findById(collection,id));
        }
        return check;
    }







    /**
     * Базовая команда
     * Вывод элементов, значение поля mood которых равно заданному
     *
     * @param collection - коллекция элементов класса HumanBeing
     */

    public static String filterByMood(List<HumanBeing> collection, Mood type) {
        String message="";
        List<HumanBeing> sortedCollection=collection
                .stream()
                .filter(x -> x.getMood() == type)
                .collect(Collectors.toList());
        for (HumanBeing human: sortedCollection){
            message = message+Object.toString(human);
        }
        if (Object.equals(message, "")) {
            return "Items not found";
        } else {
            return message;
        }
    }

    /**
     * Базовая команда
     * Вывод элементов коллекции в строковом представлении
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static String show(List<HumanBeing> collection) {
        String message = "";
        if (collection.size() > 0) {
            for (HumanBeing human: collection) {
                message = message + "\n" + StringHandler.toString(human);
            }
        } else {
            message = "Сollection is empty";
        }
        return message;
    }

    /**
     * Базовая команда
     * Очистка коллекции
     *
     * @param collection - коллекция элементов класса HumanBeing
     */

    public static boolean clear(List<HumanBeing> collection, PostgresQL ql, String login) throws SQLException {
        boolean check = false;
        ArrayList<Integer> ID = new ArrayList();
        for (HumanBeing human: collection) {
            if (ql.checkLog(human, login)) {
                ID.add(human.getId());
                check = true;
            }
        }
        for (Integer id: ID){
            ql.removeHumanBeing(id);
            collection.remove(IdHandler.findById(collection, id));
        }
        return check;
    }

    /**
     * Базовая команда
     * Вывод первого элемента коллекции (если пуста, выводится сообщение)
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static String head(List<HumanBeing> collection) {
        if (collection.size() > 0) {
            return Object.toString(collection.stream().findFirst().get());
        } else {
            return "Collection is empty";
        }
    }


    /**
     * Базовая команда
     * Добавление нового элемента в коллекцию, если его значение id меньше, чем у наименьшего элемента этой коллекции
     *
     * @param collection - коллекция элементов класса HumanBeing
     * @throws CollectionException - если скрипт содержит ошибку
     */
    public static boolean addIfMin(List<HumanBeing> collection, HumanBeing human,PostgresQL ql) {
        AtomicBoolean check = new AtomicBoolean(false);
        if (human.getImpactSpeed() < getMin(collection)&&ql.checkLog(human, "f")) {
            human.setId(IdHandler.findGoodId(collection));
            collection.stream().filter(x -> x.getImpactSpeed() < getMin(collection)).forEach(x -> {
                if (ql.addHumanBeingToBase(human,PostgresQL.getConnection())){
                    collection.add(x);
                    check.set(true);}
            });
        }
        return check.get();
    }


    /**
     * Базовая команда
     * Вывод всех элементов, значение поля soundtrackName которых содержит заданную подстроку
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static String filterContainsSoundtrackName(List<HumanBeing> collection, String name) {
        String message="";
        for(HumanBeing human: collection)
            if (human.getSoundtrackName().contains(name)){
                message=message+Object.toString(human);}
        if (Object.equals(message, "")) {
            return "Items not found";
        } else if (collection.size() == 0) {
            return "Collection is empty";
        } else {
            return message;
        }
    }


    /**
     * Удаление элемента коллеции по его порядковому номеру
     *
     * @param collection
     * @param i
     */
    public static void deleteHuman(List<HumanBeing> collection, int i) {
        int index = IdHandler.findById(collection, i);
        collection.remove(index);

    }


}
