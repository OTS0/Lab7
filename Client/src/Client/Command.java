package Client;
import FromTask.*;
import Exception.*;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import Client.App;
import Server.Answer;

/**
 * @author Ilyakova Maria
 * Абстрактный класс, реализующий базовые команды, которые выполняет консольное приложение
 */
public class Command extends Object {
    /**
     * @value статичное поле id, нужно для присваивания уникального id элементам коллекции
     */
    static int id = 0;
    /**
     * Список id элементов, которые были добавлены в коллекцию из файла, нужен для проверки уникальности id
     */
    static ArrayList<Integer> ID = new ArrayList<>();


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
    public static int findId(LinkedList<HumanBeing> collection, String str) throws NumberFormatException, NullException, IndexNotFoundException {
        int num = 0;
        int index = -1;
        if (equals("", str)) {
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
        } else if (Command.looseEquals(type, "")) {
            throw new NullException("command and mood");
        } else if (!Command.looseEquals(type, "SADNESS") && !Command.looseEquals(type, "SORROW") && !Command.looseEquals(type, "LONGING") && !Command.looseEquals(type, "GLOOM") && !Command.looseEquals(type, "FRENZY")) {
            throw new TypeException("command and Mood");
        }
        return endType;
    }

    /**
     * Получение значения минимальной скорости из всех элементов коллекции
     *
     * @param collect - коллекция элементов класса HumanBeing
     * @return минимальная скорость
     */
    public static int getMin(LinkedList<HumanBeing> collect) {
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
    public static void help() {
        System.out.println("Вам доступны команды:");
        System.out.println("help : вывести справку по доступным командам");
        System.out.println("info : вывести в стандартный поток вывода информацию о коллекции");
        System.out.println("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        System.out.println("add {element} : добавить новый элемент в коллекцию");
        System.out.println("update id {element} : обновить значение элемента коллекции, id которого равен заданному");
        System.out.println("remove_by_id id : удалить элемент из коллекции по его id");
        System.out.println("clear : очистить коллекцию");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла");
        System.out.println("exit : завершить программу (без сохранения в файл)");
        System.out.println("head : вывести первый элемент коллекции");
        System.out.println("remove_head : вывести первый элемент коллекции и удалить его");
        System.out.println("add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        System.out.println("remove_all_by_weapon_type weaponType : удалить из коллекции все элементы, значение поля weaponType которого эквивалентно заданному");
        System.out.println("filter_by_mood mood : вывести элементы, значение поля mood которых равно заданному");
        System.out.println("filter_contains_soundtrack_name soundtrackName : вывести элементы, значение поля soundtrackName которых содержит заданную подстроку");

    }


    public static HumanBeing createHuman(Scanner in, boolean fromConsole) {
        HumanBeing human = new HumanBeing();
        try {
            setAll(human, in, fromConsole);
        } catch (CollectionException c){
            System.out.println("File has mistakes");
            System.out.println(c.getInfo());
            human = null;
        }
        return human;
    }
    /**
     * Базовая команда
     * Сохранить коллекцию в файл
     *
     * @param collection - коллекция элементов класса HumanBeing
     * @param path       - путь к файлу
     */
    public static boolean save(LinkedList<HumanBeing> collection, String path) {
        try {
            if (path == null) {
                return false;
            }
            path = findGoodFile(path, true);
            if (path != null) {
                File file = new File(path);
                PrintWriter writer = new PrintWriter(file);
                writer.write(StringHandler.toString(collection));
                writer.close();
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException f) {
            return false;
        }
    }
    /**
     * Базовая команда
     * Вывод информации о коллекции
     *
     * @param collection - коллекция элементов класса HumanBeing
     * @param time       - время создания коллекции в строковом представлении
     */
    public static void info(LinkedList<HumanBeing> collection, String time) {
        System.out.println(collection.getClass());
        System.out.println("Creation  time: " + time);
        System.out.println("Number of elements: " + collection.size());
    }

    /**
     * Базовая команда
     * Обновления значений полей элемента коллекции с данным id
     * @param in - объект класса Scanner для ввода новых значений полей
     * @param str - строка, введенная пользователем, содержащая команду и id элемента
     * @throws CollectionException - если скрипт вводит недопустимые значения полей или id
     */
    /**
     * public static Integer updateId(Scanner in, String str, Boolean fromConsole) throws CollectionException {
     * Integer id = null;
     * String strNew = trimString(str, "update id");
     * try {
     * if (equals(strNew, "")){
     * throw new NullException("id");
     * }
     * id = Integer.parseInt(strNew);
     * HumanBeing human = createHuman(in, fromConsole);
     * human.setId(Integer.parseInt(strNew));
     * } catch (NumberFormatException e) {
     * System.out.println("Please, enter command with id (integer)");
     * } catch (NullException n) {
     * System.out.println(n.getMessage());
     * }  finally {
     * return id;
     * }
     * }
     */
    public static Integer getIdForUpdate(String str) throws NullException, NumberFormatException {
        Integer id = null;
        String strNew = trimString(str, "update id");
        if (equals(strNew, "")) {
            throw new NullException("id");
        }
        id = Integer.parseInt(strNew);
        return id;
    }

    /**
     * Базовая команда
     * Удаление элемента коллекции с данным id
     *
     * @param str - строка, содержащая команду и id
     */
    public static Integer removeById(String str) throws NullException, NumberFormatException {
        Integer id = null;
        str=str.trim();
        String strNew = trimString(str, "remove_by_id");
        if (equals(strNew, "")) {
            throw new NullException("command and id");
        }
        id = Integer.parseInt(strNew);
        return id;
    }

    /**
     * Базовая команда
     * Удаление всех элементов коллекции с данным значением поля weaponType
     *
     * @param str - строка, содержащая команду и weapoType
     */
    public static WeaponType removeAllByWeaponType(String str) throws NullException, TypeException {
        WeaponType type = null;
        String strType = trimString(str, "remove_all_by_weapon_type");
        if (equals("", str)) {
            throw new NullException("command and weapon type");
        }
        type = getWeapon(strType);
        return type;
    }

    /**
     * Базовая команда
     * Вывод элементов, значение поля mood которых равно заданному
     *
     * @param str - строка, содержащая команду и mood
     */
    public static Mood filterByMood(String str) throws TypeException, NullException {
        Mood type = null;
        String strType = trimString(str, "filter_by_mood");
        if (equals("", str)) {
            throw new NullException("command and mood");
        }
        type = getMood(strType);
        return type;
    }

    /**
     * Базовая команда
     * Вывод элементов коллекции в строковом представлении
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static void show(LinkedList<HumanBeing> collection) {
        if (collection.size() > 0) {
            for (int i = 0; i < collection.size(); i++) {
                System.out.print("\nElement №" + i + ": ");
                System.out.println(toString(collection.get(i)));
            }
        } else {
            System.out.println("Сollection is empty");
        }
    }

    /**
     * Базовая команда
     * Очистка коллекции
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static void clear(LinkedList<HumanBeing> collection) {
        collection.clear();
        System.out.println("Сollection is empty");
    }

    /**
     * Базовая команда
     * Вывод первого элемента коллекции (если пуста, выводится сообщение)
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static void head(LinkedList<HumanBeing> collection) {
        if (collection.size() > 0) {
            System.out.println(toString(collection.get(0)));
        } else {
            System.out.println("Collection is empty");
        }
    }

    /**
     * Базовая команда
     * Удаление первого элемента коллекции (если пуста, выводится сообщение)
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static void removeHead(LinkedList<HumanBeing> collection) {
        if (collection.size() > 0) {
            head(collection);
            collection.remove(0);
            System.out.println("First item deleted");
        } else {
            System.out.println("Collection is empty");
        }
    }


    /**
     * Базовая команда
     * Вывод всех элементов, значение поля soundtrackName которых содержит заданную подстроку
     */
    public static String filterContainsSoundtrackName(String str) throws NullException {
        str = trimString(str, "filter_contains_soundtrack_name");
        if (equals("", str)) {
            throw new NullException("soundtrack name");
        }
        return str;
    }

    public static File executeScript(String path) {
        path = findGoodFile(path, true);
        File file = null;
        if (path != null){
            file = new File(path);
        }
        return file;
    }
    public static String findGoodFile(String path, boolean forRead) {
        Scanner console = new Scanner(System.in);
        Boolean good = false;
        while (!good) {
            path = path.trim();
            if (!equals(path, "") && !equals(path, "/") && !equals(path, "\\")) {
                good = checkGoodFile(path, forRead);
            }
            if (looseEquals(path, "skip")) {
                path = null;
            }
            if (!good) {
                System.out.println("Please write a new file path or \"skip\" to skip this step ");
                path = readNewPath(console);
            }
        }
        return path;
    }

    public static Boolean checkGoodFile(String path, boolean forRead) {
        boolean check = true;
        try {
            if (looseEquals(path, "skip")) {
                return true;
            }
            else if (path == null) {
                System.out.println("File not found");
                throw new FileNotFoundException();
            }
            File file = new File(path);
            if (checkFile(file, forRead)) {
                throw new AccessRightsException();
            }

        } catch (FileNotFoundException | AccessRightsException a) {
            return false;
        }
        return check;
    }


    public static boolean checkFile(File file, Boolean forRead) throws FileNotFoundException{
        boolean error = false;
        if (!file.exists()) {
            System.out.println("File not found");
            throw new FileNotFoundException();
        }
        else if (!file.canRead() && forRead) {
            error = true;
            System.out.println("Access rights exception. Unable to read");
        }
        else if (!file.canWrite() && !forRead){
            error = true;
            System.out.println("Access rights exception. Unable to write");
        }
        return error;
    }
    public static Answer updateId(ObjectInputStream in, ObjectOutputStream out, Scanner console,  Integer id) throws IOException, ClassNotFoundException {
        HumanBeing human = Command.createHuman(console, true);
        human.setId(id);
        Request requestNew = new Request();
        requestNew.setHuman(human);
        requestNew.setId(id);
        requestNew.setCommand("id update");
        out.writeObject(requestNew);
        System.out.println("Проверяем, какая команда у отправителя: " +requestNew.getCommand());
        Answer answerNew = (Answer)in.readObject();
        System.out.println("Мы успешно приняли ответ");
        return answerNew;
    }



    /**
     * Ввод нового пути к файлу, если с предыдущим возникла ошибка
     * @param in - объект Scanner для ввода
     * @return строку, содержащую новый путь
     */
    public static String readNewPath(Scanner in){
        String newPath = "";
        newPath = in.nextLine();
        if (looseEquals(newPath, "")){
            newPath = readNewPath(in);
        }
        return newPath;
    }


    /**
     * Устанавливает все значения объекта, кроме id
     * Этот метод нужен для работы с файлом, поэтому выбрасывается только одно исключение без возможности исправить ошибку на ходу
     * @param human - объект класс HumanBeing, чьи поля определяем
     * @param in - объект Sсanner
     * @param check - по сути всегда false при вызове этого метода, т.к. показывает, что читаем из файла
     * @throws CollectionException - в файле ошибки (выход за диапазон, недопустимый тип, пустая строка)
     */
    public static void setAll(HumanBeing human, Scanner in, Boolean check) throws CollectionException{
        human.setName(in, check);
        human.setCoordx(in, check);
        human.setCoordy(in, check);
        human.setRealHero(in, check);
        human.setHasToothpick(in, check);
        human.setImpactSpeed(in, check);
        human.setSoundtrackName(in, check);
        human.setWeaponType(in, check);
        human.setMood(in, check);
        human.setCarName(in, check);
        human.setCarCool(in, check);
    }
}
