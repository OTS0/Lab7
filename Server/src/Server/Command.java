package Server;

import FromTask.*;
import Exception.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Ilyakova Maria
 * Абстрактный класс, реализующий базовые команды, которые выполняет консольное приложение
 */
public abstract class Command extends Object {
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

    public static boolean existId(LinkedList<HumanBeing> collection, Integer id) throws NullException {
        try {
            return findId(collection, id + "") != -1;
        } catch (IndexNotFoundException i) {
            return false;
        }
    }

    /**
     * @param num   - номер, который хотим присвоить id элемента, и значение которого мы проверяем на уникальность, пробегая по списку ID
     * @param check - если true, то проверяется id элемента из файла, то есть его id меняться не будет, но сделается проверка, если false, то ошибки быть не может и мы просто делаем id на 1 больше и снова проверяем
     * @return значения id, которое можно присвоить элементу с сохранением уникальности всех id коллекции
     * @throws IdException - выбрасывается, если id двух элементов из файла совпадают
     */
    public static int findGoodId(int num, boolean check) throws IdException {
        for (Integer integer : ID) {
            if (num == integer && (check)) {
                throw new IdException();
            } else if (num == integer && (!check)) {
                num = num + 1;
                num = findGoodId(num, check);
                break;
            }
        }
        return num;
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
        if (Object.looseEquals(type, "SHOTGUN")) {
            endType = WeaponType.SHOTGUN;
        } else if (Object.looseEquals(type, "RIFLE")) {
            endType = WeaponType.RIFLE;
        } else if (Object.looseEquals(type, "KNIFE")) {
            endType = WeaponType.KNIFE;
        } else if (Object.looseEquals(type, "MACHINE_GUN")) {
            endType = WeaponType.MACHINE_GUN;
        } else if (Object.equals(type, "")) {
            throw new NullException("weaponType");
        } else if (!Object.looseEquals(type, "SHOTGUN") && !Object.looseEquals(type, "RIFLE") && !Object.looseEquals(type, "KNIFE") && !Object.looseEquals(type, "MACHINE_GUN")) {
            throw new TypeException("WeaponType");
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

    public static boolean add(LinkedList<HumanBeing> collect, HumanBeing human) throws IdException {
        // добавляем в коллекцию данные по умолчанию
        boolean check = false;
        id = id + 1;
        human.setId(findGoodId(id, false));
        id = human.getId();
        collect.add(human);
        check = true;
        return check;
    }

    /**
     * Базовая команда
     * Вывод информации о коллекции
     *
     * @param collection - коллекция элементов класса HumanBeing
     * @param time       - время создания коллекции в строковом представлении
     */
    public static String info(LinkedList<HumanBeing> collection, String time) {
        return collection.getClass() + "\nCreation time: " + time + "\nNumber of elements: " + collection.size();
    }

    /**
     * Базовая команда
     * Обновления значений полей элемента коллекции с данным id
     *
     * @param collection - коллекция элементов класса HumanBeing
     * @throws CollectionException - если скрипт вводит недопустимые значения полей или id
     */

    public static boolean updateId(LinkedList<HumanBeing> collection, HumanBeing human) {
        boolean check = false;
        try {
            findId(collection, id + "");
            collection.remove(findId(collection, human.getId() + ""));
            collection.add(human);
            check = true;
        } catch (NullException | IndexNotFoundException ignored) {
        }
        return check;
    }

    /**
     * Базовая команда
     * Удаление элемента коллекции с данным id
     *
     * @param collection - коллекция элементов класса HumanBeing
     */

    public static boolean removeById(LinkedList<HumanBeing> collection, Integer id) {
        boolean check = false;
        try {
            collection.remove(findId(collection, id + ""));
            check = true;
        } catch (NullException | IndexNotFoundException n) {
            System.out.println(n.getMessage());
        } finally {
            return check;
        }
    }


    /**
     * Базовая команда
     * Удаление всех элементов коллекции с данным значением поля weaponType
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static boolean removeAllByWeaponType(LinkedList<HumanBeing> collection, WeaponType type) {
        try {
            collection.stream().filter(x -> x.getWeaponType() == type).forEach(x -> collection.remove(x.getId() - 1));
        } catch (ConcurrentModificationException ignored) {
        }

        if (collection.stream().filter(x -> x.getWeaponType() == type).count() != 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Базовая команда
     * Вывод элементов, значение поля mood которых равно заданному
     *
     * @param collection - коллекция элементов класса HumanBeing
     */

    public static String filterByMood(LinkedList<HumanBeing> collection, Mood type) {
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
    public static String show(LinkedList<HumanBeing> collection) {
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

    public static boolean clear(LinkedList<HumanBeing> collection) {
        boolean check = false;
        collection.clear();
        check = true;
        return check;
    }

    /**
     * Базовая команда
     * Вывод первого элемента коллекции (если пуста, выводится сообщение)
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static String head(LinkedList<HumanBeing> collection) {
        if (collection.size() > 0) {
            return Object.toString(collection.stream().findFirst().get());
        } else {
            return "Collection is empty";
        }
    }

    /**
     * Базовая команда
     * Удаление первого элемента коллекции (если пуста, выводится сообщение)
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static String removeHead(LinkedList<HumanBeing> collection) {
        String str = "Collection is empty. Life is harsh. Dont be upset.";
        Optional<HumanBeing> p;
        if (collection.size() > 0) {
            p=collection.stream().findFirst();
            str = head(collection);
            collection.remove(p.get());
        }
        return str;
    }

    /**
     * Базовая команда
     * Добавление нового элемента в коллекцию, если его значение id меньше, чем у наименьшего элемента этой коллекции
     *
     * @param collection - коллекция элементов класса HumanBeing
     * @throws CollectionException - если скрипт содержит ошибку
     */
    public static boolean addIfMin(LinkedList<HumanBeing> collection, HumanBeing human) {
        if (human.getImpactSpeed() < getMin(collection)) {
            human.setId(id);
            collection.stream().filter(x -> x.getImpactSpeed() < getMin(collection)).forEach(x -> collection.add(x));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Базовая команда
     * Вывод всех элементов, значение поля soundtrackName которых содержит заданную подстроку
     *
     * @param collection - коллекция элементов класса HumanBeing
     */
    public static String filterContainsSoundtrackName(LinkedList<HumanBeing> collection, String name) {
        String message="";
        List<HumanBeing> sortedcollection=collection;
        sortedcollection
                .stream()
                .filter(x -> x.getSoundtrackName().contains(name))
                .collect(Collectors.toList());
        for(HumanBeing human: sortedcollection)
            message=message+Object.toString(human);
        if (Object.equals(message, "")) {
            return "Items not found";
        } else if (collection.size() == 0) {
            return "Collection is empty";
        } else {
            return message;
        }
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
            File file = new File(path);
            PrintWriter writer = new PrintWriter(file);
            writer.write(toString(collection));
            writer.close();
            return true;
        } catch (FileNotFoundException f) {
            return false;
        }
    }

    /**
     * Метод для проверки, существует ли файл, и проверки прав доступа
     *
     * @param file  - файл, который проверяем
     * @param check - false, если хотим записать в файл, true, если собираемся читать из файла
     * @return true, если файл существует и мы можем с ним работать, false в противном случае
     * @throws FileNotFoundException
     */
    public static boolean checkFile(File file, Boolean check) throws FileNotFoundException {
        boolean error = false;
        if (!file.exists()) {
            System.out.println("File not found");
            throw new FileNotFoundException();
        } else if (!file.canRead() && check) {
            System.out.println("read");
            error = true;
            System.out.println("Access rights exception. Unable to read");
        } else if (!file.canWrite() && !check) {
            error = true;
            System.out.println("Access rights exception. Unable to write");
        }
        return error;
    }

    /**
     * Метод дл прочтения файла
     *
     * @param collection
     * @param path
     * @return
     */
    public static boolean read(LinkedList<HumanBeing> collection, String path) {
        String str;
        Boolean check = true;
        int i = id + 1;
        try {
            if (path == null) {
                throw new NullException();
            }
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                if (str.contains("{")) {
                    i = findGoodId(id + 1, false);
                    HumanBeing human = new HumanBeing();
                    human.setId(i);
                    collection.add(human);
                } else if (!Object.equals(str, "")) {
                    str = str.trim();
                    str = str.substring(1);
                    i = Parser.setVar(collection, str, i);
                }
            }
            System.out.println("The file was read!");
        } catch (NullException | IndexNotFoundException | IdException | TypeException | SizeException | NumberFormatException | FileNotFoundException e) {
            System.out.println("An error was found in the file.");
            check = false;
            deleteHuman(collection, i);
        }
        return check;
    }

    /**
     * Удаление элемента коллеции по его порядковому номеру
     *
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
     * Класс Parcer для прочтения файла
     */
    private static class Parser {
        /**
         * метод для получения значения полея из json файла
         *
         * @param json   - строка из файла json
         * @param trim   - поле, значение которого содержится в строке
         * @param quotes - есть ли кавычки (то есть значение поля не числовое)
         * @return преобразованную строку, содержащую лишь значение поля
         * @throws NullException - если значение поля равно пустой строке
         */
        public static String trimJson(String json, String trim, Boolean quotes) throws NullException {
            json = Object.trimString(json, trim);
            if (Object.equals(json, "")) {
                throw new NullException("");
            }
            json = json.trim();
            if (Object.looseEquals(json.substring(json.length() - 1), ",")) {
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
         *
         * @param collection - коллекция, содержащая элементы класса HumanBeing
         * @param str        - строка, содержащая значение поля
         * @param i          - значение id
         * @param type       - название поля, которому хотим присвоить значение
         * @throws NullException          - если str является пустой строкой
         * @throws IndexNotFoundException - если не существует элемента коллекции с таким id
         */
        public static void setNamesById(LinkedList<HumanBeing> collection, String str, int i, String type) throws NullException, IndexNotFoundException {
            if (Object.looseEquals(type, "name")) {
                collection.get(findId(collection, i + "")).setName(str);
            } else if (Object.looseEquals(type, "soundtrackName")) {
                collection.get(findId(collection, i + "")).setSoundtrackName(str);
            } else if (Object.looseEquals(type, "carName")) {
                collection.get(findId(collection, i + "")).setCarName(str);
            }
        }

        /**
         * определяем поля, имеющие числовое значение
         * Использую шаблон FactoryMethod
         *
         * @param collection - коллекция, содержащая элементы класса HumanBeing
         * @param str        - строка, содержащая значение поля
         * @param i          - значение id
         * @param type       - название поля, которому хотим присвоить значение
         * @throws NullException          - если str является пустой строкой
         * @throws IndexNotFoundException - если str нельзя преобразовать в число, если не найдем элемент с таким id
         * @throws SizeException          - если значение str выходит за рамки допустимых значений поля
         */
        public static void setNumberById(LinkedList<HumanBeing> collection, String str, int i, String type) throws NullException, IndexNotFoundException, SizeException {
            if (Object.looseEquals(type, "x")) {
                collection.get(findId(collection, i + "")).setCoordx(Integer.parseInt(str));
            } else if (Object.looseEquals(type, "y")) {
                collection.get(findId(collection, i + "")).setCoordy(Float.parseFloat(str));
            } else if (Object.looseEquals(type, "impactSpeed")) {
                int impactSpeed = Integer.parseInt(str);
                if (impactSpeed > 967) {
                    throw new SizeException(967);
                }
                if (Object.equals(str, "")) {
                    throw new NullException("impactSpeed");
                }
                collection.get(findId(collection, i + "")).setImpactSpeed(impactSpeed);
            }
        }

        /**
         * Определяем id элемента
         * Использую шаблон FactoryMethod
         *
         * @param collection - коллекция, содержащая элементы класса HumanBeing
         * @param str        - строка, содержащая значение поля
         * @param i          - значение id
         * @throws NullException          - если str является пустой строкой
         * @throws IndexNotFoundException - если не найдем элемент с таким id
         * @throws IdException            - если значение id не является уникальным (т.к. чтение производится из файла)
         * @throws NumberFormatException  - если значение str выходит за рамки допустимых значений поля
         */
        public static int setIdById(LinkedList<HumanBeing> collection, String str, int i) throws NullException, IndexNotFoundException, IdException, NumberFormatException {
            int num = Integer.parseInt(str);
            if (num < 0) {
                throw new IdException();
            }
            num = findGoodId(num, true);
//            ID.add(num);
            collection.get(findId(collection, i + "")).setId(num);
            return num;
        }

        /**
         * Определяем поля элемента, имеющие логическое значение
         *
         * @param collection - коллекция, содержащая элементы класса HumanBeing
         * @param str        - строка, содержащая значение поля
         * @param i          - значение id
         * @param type       - имя поля, которому хотим присвоить значение
         * @throws TypeException          - если str не может быть преобразована в тип, соответствующий полю
         * @throws NullException          - если str пустая
         * @throws IndexNotFoundException - если не найдем элемент с таким id
         */

        public static void setBooleanById(LinkedList<HumanBeing> collection, String str, int i, String type) throws TypeException, NullException, IndexNotFoundException {
            if (Object.looseEquals(str, "true")) {
                if (Object.looseEquals(type, "RealHero")) {
                    collection.get(findId(collection, i + "")).setRealHero(true);
                } else if (Object.looseEquals(type, "HasToothpick")) {
                    collection.get(findId(collection, i + "")).setHasToothpick(true);
                } else if (Object.looseEquals(type, "carCool")) {
                    collection.get(findId(collection, i + "")).setCarCool(true);
                }
            } else if (Object.looseEquals(str, "false")) {
                if (Object.looseEquals(type, "RealHero")) {
                    collection.get(findId(collection, i + "")).setRealHero(false);
                } else if (Object.looseEquals(type, "HasToothpick")) {
                    collection.get(findId(collection, i + "")).setHasToothpick(false);
                } else if (Object.looseEquals(type, "carCool")) {
                    collection.get(findId(collection, i + "")).setCarCool(false);
                }
            } else {
                throw new TypeException("");
            }
        }

        /**
         * Определяем значение поля weaponType
         *
         * @param collection - коллекция, содержащая элементы класса HumanBeing
         * @param str        - строка, содержащая значение поля
         * @param i          - значение id
         * @throws TypeException          - если str не может быть преобразована в тип, соответствующий полю
         * @throws NullException          - если str пустая
         * @throws IndexNotFoundException - если не найдем элемент с таким id
         */
        public static void setWeaponTypeById(LinkedList<HumanBeing> collection, String str, int i) throws NullException, TypeException, IndexNotFoundException {
            collection.get(findId(collection, i + "")).setWeaponType(getWeapon(str));
        }

        /**
         * Определяем значение поля mood
         *
         * @param collection - коллекция, содержащая элементы класса HumanBeing
         * @param str        - строка, содержащая значение поля
         * @param i          - значение id
         * @throws TypeException          - если str не может быть преобразована в тип, соответствующий полю
         * @throws NullException          - если str пустая
         * @throws IndexNotFoundException - если не найдем элемент с таким id
         */
        public static void setMoodTypeById(LinkedList<HumanBeing> collection, String str, int i) throws NullException, TypeException, IndexNotFoundException {
            collection.get(findId(collection, i + "")).setMood(getMood(str));
        }

        /**
         * Определяем, какому полю нужно присвоить значение (по str) и вызываем один из методов, делающих это
         *
         * @param collection - коллекция, содержащая элементы класса HumanBeing
         * @param str        - строка, содержащая значение поля и его имя
         * @param i          - значение id (изначально равно -1, т.к. не факт, что id будет прописано в файле первым)
         * @return уникальное значение id элемента
         * @throws TypeException          - если str не может быть преобразована в тип, соответствующий полю
         * @throws NullException          - если str пустая
         * @throws IndexNotFoundException - если не найдем элемент с таким id
         * @throws IdException            - если значение id элемента не уникально
         * @throws TypeException          - если значение поля в str не может быть преобразовано в нужный тип (для enum)
         * @throws SizeException          - если значение поля в str выходит за рамки допустимого
         * @throws NumberFormatException  - если значение поля в str не может быть преобразовано в число (если это нужно)
         */
        public static int setVar(LinkedList<HumanBeing> collection, String str, int i) throws NullException, IndexNotFoundException, IdException, TypeException, SizeException, NumberFormatException {
            if (Object.equalsPart(str, "name")) {
                str = trimJson(str, "name", true);
                setNamesById(collection, str, i, "name");

            } else if (Object.equalsPart(str, "soundtrackName")) {
                str = trimJson(str, "soundtrackName", true);
                setNamesById(collection, str, i, "soundtrackName");

            } else if (Object.equalsPart(str, "carName")) {
                str = trimJson(str, "carName", true);
                setNamesById(collection, str, i, "carName");

            } else if (Object.equalsPart(str, "x")) {
                str = trimJson(str, "x", false);
                setNumberById(collection, str, i, "x");

            } else if (Object.equalsPart(str, "y")) {
                str = trimJson(str, "y", false);
                setNumberById(collection, str, i, "y");

            } else if (Object.equalsPart(str, "impactSpeed")) {
                str = trimJson(str, "impactSpeed", false);
                setNumberById(collection, str, i, "impactSpeed");

            } else if (Object.equalsPart(str, "id")) {
                str = trimJson(str, "id", false);
                i = setIdById(collection, str, i);

            } else if (Object.equalsPart(str, "realHero")) {
                str = trimJson(str, "realHero", false);
                setBooleanById(collection, str, i, "realHero");

            } else if (Object.equalsPart(str, "HasToothpick")) {
                str = trimJson(str, "HasToothpick", false);
                setBooleanById(collection, str, i, "HasToothpick");

            } else if (Object.equalsPart(str, "carCool")) {
                str = trimJson(str, "carCool", false);
                setBooleanById(collection, str, i, "carCool");

            } else if (Object.equalsPart(str, "weaponType")) {
                str = trimJson(str, "weaponType", true);
                setWeaponTypeById(collection, str, i);

            } else if (Object.equalsPart(str, "mood")) {
                str = trimJson(str, "mood", true);
                setMoodTypeById(collection, str, i);
            }
            return i;
        }

    }
}
