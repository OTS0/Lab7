package Client;
import FromTask.*;
import Exception.*;
import Server.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Ilyakova Maria
 * Абстрактный класс, реализующий базовые команды, которые выполняет консольное приложение
 */
public abstract class Command extends Object {
    /** @value статичное поле id, нужно для присваивания уникального id элементам коллекции */
    static int id = 0;
    /** Список id элементов, которые были добавлены в коллекцию из файла, нужен для проверки уникальности id*/
    static ArrayList<Integer> ID = new ArrayList<>();



    /**
     * Поиск индекса элемента коллекции по его id
     * @param collection - лист, содержащий объекты класса HumanBeing
     * @param str - id в строковом представлении
     * @return порядковый номер элемента в коллекции
     * @throws NumberFormatException - если строка не может быть представлена в виде int
     * @throws NullException - если строка null или ""
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
     *
     * @param num - номер, который хотим присвоить id элемента, и значение которого мы проверяем на уникальность, пробегая по списку ID
     * @param check - если true, то проверяется id элемента из файла, то есть его id меняться не будет, но сделается проверка, если false, то ошибки быть не может и мы просто делаем id на 1 больше и снова проверяем
     * @return значения id, которое можно присвоить элементу с сохранением уникальности всех id коллекции
     * @throws IdException - выбрасывается, если id двух элементов из файла совпадают
     */
    public static int setId(int num, boolean check) throws IdException {
        for (int i = 0; i < ID.size(); i++) {
            if (num == ID.get(i) && (check)) {
                throw new IdException();
            } else if (num == ID.get(i) && (!check)) {
                num = num + 1;
                num = setId(num, check);
                break;
            }
        }
        return num;
    }

    /**
     * Преобразование строки в WeaponType
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
            throw new TypeException("WeaponType");
        }
        return endType;
    }

    /**
     * Преобразование строки в Mood
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
            throw new NullException("mood");
        } else if (!Command.looseEquals(type, "SADNESS") && !Command.looseEquals(type, "SORROW") && !Command.looseEquals(type, "LONGING") && !Command.looseEquals(type, "GLOOM") && !Command.looseEquals(type, "FRENZY")) {
            throw new TypeException("Mood");
        }
        return endType;
    }

    /**
     * Получение значения минимальной скорости из всех элементов коллекции
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

    /**
     * Базовая команда
     * Добавление элемента в коллекцию
     * @throws CollectionException - если в коллекции из файла есть ошибки
     * @throws IdException - если в файле содержатся элементы с одинаковым id
     */
    public static HumanBeing add(Scanner in, boolean fromConsole) throws CollectionException, IdException {
        // создаем объекты
        HumanBeing human = new HumanBeing();
        // ввод данных в нужные поля
        setAll(human, in, fromConsole);

        System.out.println("A new object has been added to the collection");
        return human;
    }

    /**
     * Базовая команда
     * Вывод информации о коллекции
     * @param collection - коллекция элементов класса HumanBeing
     * @param time - время создания коллекции в строковом представлении
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
    public static Integer updateId(Scanner in, String str, Boolean fromConsole) throws CollectionException {
        Integer id = null;
        String strNew = trimString(str, "update id");
        try {
            if (equals(strNew, "")){
                throw new NullException("id");
            }
            id = Integer.parseInt(strNew);
            HumanBeing human = new HumanBeing();
            human.setId(Integer.parseInt(strNew));
            setAll(human, in, fromConsole);
        } catch (NumberFormatException e) {
            System.out.println("Please, enter command with id (integer)");
        } catch (NullException n) {
            System.out.println(n.getMessage());
        }  finally {
            return id;
        }
    }

    /**
     * Базовая команда
     * Удаление элемента коллекции с данным id
     * @param str - строка, содержащая команду и id
     */
    public static Integer removeById(String str) {
        Integer id = null;
        String strNew = trimString(str, "remove_by_id");
        try {
            if (equals(strNew, "")){
                throw new NullException();
            }
            id = Integer.parseInt(strNew);

        } catch (NumberFormatException e) {
            System.out.println("Please, enter command with id");
        } catch (NullException n) {
            System.out.println(n.getMessage());
        }
        return id;
    }

    /**
     * Базовая команда
     * Удаление всех элементов коллекции с данным значением поля weaponType
     * @param str - строка, содержащая команду и weapoType
     */
    public static WeaponType removeAllByWeaponType(String str) {
        WeaponType type = null;
        try {
            String strType = trimString(str, "removeAllByWeaponType");
            if (equals("", str)) {
                throw new NullException("weapon type");
            }
            type = getWeapon(strType);
        } catch (NullException | TypeException n) {
            System.out.println(n.getMessage());
        } finally {
            return type;
        }
    }

    /**
     * Базовая команда
     * Вывод элементов, значение поля mood которых равно заданному
     * @param str - строка, содержащая команду и mood
     */
    public static Mood filterByMood(String str) {
        Mood type = null;
        try {
            String strType = trimString(str, "filterByMood");
            if (equals("", str)) {
                throw new NullException("mood");
            }
            type = getMood(strType);

        } catch (NullException | TypeException n) {
            System.out.println(n.getMessage());
        } finally {
            return type;
        }
    }

    /**
     * Базовая команда
     * Вывод элементов коллекции в строковом представлении
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static void show(LinkedList<HumanBeing> collection) {
        if (collection.size() > 0) {
            for (int i = 0; i < collection.size(); i++) {
                System.out.print("Element №" + i + ": ");
                System.out.println(toString(collection.get(i)));
            }
        } else {
            System.out.println("Сollection is empty");
        }
    }
    /**
     * Базовая команда
     * Очистка коллекции
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static void clear(LinkedList<HumanBeing> collection) {
        collection.clear();
        System.out.println("Сollection is empty");
    }
    /**
     * Базовая команда
     * Вывод первого элемента коллекции (если пуста, выводится сообщение)
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
     * Добавление нового элемента в коллекцию, если его значение id меньше, чем у наименьшего элемента этой коллекции
     * @param in - объект Scanner для ввода значений полей
     * @param check - false, если исполняется скрипт и нельзя попросить ввести другое значение id в случае ошибки
     * @throws CollectionException - если скрипт содержит ошибку
     */
    public static HumanBeing addIfMin(Scanner in, Boolean check) throws CollectionException {
        HumanBeing human = new HumanBeing();
        setAll(human, in, check);
        return human;
    }

    /**
     * Базовая команда
     * Вывод всех элементов, значение поля soundtrackName которых содержит заданную подстроку
     */
    public static String filterContainsSoundtrackName (String str) {
        str = trimString(str, "filter_contains_soundtrack_name");
        try {
            if (equals("", str)) {
                throw new NullException("soundtrack name");
            }
        } catch (NullException n) {
            System.out.println(n.getMessage());
        } finally {
            return str;
        }
    }

    /**
     * Базовая команда
     * Выполнить скрипт
     * @param path - строка, содержащая команду и значение поля soundtrackName
     * @throws CollectionException - если в скрипте ошибка
     * @throws IdException
     */
    public static Boolean executeScript(String path) throws CollectionException, IdException{
        boolean check = false;
        try {
            path = trimString(path, "execute_Script");
            if (path == null){
                System.out.println("File not found");
                throw new FileNotFoundException();
            }
            File file = new File(path);
            if (checkFile(file, false)){
                throw new AccessRightsException();
            }
            String str = "";
            Scanner scanner = new Scanner(file);
            while (!equals(str, "exit")&&(scanner.hasNextLine())){
                str = scanner.nextLine();
              //  App.command(str, scanner, false);
            }
            check = true;
            System.out.println("The script was executed.");
            scanner.close();
        }catch (FileNotFoundException | AccessRightsException a){
            System.out.println("Please write a new file path or \"skip\" to skip this step ");
            Scanner in = new Scanner(System.in);
            String newPath = readNewPath(in);
            if (equalsPart(newPath, "skip")){
                System.out.println("Please, enter a new command");
                check = false;
            }else {
                check = executeScript(newPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return check;
        }

    }




    /**
     * Метод для проверки, существует ли файл, и проверки прав доступа
     * @param file - файл, который проверяем
     * @param check - false, если хотим записать в файл, true, если собираемся читать из файла
     * @return true, если файл существует и мы можем с ним работать, false в противном случае
     * @throws FileNotFoundException
     */
    public static boolean checkFile(File file, Boolean check) throws FileNotFoundException{
        boolean error = false;
        if (!file.exists()) {
            System.out.println("File not found");
            throw new FileNotFoundException();
        }
        else if (!file.canRead() && check) {
            System.out.println("read");
            error = true;
            System.out.println("Access rights exception. Unable to read");
        }
        else if (!file.canWrite() && !check){
            error = true;
            System.out.println("Access rights exception. Unable to write");
        }
        return error;
    }

    /**
     * Чтение коллекции из файла
     * @param collection - коллекция элементов класса HumanBeing
     * @param path - путь к файлу
     */
    public static void read(LinkedList<HumanBeing> collection, String path) {
        String str;
        int i = id + 1;
        try {
            if (path == null){
                System.out.println("File not found");
                throw new FileNotFoundException();
            }
            File file = new File(path);
            if (checkFile(file, true)){
                throw new AccessRightsException();
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                if (str.contains("{")) {
                    i = setId(id + 1, false);
                    HumanBeing human = new HumanBeing();
                    human.setId(i);
                    collection.add(human);

                } else if (!equals(str, "")) {
                    str = str.trim();
                    str = str.substring(1);
                    i = setVar(collection, str, i);
                }
            }
            System.out.println("The file was read!");
        } catch (NullException | IndexNotFoundException | IdException | TypeException | SizeException | NumberFormatException e) {
            System.out.println("An error was found in the file.");
            deleteHuman(collection, i);
        } catch (FileNotFoundException | AccessRightsException a){
            System.out.println("Please write a new file path or \"skip\" to skip this step ");
            Scanner in = new Scanner(System.in);
            String newPath = readNewPath(in);
            if (equalsPart(newPath, "skip")){
                System.out.println("Please, enter a new command");
            }else {
                read(collection, newPath);
            }
        }
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
     * Удаление элемента коллеции по его порядковому номеру
     * @param collection
     * @param i
     */
    public static void deleteHuman(LinkedList<HumanBeing> collection, int i) {
        try {
            int index = findId(collection, i + "");
            collection.remove(index);
        } catch (NullException | IndexNotFoundException e) {
            System.out.println("Element not found");
        }
    }

    /**
     * метод для получения значения полея из json файла
     * @param json - строка из файла json
     * @param trim - поле, значение которого содержится в строке
     * @param quotes - есть ли кавычки (то есть значение поля не числовое)
     * @return преобразованную строку, содержащую лишь значение поля
     * @throws NullException - если значение поля равно пустой строке
     */
    public static String trimJson(String json, String trim, Boolean quotes) throws NullException {
        json = trimString(json, trim);
        if (equals(json, "")) {
            throw new NullException("");
        }
        json = json.trim();
        if (looseEquals(json.substring(json.length() - 1), ",")) {
            if (quotes) {
                json = json.substring(1, json.length() - 2);
            } else {
                json = json.substring(0, json.length() - 1);
            }
        } else if (quotes) {
            json = json.substring(1, json.length() - 1);
        }
        json = json.trim();
        json = json.substring(1);
        json = json.trim();
        json = json.substring(1);
        json = json.trim();
        return json;
    }


    //JSON TAMING

    /**
     * Присваивание значения определенному полю элемента коллекции, найденному по id
     * Использую шаблон FactoryMethod
     * @param collection - коллекция, содержащая элементы класса HumanBeing
     * @param str - строка, содержащая значение поля
     * @param i - значение id
     * @param type - название поля, которому хотим присвоить значение
     * @throws NullException - если str является пустой строкой
     * @throws IndexNotFoundException - если не существует элемента коллекции с таким id
     */
    public static void setNamesById(LinkedList<HumanBeing> collection, String str, int i, String type) throws NullException, IndexNotFoundException {
        if (looseEquals(type, "name")) {
           collection.get(findId(collection, i + "")).setName(str);
       }
       else if (looseEquals(type, "soundtrackName")) {
           collection.get(findId(collection, i + "")).setSoundtrackName(str);
       }
       else if (looseEquals(type, "carName")) {
           collection.get(findId(collection, i + "")).setCarName(str);
       }
    }

    /**
     * определяем поля, имеющие числовое значение
     * Использую шаблон FactoryMethod
     * @param collection - коллекция, содержащая элементы класса HumanBeing
     * @param str - строка, содержащая значение поля
     * @param i - значение id
     * @param type - название поля, которому хотим присвоить значение
     * @throws NullException - если str является пустой строкой
     * @throws IndexNotFoundException - если str нельзя преобразовать в число, если не найдем элемент с таким id
     * @throws SizeException - если значение str выходит за рамки допустимых значений поля
     */
    public static void setNumberById(LinkedList<HumanBeing> collection, String str, int i, String type) throws NullException, IndexNotFoundException, SizeException {
        if (looseEquals(type, "x")){
            collection.get(findId(collection, i + "")).setCoordx(Integer.parseInt(str));
        }
        else if (looseEquals(type, "y")) {
            collection.get(findId(collection, i + "")).setCoordy(Float.parseFloat(str));
        }
        else if (looseEquals(type, "impactSpeed")) {
            int impactSpeed = Integer.parseInt(str);
            if (impactSpeed > 967) {
                throw new SizeException(967);
            }
            if (equals(str, "")) {
                throw new NullException("impactSpeed");
            }
            collection.get(findId(collection, i + "")).setImpactSpeed(impactSpeed);
        }
    }
    /**
     * Определяем id элемента
     * Использую шаблон FactoryMethod
     * @param collection - коллекция, содержащая элементы класса HumanBeing
     * @param str - строка, содержащая значение поля
     * @param i - значение id
     * @throws NullException - если str является пустой строкой
     * @throws IndexNotFoundException - если не найдем элемент с таким id
     * @throws IdException - если значение id не является уникальным (т.к. чтение производится из файла)
     * @throws NumberFormatException - если значение str выходит за рамки допустимых значений поля
     */
    public static int setIdById(LinkedList<HumanBeing> collection, String str, int i) throws NullException, IndexNotFoundException, IdException, NumberFormatException {
        int num = Integer.parseInt(str);
        if (num < 0) {
            throw new IdException();
        }
        setId(num, true);
        ID.add(num);
        collection.get(findId(collection, i + "")).setId(num);
        return num;
    }

    /**
     * Определяем поля элемента, имеющие логическое значение
     * @param collection - коллекция, содержащая элементы класса HumanBeing
     * @param str - строка, содержащая значение поля
     * @param i - значение id
     * @param type - имя поля, которому хотим присвоить значение
     * @throws TypeException - если str не может быть преобразована в тип, соответствующий полю
     * @throws NullException - если str пустая
     * @throws IndexNotFoundException - если не найдем элемент с таким id
     */

    public static void setBooleanById(LinkedList<HumanBeing> collection, String str, int i, String type) throws TypeException, NullException, IndexNotFoundException {
        if (looseEquals(str, "true")) {
            if (looseEquals(type, "RealHero")) {
                collection.get(findId(collection, i + "")).setRealHero(true);
            }
            else if (looseEquals(type, "HasToothpick")) {
                collection.get(findId(collection, i + "")).setHasToothpick(true);
            }
            else if (looseEquals(type, "carCool")) {
                collection.get(findId(collection, i + "")).setCarCool(true);
            }
        }
        else if (looseEquals(str, "false")) {
            if (looseEquals(type, "RealHero")) {
                collection.get(findId(collection, i + "")).setRealHero(false);
            }
            else if (looseEquals(type, "HasToothpick")) {
                collection.get(findId(collection, i + "")).setHasToothpick(false);
            }
            else if (looseEquals(type, "carCool")) {
                collection.get(findId(collection, i + "")).setCarCool(false);
            }
        }
        else {
            throw new TypeException("");
        }
    }

    /**
     * Определяем значение поля weaponType
     * @param collection - коллекция, содержащая элементы класса HumanBeing
     * @param str - строка, содержащая значение поля
     * @param i - значение id
     * @throws TypeException - если str не может быть преобразована в тип, соответствующий полю
     * @throws NullException - если str пустая
     * @throws IndexNotFoundException - если не найдем элемент с таким id
     */
    public static void setWeaponTypeById(LinkedList<HumanBeing> collection, String str, int i) throws NullException, TypeException, IndexNotFoundException {
        collection.get(findId(collection, i + "")).setWeaponType(getWeapon(str));
    }

    /**
     * Определяем значение поля mood
     * @param collection - коллекция, содержащая элементы класса HumanBeing
     * @param str - строка, содержащая значение поля
     * @param i - значение id
     * @throws TypeException - если str не может быть преобразована в тип, соответствующий полю
     * @throws NullException - если str пустая
     * @throws IndexNotFoundException - если не найдем элемент с таким id
     */
    public static void setMoodTypeById(LinkedList<HumanBeing> collection, String str, int i) throws NullException, TypeException, IndexNotFoundException {
        collection.get(findId(collection, i + "")).setMood(getMood(str));
    }

    /**
     * Определяем, какому полю нужно присвоить значение (по str) и вызываем один из методов, делающих это
     * @param collection - коллекция, содержащая элементы класса HumanBeing
     * @param str - строка, содержащая значение поля и его имя
     * @param i - значение id (изначально равно -1, т.к. не факт, что id будет прописано в файле первым)
     * @return уникальное значение id элемента
     * @throws TypeException - если str не может быть преобразована в тип, соответствующий полю
     * @throws NullException - если str пустая
    * @throws IndexNotFoundException - если не найдем элемент с таким id
     * @throws IdException - если значение id элемента не уникально
     * @throws TypeException - если значение поля в str не может быть преобразовано в нужный тип (для enum)
     * @throws SizeException - если значение поля в str выходит за рамки допустимого
     * @throws NumberFormatException - если значение поля в str не может быть преобразовано в число (если это нужно)
     */
    public static int setVar(LinkedList<HumanBeing> collection, String str, int i) throws NullException, IndexNotFoundException, IdException, TypeException, SizeException, NumberFormatException {
        if (equalsPart(str, "name")) {
            str = trimJson(str, "name", true);
            setNamesById(collection, str, i, "name");

        } else if (equalsPart(str, "soundtrackName")) {
            str = trimJson(str, "soundtrackName", true);
            setNamesById(collection, str, i, "soundtrackName");

        } else if (equalsPart(str, "carName")) {
            str = trimJson(str, "carName", true);
            setNamesById(collection, str, i, "carName");

        } else if (equalsPart(str, "x")) {
            str = trimJson(str, "x", false);
            setNumberById(collection, str, i, "x");

        } else if (equalsPart(str, "y")) {
            str = trimJson(str, "y", false);
            setNumberById(collection, str, i, "y");

        } else if (equalsPart(str, "impactSpeed")) {
            str = trimJson(str, "impactSpeed", false);
            setNumberById(collection, str, i, "impactSpeed");

        } else if (equalsPart(str, "id")) {
            str = trimJson(str, "id", false);
            i = setIdById(collection, str, i);

        } else if (equalsPart(str, "realHero")) {
            str = trimJson(str, "realHero", false);
            setBooleanById(collection, str, i, "realHero");

        } else if (equalsPart(str, "HasToothpick")) {
            str = trimJson(str, "HasToothpick", false);
            setBooleanById(collection, str, i, "HasToothpick");

        } else if (equalsPart(str, "carCool")) {
            str = trimJson(str, "carCool", false);
            setBooleanById(collection, str, i, "carCool");

        } else if (equalsPart(str, "weaponType")) {
            str = trimJson(str, "weaponType", true);
            setWeaponTypeById(collection, str, i);

        } else if (equalsPart(str, "mood")) {
            str = trimJson(str, "mood", true);
            setMoodTypeById(collection, str, i);
        }
        return i;
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
