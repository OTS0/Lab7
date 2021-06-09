package Server;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

import Exception.*;

public class Server extends Object {
    final int serverPort = 6666;
    
 public Server(){
     goServer();
 }

    public void goServer(){
        System.out.println("Сервер запущен");
        SocketChannel channel = null;
        try {
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
