package Server;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import Client.Request;
import Exception.*;
import FromTask.WeaponType;

public class Server extends Object {
    final int serverPort = 1112;


    public Server(){
        connect();
    }

    public void connect() {
        try {
            System.out.println("Сервер запущен");
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(serverPort));
            server.configureBlocking(false);
            Selector selector = Selector.open();
            SelectionKey key = server.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = readyKeys.iterator();
                while (iterator.hasNext()){
                    ServerSocketChannel server_now = (ServerSocketChannel) key.channel();
                    //
                    try {
                        SocketChannel client = null;
                        while (client == null) {
                            System.out.println("accept");
                            client = server_now.accept();
                        }
                        App app = new App();
                        Running r = new Running(app, client);
                        BigRunning b = new BigRunning(app, client, r);
                        b.start();
                    } catch (IOException i) {
                        System.out.println("IOException in method connect");
                        return;
                    }
                    //

                    iterator.next();
                    iterator.remove();
                }
            }
        } catch (IOException i) {
            System.out.println("Соединение прервано");
            i.printStackTrace();
        }
    }

    public class BigRunning extends Thread {
        SocketChannel client;
        App app;
        Running r;
        public BigRunning(App app, SocketChannel client, Running r){
            this.client = client;
            this.app = app;
            this.r = r;
        }

        public void run() {
            while (client.isOpen() && !app.getExit()) {
                try {
                    System.out.println("НОВЫЙ КРУГ");
                    Thread thread_read = new Thread(r::read);
                    thread_read.start();
                    thread_read.join();
                    if (app.getRequest() != null) {
                        Thread thread_work = new Thread(r::work);
                        System.out.println("этап работы");
                        thread_work.start();
                        thread_work.join();
                    }
                    if (app.getAnswer() != null) {
                        Thread thread_write = new Thread(r::write);
                        System.out.println("этап записи");
                        thread_write.start();
                        thread_write.join();
                    }
                } catch(InterruptedException i){
                    System.out.println("Соединение прервано");
                    break;
                }
            }
        }
    }

}
