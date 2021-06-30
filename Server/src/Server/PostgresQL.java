package Server;

import Client.Account;
import FromTask.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import Exception.*;

/**
 * Class for work with Date base
 */
public class PostgresQL {
    public PostgresQL() {
    }

    private String url = "jdbc:postgresql://pg/studs";
    private String user = "s285652";
    private String passwd = "skx966";

    private static Boolean creation = true;
    public static Connection connection = null;
    static List<HumanBeing> humanBeings = new CopyOnWriteArrayList<>();

    public static Connection getConnection() {
        return connection;
    }

    /**
     * change creation(creation - for checks table creation)
     * @param creation
     */
    public void setCreation(Boolean creation) {
        this.creation = creation;
    }

    /**
     * connect with database
     * @return conection
     */
    public Connection connectWithDataBase()  {
        try {
            Class.forName("org.postgresql.Driver");
            //System.out.println("Driver connected");
            connection=DriverManager.getConnection(url,user,passwd);
            //System.out.println("WoW!Connection established!");
            }
        catch (ClassNotFoundException | SQLException throwables){
            System.out.println("Unable to connect to database");
        }
        return connection;}
    public static Boolean getCreation() {
        return creation;
    }


    /**
     * Clean all database humanbeing
     * @param connection
     * @throws SQLException
     */
    public static void deleteCollection(Connection connection) throws SQLException {
        Statement statement= connection.createStatement();
        statement.executeUpdate("DELETE FROM humanbeing");
    }


    /**
     * Method for creating a database with the fields we need (class HumanBeing) and for creating table users
     */
    public void createTable(Connection connection) throws SQLException {
        Statement statement=null;
            statement= connection.createStatement();
        statement.executeUpdate("CREATE TABLE users\n" +
                "(\n" +
                "    login text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    password text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    CONSTRAINT users_pkey PRIMARY KEY (login)\n" +
                ")\n" +
                "\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE users\n" +
                "    OWNER to s285652;");

            statement.executeUpdate("CREATE TABLE humanbeing " +
                    "(id serial PRIMARY KEY NOT NULL," +
                    " name text NOT NULL, " +
                    " coordinate_x integer NOT NULL," +
                    " coordinate_y float NOT NULL, " +
                    " creation_date timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    " real_hero boolean," +
                    " has_tooth_pick boolean NOT NULL," +
                    " impact_speed integer CONSTRAINT check_impact_speed CHECK (impact_speed<=967)," +
                    " soundtrack_name text NOT NULL," +
                    " weapon_type text," +
                    " mood text," +
                    " car_name text NOT NULL," +
                    " car_cool boolean," +
                    " login text NOT NULL,"+
                    "CONSTRAINT check_coordinate_x CHECK (coordinate_x<=574))");
            statement.executeUpdate("CREATE SEQUENCE id START 1;");
            setCreation(true);


    }

    /**
     * create new account in table humanbeing
     * @param login
     * @param passwd
     * @throws SQLException
     */
    public static void createAccount(String login, String passwd) throws SQLException{
        String hashpasswd= hash(passwd);
        connection.prepareStatement(String.format("INSERT INTO users VALUES ('%s','%s');", login, hashpasswd)).executeUpdate();
    }

    /**
     * update element in table humanbeing in database
     * @param humanBeing
     * @return
     * @throws SQLException
     */
    public boolean updateHumanBeing(HumanBeing humanBeing) throws SQLException {
        Boolean checkUpdate=false;
        ResultSet resultSetHumanBeing = connection.prepareStatement("SELECT * FROM humanbeing").executeQuery();
        String name = "null";
        while(resultSetHumanBeing.next()) {
            if (resultSetHumanBeing.getInt("id") == humanBeing.getId()) {
                name = resultSetHumanBeing.getString("name");
                connection.prepareStatement(String.format("UPDATE humanbeing SET " +
                                "name='%s', " +
                                "coordinate_x='%s', " +
                                "coordinate_y='%s', " +
                                "real_hero='%s', " +
                                "has_tooth_pick='%s', " +
                                "impact_speed='%s', " +
                                "soundtrack_name='%s', " +
                                "weapon_type='%s', " +
                                "mood='%s', " +
                                "car_name='%s', " +
                                "car_cool='%s', " +
                                "login ='%s' " +
                                "WHERE id = '%s';",
                        humanBeing.getName(),
                        humanBeing.getCoordx(),
                        humanBeing.getCoord().toString().replace(',', '.'),
                        humanBeing.getRealHero(),
                        humanBeing.getHasToothpick(),
                        humanBeing.getImpactSpeed(),
                        humanBeing.getSoundtrackName(),
                        humanBeing.getWeaponType(),
                        humanBeing.getMood(),
                        humanBeing.getCarName(),
                        humanBeing.getCarCool(),
                        humanBeing.getLogin(),
                        humanBeing.getId())).executeUpdate();

            }
        }
        if (StringHandler.equals(name, (humanBeing.getName()))){
            checkUpdate=true;
        }

        return checkUpdate;
    }

    /**
     * Gets a collection from a database
     * @throws SQLException
     * @throws NullPointerException
     */
    public static void getCollection() throws SQLException, NullPointerException, NullException, TypeException {
        ResultSet resultSetHumanBeing =connection.prepareStatement("SELECT * FROM humanbeing;").executeQuery();
        while (resultSetHumanBeing.next()) {
            Integer id=resultSetHumanBeing.getInt("id");
            String name=resultSetHumanBeing.getString("name");
            Integer x=resultSetHumanBeing.getInt("coordinate_x");
            Float y=resultSetHumanBeing.getFloat("coordinate_y");
            //java.time.LocalDateTime creationDate = LocalDateTime.parse(resultSetHumanBeing.getString("creation_date")); не работает
            LocalDateTime creationDate=LocalDateTime.now();
            boolean realHero=resultSetHumanBeing.getBoolean("real_hero");
            Boolean hasToothpick=resultSetHumanBeing.getBoolean("has_tooth_pick");
            int impactSpeed=resultSetHumanBeing.getInt("impact_speed");
            String soundtrackName=resultSetHumanBeing.getString("soundtrack_name");
            WeaponType weaponType=Command.getWeapon(resultSetHumanBeing.getString("weapon_type"));
            Mood mood=Command.getMood(resultSetHumanBeing.getString("mood"));
            String carName=resultSetHumanBeing.getString("car_name");
            Boolean cool=resultSetHumanBeing.getBoolean("car_cool");
            String login= resultSetHumanBeing.getString("login");
            Car car= new Car();
            car.setCool(cool);
            car.setName(carName);
            Coordinates coord = new Coordinates();
            coord.setCoordx(x);
            coord.setCoordy(y);
            HumanBeing humanBeing = new HumanBeing(id,name,coord,creationDate,realHero,hasToothpick,impactSpeed,soundtrackName,weaponType,mood,car, login);
            humanBeings.add(humanBeing);
        }
    }

    /**
     * sort collection ,which get from database
     * @param humanBeings
     */
    public void sort(List<HumanBeing> humanBeings) {
        Comparator<HumanBeing> comparator = Comparator.comparing(HumanBeing::getId);
        humanBeings.sort(comparator);
    }
    /**
     * Add humanBeing to DateBase
     * @param humanBeing
     * @param connection
     */
    public Boolean addHumanBeingToBase(HumanBeing humanBeing, Connection connection) {
        Boolean check=false;
        try {
            connection.prepareStatement(String.format("INSERT INTO HumanBeing (name, coordinate_x, coordinate_y, real_hero, " +
                            "has_tooth_pick, impact_speed, soundtrack_name, weapon_type, mood, car_name, car_cool, login)" +
                            " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s','%s', '%s', '%s', '%s', '%s');", humanBeing.getName(),
                    humanBeing.getCoord().getCoordx(),
                    humanBeing.getCoord().getCoordy(), humanBeing.getRealHero(), humanBeing.getHasToothpick(),
                    humanBeing.getImpactSpeed(), humanBeing.getSoundtrackName(),humanBeing.getWeaponType(), humanBeing.getMood(),
                    humanBeing.getCarName(), humanBeing.getCarCool(), humanBeing.getLogin())).executeUpdate();
            ResultSet resultSetHumanBeing =connection.prepareStatement("SELECT * FROM humanbeing;").executeQuery();
            while (resultSetHumanBeing.next()) {
                String name = resultSetHumanBeing.getString("name");

                if (StringHandler.equals(name, humanBeing.getName())) {
                    humanBeings.add(humanBeing);
                    check = true;
                    break;
                }
            }
        } catch (SQLException throwables) {
            check=false;
            System.out.println("The object has not been added to the database");

        }
        return check;
    }

    /**
     * remove element by id from database
     * @param id
     * @throws SQLException
     */
    public void removeHumanBeing(int id) throws SQLException {
        connection.prepareStatement(String.format("DELETE FROM humanbeing WHERE id = '%s';", id)).executeUpdate();
    }
    public static void setConnection(Connection connection) {
        PostgresQL.connection = connection;
    }
    public void getUsers() throws SQLException {
        ResultSet resultSetUsers =connection.prepareStatement("SELECT * FROM users;").executeQuery();
        while (resultSetUsers.next()) {
            String login=resultSetUsers.getString("login");
            String pwd=resultSetUsers.getString("password");
        }
    }
    /**
     * Hashes the str(password) use algorithm SHA-384
     * @param str
     * @return
     */
    public static String hash(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] messageDigest = md.digest(str.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks logins, if the logins match, return true
     * @param humanBeing
     * @param login
     * @return
     */
    public Boolean checkLog(HumanBeing humanBeing, String login)  {
        Boolean checkLog = false;
        try{
            ResultSet users = connection.prepareStatement(String.format("SELECT * FROM users WHERE login = '%s';", humanBeing.getLogin())).executeQuery();
            while(users.next()){
                System.out.println(login + " and " + users.getString("login"));
                if (StringHandler.equals(users.getString("login"), login)){
                    System.out.println("TRUE");
                    checkLog = true;
                    break;
                }
            }
        }
        catch (SQLException p){
            System.out.println("Проблема с Базой данных users");
        }
        return checkLog;
    }

}
