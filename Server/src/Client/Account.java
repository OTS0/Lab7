package Client;

import Server.App;

import java.util.Scanner;

public class Account {
    String login;
    String passwd;
    public Account(String login, String passwd){
        this.login=login;
        this.passwd=passwd;
    }

    /**
     * return all object account
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
        String name=scanner.next();
        System.out.println("Enter password, please");
        String passwd=scanner.next();
        Account account=new Account(name,passwd);
        System.out.println(account.toString());
        return account;
    }

    /**
     * Checks that the user has entered (registration or log in )
     * @param scanner
     * @return
     */
    public static boolean entry(Scanner scanner){
        System.out.println("To work with the collection, you need to log in.\n" +
                "enter <<registration>>, if you haven't account, else enter something ");
        String entrance=scanner.next();
        if (Object.equalsPart(entrance,"registration")){
            return false;
        }
        else {
            return true;
        }
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
        String name=scanner.next();
        System.out.println("Enter password, please");
        String passwd=scanner.next();
        System.out.println("Enter password again, please");
        String passwd1=scanner.next();
        while (!App.equals(passwd1,passwd)){
            System.out.println("Password mismatch. Enter password again, please");
            System.out.println("Enter password, please");
            passwd=scanner.next();
            System.out.println("Enter password again, please");
            passwd1=scanner.next();
        }
        System.out.println("Your account has been created");
        Account account=new Account(name,passwd);
        return account;
    }

}


