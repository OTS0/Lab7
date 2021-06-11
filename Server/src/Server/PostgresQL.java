package Server;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresQL {
    private static Boolean creation =false;
    private String url = "jdbc:postgresql://pg:6666/studs";
    private String user = "s312632";
    private String passwd = "rsx255";
    public void setCreation(Boolean creation) {
        this.creation = creation;
    }

    public static Boolean getCreation() {
        return creation;
    }

//непонятно, надо доделать
    public String createTable(Connection connection){
        try{Statement statement=null;
            if (!creation){ statement= DriverManager.getConnection(url, user, passwd).createStatement();}

            String users="";
            statement.executeUpdate(users);
            String pQL = "CREATE DATABASE HumanBeing " +
                "(ID INT PRIMARY KEY NOT NULL," +
                " NAME TEXT NOT NULL, " +
                " \"coordinateX\" INT NOT NULL CONSTRAINT \"CheckCoordinateX\" CHECK \"CoordinateX\"<=574" +
                " \"coordinateY\" FLOAT NOT NULL, " +
                " \"creationDate\" timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                " \"realHero BOOLEAN\"," +
                " \"hasToothpick BOOLEAN NOT NULL\"" +
                " \"impactSpeed\" INT CONSTRAINT \"CheckImpactSpeed\" CHECK \"impactSpeed\"<=967" +
                " \"soundtrackName\" TEXT NOT NULL" +
                " \"weaponType\" TEXT" +
                " \"mood\" TEXT" +
                " \"carName\" TEXT NOT NULL" +
                " \"carCool\" BOOLEAN)";
            statement.executeUpdate(pQL); }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    private String hash (String str){
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
