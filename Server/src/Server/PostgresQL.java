package Server;
import FromTask.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostgresQL {
    public PostgresQL(String url, String user,String passwd){
        this.url=url;
        this.user=user;
        this.passwd=passwd;
    }
    private static Boolean creation =true;
    private String url ;//возможно нужно вынест в переменную оружения
    private String user ;
    private String passwd ;
    public Connection connection=null;
    List<HumanBeing> humanBeings = new CopyOnWriteArrayList<>();

    public void setCreation(Boolean creation) {
        this.creation = creation;
    }

    /**
     * conect with datebase
     * @return conection
     */
    public Connection connectWithDataBase()  {
        try {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver connected");
            connection=DriverManager.getConnection(url,user,passwd);}
         catch (ClassNotFoundException | SQLException throwables){
             System.out.println("Unable to connect to database");
        }
        System.out.println("WoW!Connection established!");
    return connection;}
    public static Boolean getCreation() {
        return creation;
    }

    /**
     * Method for creating a database with the fields we need (class HeingBeing)
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
                "    OWNER to s312632;");

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
    public void createAccount(String login, String passwd) throws SQLException{
        String hashpasswd= hash(passwd);
        connection.prepareStatement(String.format("INSERT INTO users VALUES ('%s','%s');", login, hashpasswd)).executeUpdate();
    }
    public void checkAccount(String login, String passwd) throws SQLException, NoSuchAlgorithmException {
        if (login.equals("alreadyLoggedInUser")) throw new SQLException("Недопустимое имя пользователя");
        ResultSet users = connection.prepareStatement(String.format("SELECT * FROM users WHERE login = '%s';", login)).executeQuery();
        String hashpasswd= hash(passwd);
        int k = 0;
        while (users.next()) {
            k++;
            if (!users.getString("password").equals(hashpasswd)) throw new SQLException("Пароль не подходит");
        }
        if (k == 0) throw new SQLException("Пользователя с данным именем не найденно");
    }
    public void updateHumanBeing (HumanBeing humanBeing) throws SQLException {
        connection.prepareStatement(String.format("UPDATE venues SET name='%s'")).executeUpdate();;
    }

    /**
     * Gets a collection from a database
     * @throws SQLException
     * @throws NullPointerException
     */
    public void getCollection() throws SQLException,NullPointerException {
        ResultSet resultSetHumanBeing =connection.prepareStatement("SELECT * FROM humanbeing;").executeQuery();
        while (resultSetHumanBeing.next()) {
            Integer id=resultSetHumanBeing.getInt("id");
            String name=resultSetHumanBeing.getString("name");
            Integer x=resultSetHumanBeing.getInt("coordinate_x");
            Float y=resultSetHumanBeing.getFloat("coordinate_y");
            //java.time.LocalDateTime creationDate = LocalDateTime.parse(resultSetHumanBeing.getString("creation_date")); не работает
            java.time.LocalDateTime creationDate=LocalDateTime.now();
            boolean realHero=resultSetHumanBeing.getBoolean("real_hero");
            Boolean hasToothpick=resultSetHumanBeing.getBoolean("has_tooth_pick");
            int impactSpeed=resultSetHumanBeing.getInt("impact_speed");
            String soundtrackName=resultSetHumanBeing.getString("soundtrack_name");
            WeaponType weaponType=WeaponType.valueOf(resultSetHumanBeing.getString("weapon_type"));
            Mood mood=Mood.valueOf(resultSetHumanBeing.getString("mood"));
            String carName=resultSetHumanBeing.getString("car_name");
            Boolean cool=resultSetHumanBeing.getBoolean("car_cool");
            String login= resultSetHumanBeing.getString("login");
            HumanBeing humanBeing=new HumanBeing(id,name,new Coordinates(x,y),creationDate,realHero,hasToothpick,impactSpeed,soundtrackName,weaponType,mood,new Car(carName,cool), login);
            System.out.println(humanBeing.toString());
            humanBeings.add(id,humanBeing);
        }
        humanBeings.sort((Comparator<? super HumanBeing>) humanBeings);
    }
    public void sort(List<HumanBeing> humanBeings) {
        Comparator<HumanBeing> comparator = Comparator.comparing(HumanBeing::getId);
        humanBeings.sort(comparator);
    }
    /**
     * Add humanBeing to DateBase
     * @param humanBeing
     * @param connection
     */
    public  void addHumanBeingToBase(HumanBeing humanBeing, Connection connection) {

        try {
            connection.prepareStatement(String.format("INSERT INTO HumanBeing (name, coordinate_x, coordinate_y, real_hero, " +
                            "has_tooth_pick, impact_speed, soundtrack_name, weapon_type, mood, car_name, car_cool, login)" +
                            " VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s','%s', '%s', '%s', '%s', '%s');", humanBeing.getName(),
                    humanBeing.getCoordinates().getCoordx(),
                    humanBeing.getCoordinates().getCoordy(), humanBeing.getRealHero(), humanBeing.getHasToothpick(),
                    humanBeing.getImpactSpeed(), humanBeing.getSoundtrackName(),humanBeing.getWeaponType(), humanBeing.getMood(),
                    humanBeing.getCarName(), humanBeing.getCarCool(), humanBeing.getLogin())).executeUpdate();
            ResultSet resultSetHumanBeing =connection.prepareStatement("SELECT * FROM humanbeing;").executeQuery();
            Integer id=resultSetHumanBeing.getInt("id");
            String name=resultSetHumanBeing.getString("name");
            if (name.equals(humanBeing.getName())){
            humanBeings.add(id, humanBeing);
                System.out.println("The object has been added to the database");}
        } catch (SQLException throwables) {
            System.out.println("The object has not been added to the database");
        }
    }


    public void removeHumanBeing(HumanBeing humanBeing) throws SQLException {
        connection.prepareStatement(String.format("DELETE FROM humanbeing WHERE id = '%s';", humanBeing.getId())).executeUpdate();
    }

    /**
     * Hashes the str(password)
     * @param str
     * @return
     */
    public String hash(String str){
        try {
            // getInstance() method is called with algorithm SHA-384
            MessageDigest md = MessageDigest.getInstance("SHA-384");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(str.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
