package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import FromTask.*;
import Exception.*;
import Server.*;




public class Client extends Object  implements Serializable {
    int port=6666;
    private String address="";
    private Socket ClientSocket;



    public void workClient(){

        try{
            ClientSocket = new Socket(address,port);
            App app = new App();
            ObjectOutputStream out = new ObjectOutputStream(ClientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(ClientSocket.getInputStream());
            app.start(in, out);
        }
        catch (IOException | ClassNotFoundException e){
        }
    }
    String str = "";









}