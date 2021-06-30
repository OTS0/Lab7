package Client;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;
import FromTask.*;
import Exception.*;
import Server.*;




public class Client extends Object implements Serializable {
    int port=1115;
    private String address="127.0.0.1";
    private Socket clientSocket;

    /**
     * Method for work Client
     * @throws IOException
     */
    public void workClient() throws IOException {

        try {
            clientSocket = new Socket(address, port);
            App app = new App();
            System.out.println("1");
            ObjectOutputStream out = null;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println("2");
            ObjectInputStream in = null;
            in = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("3");
            app.start(in, out);
            in.close();
            out.close();

        } catch (ConnectException c) {
            System.out.println("Соединения нет");
        } catch (SocketException | ClassNotFoundException s) {
            System.out.println("(Соединение прервано)");
        } catch (NoSuchElementException e) {
            System.out.println("Invalid input! Collection didn't save...");
            System.out.println("It is very bad.");
            System.out.println(":(");
            System.out.println("Goodbye");
        }

    }





}