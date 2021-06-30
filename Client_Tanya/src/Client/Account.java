package Client;

import Server.App;

import java.util.Scanner;
/**
 * Class for work with users
 */
public class Account {
    String login;
    String passwd;

    public Account(String login, String passwd){
        this.login=login;
        this.passwd=passwd;
    }
    /**
     * return object of class Account
     * @return
     */
    @Override
    public String toString() {
        return "Account{" +
                "login='" + login + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
    /**
     * Creates an account object that is already in the database
     * @param scanner
     * @return
     */
    public static Account login(Scanner scanner){
        System.out.println("Enter login, please");
        String name=scanner.nextLine();
        System.out.println("Enter password, please");
        String passwd=scanner.nextLine();
        Account account=new Account(name,passwd);
        System.out.println(account.toString());
        return account;
    }
    /**
     * Checks that the user has entered (registration or log in )
     * @param scanner
     * @return
     */
    public static Boolean entry(Scanner scanner){
        System.out.println("To work with the collection, you need to log in.\n" +
                "enter «registration», if you haven't account, else enter «log in» ");
        String entrance=scanner.nextLine();
        if (Object.equalsPart(entrance,"registration")){
            return false;
        }
        else if(Object.equalsPart(entrance,"log in")) {
            return true;}
        else {return null;}

    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {
        return passwd;
    }
    /**
     * Creates an account object that isn't in the database
     * @param scanner
     * @return
     */
    public static Account register(Scanner scanner) {//добавить проверку на уникальность имени
        System.out.println("Enter login, please");
        String name=scanner.nextLine();
        System.out.println("Enter password, please");
        String passwd=scanner.nextLine();
        System.out.println("Enter password again, please");
        String passwd1=scanner.nextLine();
        while (!App.equals(passwd1,passwd)){
            System.out.println("Password mismatch. Enter password again, please");
            System.out.println("Enter password, please");
            passwd=scanner.nextLine();
            System.out.println("Enter password again, please");
            passwd1=scanner.nextLine();
        }
        Account account=new Account(name,passwd);
        return account;
    }

}


