package Client;

import java.util.Scanner;

public class Account {
String login;
String passwd;
    public Account(String login,String passwd){
        this.login=login;
        this.passwd=passwd;
    }

    @Override
    public String toString() {
        return "Account{" +
                "login='" + login + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }

    public static Account login(Scanner scanner){
        System.out.println("Enter login, please");
        String name=scanner.next();
        System.out.println("Enter password, please");
        String passwd=scanner.next();
        Account account=new Account(name,passwd);
        System.out.println(account.toString());
        return account;
    }

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
