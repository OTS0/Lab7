package Server;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import Exception.*;
import FromTask.*;

public class Server extends Object {
    final int serverPort = 5432;
    private String url = "jdbc:postgresql://pg:5432/studs";//возможно нужно вынестi в переменную оружения
    private String user = "s312632";
    private String passwd = "rsl255";
 public Server(){
     goServer();
 }

    public void goServer(){
        System.out.println("Сервер запущен");
        SocketChannel channel = null;
        PostgresQL ql=new PostgresQL(url,user,passwd);
        try {
            Connection connection= null;
            try {
                connection = ql.connectWithDataBase();
                Coordinates coordinates=new Coordinates(1, (float) 5);
                HumanBeing humanBeing=new HumanBeing("OTS0",coordinates,true,true,154, "45", WeaponType.SHOTGUN, Mood.FRENZY,new Car("auto", true),"OTS");
                ql.getCollection();
                if(!PostgresQL.getCreation()){
                ql.createTable(connection);
            }
                ql.addHumanBeingToBase(humanBeing,connection);
            } catch (SQLException|NullPointerException throwables) {
                System.out.println("Database connection problems");
            }
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(serverPort));
            server.configureBlocking(false);
            while (true) {
                go(server, null);
            }


        } catch (IOException | ClassNotFoundException | IdException | IndexNotFoundException i) {
            System.out.println("Соединение прервано");
        }

    }
    public void go(ServerSocketChannel server, SocketChannel channel) throws ClassNotFoundException, IdException, IndexNotFoundException {
        try {
            while (channel == null) {
                channel = server.accept();
            }
        if (channel != null) {
            App app = new App();
            ObjectOutputStream out = new ObjectOutputStream(Channels.newOutputStream(channel));
            ObjectInputStream in = new ObjectInputStream(Channels.newInputStream(channel));
            while (server.isOpen()) {
                app.start(in, out);
            }
            in.close();
            out.close();
            server.close();
        }
        } catch (SocketTimeoutException e) {
            System.out.println("Превышено время ожидания.");
        } catch (IOException | NullException i){
            go(server, null);

        }
    }


}
