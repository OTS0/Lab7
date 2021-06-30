/**
 * @author Maria Ilyakova
 */
package FromTask;
import Exception.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Scanner;
import Server.*;


public class HumanBeing implements Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private Boolean hasToothpick; //Поле не может быть null
    private int impactSpeed; //Максимальное значение поля: 967
    private String soundtrackName; //Поле не может быть null
    private WeaponType weaponType; //Поле может быть null
    private Mood mood; //Поле может быть null
    private Car car; //Поле может быть null
    private String login = null;

    public HumanBeing(){
        this.coordinates = coord;
        this.coordinates = coord;
        this.car = Car;
        LocalDateTime creationDate = LocalDateTime.now();
        this.creationDate = creationDate;
    }
    public HumanBeing( Integer id,String name, Coordinates coordinates, LocalDateTime creationDate, boolean realHero, Boolean hasToothpick, int impactSpeed, String soundtrackName, WeaponType weaponType, Mood mood, Car car, String login) {
        this.id=id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
        this.login = login;
    }

    Car Car = new Car();
    Coordinates coord = new Coordinates();
    public String getLogin(){
        return this.login;
    }
    public void setLogin(String login){
        this.login = login;
    }
    /**
     *
     * @param obj1 - сравниваемая строка
     * @param obj2 - сравниваемая строка
     * @return true, если строки не null, одинаковой длины и состоят из одинаковых символов
     */
    // проверка равенства двух строк
    public boolean equals(String obj1, String obj2) {
        boolean check = true;
        if (obj2 == null || obj1 == null) {
            check = false;
        }
        if (obj1.length() != obj2.length()) {
            check = false;
        } else {
            for (int j = 0; j < obj2.length(); j++) {
                if (obj1.charAt(j) != obj2.charAt(j)) {
                    check = false;
                }

            }
        }
        return check;
    }

    /**
     * Геттеры
     * @return значения полей объекта
     */

    public String getName(){
        return this.name;
    }
    public Boolean getRealHero(){
        return this.realHero;
    }
    public Boolean getHasToothpick(){
        return this.hasToothpick;
    }
    public String getSoundtrackName(){
        return this.soundtrackName;
    }
    public WeaponType getWeaponType(){
        return this.weaponType;
    }
    public Mood getMood(){
        return this.mood;
    }
    public Car getCar(){
        return this.car;
    }
    public Coordinates getCoord(){
        return this.coordinates;
    }
    public int getImpactSpeed(){
        return this.impactSpeed;
    }
    public int getId(){
        return this.id;
    }
    public int getCoordx(){
        return this.coord.getCoordx();
    }
    public float getCoordy(){
        return this.coord.getCoordy();
    }
    public Boolean getCarCool(){
        return this.car.getCool();
    }
    public String getCarName(){
        return this.car.getName();
    }


    public void setId(int id){
        this.id = id;
    }

    /**
     * Присваивание значения полю name с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setName(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("name (String): ");
            }
            this.name = in.nextLine();
            if (this.name == null || equals(this.name, "")){
                throw new NullException("name");
            }
        }
        catch (NullException  e){
            if (check) {
                System.out.print(e.getMessage());
                setName(in, check);
            }else {
                throw new CollectionException("Item name not specified.");
            }
        }
    }

    /**
     * Присваивание значения полю x с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setCoordx(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("x (Integer, < 547): ");
            }
            String value;
            value = in.nextLine();
            if (equals("", value)){
                throw new NullException("x");
            }
            int var = Integer.parseInt(value);
            if (var >= 547){
                throw new SizeException(547);
            }
            this.coordinates.setCoordx(var);
        }
        catch (NumberFormatException e){
            if (check) {
                System.out.println("Please, enter number");
                setCoordx(in, check);
            } else {
                throw new CollectionException("Non-numeric value entered for variable x.");
            }
        }
        catch (NullException | SizeException n){
            if (check) {
                System.out.println(n.getMessage());
                setCoordx(in, check);
            } else{
                throw new CollectionException("No value entered for variable x.");
            }
        }
    }
    /**
     * Присваивание значения полю y с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setCoordy(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("y (Float): ");
            }
            String value;
            value = in.nextLine();
            if (equals("", value)){
                throw new NullException("y");
            }
            this.coordinates.setCoordy(Float.parseFloat(value));
        }
        catch (NumberFormatException e){
            if (check) {
                System.out.println("Please, enter number");
                setCoordy(in, check);
            } else{
                throw new CollectionException("Non-numeric value entered for variable y.");
            }
        }
        catch (NullException n){
            if (check){
                System.out.println(n.getMessage());
                setCoordy(in, check);
            } else{
            throw new CollectionException("No value entered for variable y.");
        }
        }
    }
    /**
     * Присваивание значения полю realHero с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setRealHero(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("realHero (Boolean): ");
            }
            String value = in.nextLine();
            if (equals("", value)){
                throw new NullException("realHero");
            }
            else if (!Command.looseEquals(value, "true") && !Command.looseEquals(value, "false")){
                throw new TypeException("boolean");
            }
            this.realHero = Boolean.parseBoolean(value);
        }
        catch(NullException n){
            if (check){
            System.out.println(n.getMessage());
            setRealHero(in, check);

            } else {
                throw new CollectionException("No value entered for variable realHero.");
            }
        }
        catch(TypeException t){
            if (check){
                System.out.println(t.getMessage());
                setRealHero(in, check);

            } else {
                throw new CollectionException("Invalid value specified for variable realHero.");
            }
        }
    }
    /**
     * Присваивание значения полю soundtrackName с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setSoundtrackName(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("soundtrackName (String): ");
            }
            this.soundtrackName = in.nextLine();
            if (this.soundtrackName == null || equals(this.soundtrackName, "")) {
                throw new NullException("soundtrackName");
            }
        }
        catch (NullException e){
            if (check) {
                System.out.println(e.getMessage());
                setSoundtrackName(in, check);
            } else {
                throw  new CollectionException("No value entered for variable soundtrackName.");
            }
        }
    }
    /**
     * Присваивание значения полю hasToothpick с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setHasToothpick(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("hasToothpick (Boolean): ");
            }
            String value = in.nextLine();
            if (equals("", value)){
                throw new NullException("hasToothpick");
            }
            else if (!Command.looseEquals(value, "true") && !Command.looseEquals(value, "false")){
                throw new TypeException("boolean");
            }
            this.hasToothpick = Boolean.parseBoolean(value);
        }
        catch(NullException n){
            if (check){
                System.out.println(n.getMessage());
                setHasToothpick(in, check);

            } else {
                throw new CollectionException("No value entered for variable hasToothpick.");
            }
        }
        catch(TypeException t){
            if (check){
                System.out.println(t.getMessage());
                setHasToothpick(in, check);

            } else {
                throw new CollectionException("Invalid value specified for variable hasToothpick.");
            }
        }
    }
    /**
     * Присваивание значения полю weaponType с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
    * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setWeaponType(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("weaponType  (Possible answers: SHOTGUN, RIFLE, KNIFE, MACHINE_GUN): ");
            }
            String type = in.nextLine();
            this.weaponType = Command.getWeapon(type);
        }
        catch(NullException n){
            this.weaponType = null;
        }
        catch(TypeException t){
            if (check){
                System.out.println(t.getMessage());
                setWeaponType(in, check);

            } else {
                throw new CollectionException("Invalid value specified for variable weaponType.");
            }
        }
    }
    /**
     * Присваивание значения полю mood с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setMood(Scanner in, Boolean check) throws CollectionException{
        try{
            if (check) {
                System.out.print("mood  (Possible answers: SADNESS, SORROW, LONGING, GLOOM, FRENZY): ");
            }
            String type = in.nextLine();
            this.mood = Command.getMood(type);
        }
        catch(NullException n){
            this.mood = null;
        }
        catch(TypeException t){
            if (check){
                System.out.println(t.getMessage());
                setMood(in, check);

            } else {
                throw new CollectionException("Invalid value specified for variable mood.");
            }
        }
    }
    /**
     * Присваивание значения полю carName с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setCarName(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("Car.name (String): ");
            }
            this.car.setName(in.nextLine());
            if (this.car.getName() == null || equals(this.car.getName(), "")) {
                throw new NullException("Car.name");
            }

        }
        catch (NullException e){
            if (check) {
                System.out.println(e.getMessage());
                setCarName(in, check);
            } else{
                throw new CollectionException("No value entered for variable Car.name.");
            }
        }
    }
    /**
     * Присваивание значения полю carCool с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setCarCool(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("Car.cool (Boolean): ");
            }
            String value = in.nextLine();
            if (equals("", value)) {
                throw new NullException("Car.cool");
            }
            else if (!Command.looseEquals(value, "true") && !Command.looseEquals(value,"false")) {
                throw new TypeException("boolean");
            }
            this.car.setCool(Boolean.parseBoolean(value));
        }
        catch(NullException n){
            this.car.setCool(null);
        }
        catch(TypeException t){
            if (check){
                System.out.println(t.getMessage());
                setCarCool(in, check);

            } else {
                throw new CollectionException("Invalid value specified for variable Car.cool.");
            }
        }
    }
    /**
     * Присваивание значения полю impactSpeed с учетом разных типов ввода и с обработкой исключений
     * @param in - объект Scanner для чтения значения поля с командной строки
     * @param check - true, если данные будут вводиться пользователем, false, если производится чтение из файла
     * @throws CollectionException - исключение на случай ошибок в коллекции, читаемой из файла
     */
    public void setImpactSpeed(Scanner in, Boolean check) throws CollectionException{
        try {
            if (check) {
                System.out.print("impactSpeed (Integer, < 968): ");
            }
            String value;
            value = in.nextLine();
            if (equals("", value)){
                throw new NullException("impactSpeed");
            }
            this.impactSpeed = Integer.parseInt(value);
            if (this.impactSpeed > 967){
                throw new SizeException(968);
            }
        }
        catch (NumberFormatException e){
            if (check) {
                System.out.print("Please, enter number ");
                setImpactSpeed(in, check);
            } else {
                throw new CollectionException("Invalid value specified for variable impactSpeed.");
            }
        }
        catch (NullException n){
            if (check) {
                System.out.println(n.getMessage());
                setImpactSpeed(in, check);
            } else {
                throw new CollectionException("No value entered for variable impactSpeed.");
            }
        }
        catch (SizeException v){
            if (check) {
                System.out.println(v.getMessage());
                setImpactSpeed(in, check);
            } else {
                throw new CollectionException("Invalid value specified for variable impactSpeed.");
            }
        }
    }


    /**
     * Обычные сеттеры без обработки исключений
     */
    public void setName(String str){
        this.name = str;
    }
    public void setCoordx(int x){
        this.coordinates.setCoordx(x);
    }
    public void setCoordy(float y){
        this.coordinates.setCoordy(y);
    }
    public void setRealHero(boolean var){
        this.realHero = var;
    }
    public void setHasToothpick(boolean var){
        this.hasToothpick = var;
    }
    public void setImpactSpeed(int x){
        this.impactSpeed = x;
    }
    public void setSoundtrackName(String str){
        this.soundtrackName = str;
    }
    public void setWeaponType(WeaponType type){
        this.weaponType = type;
    }
    public void setMood(Mood mood){
        this.mood = mood;
    }
    public void setCarName(String str){
        this.car.setName(str);
    }
    public void setCarCool(boolean var){
        this.car.setCool(var);
    }
}

