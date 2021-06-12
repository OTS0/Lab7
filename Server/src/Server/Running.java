package Server;

import Client.Request;
import Exception.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.logging.FileHandler;

public class Running implements Runnable {
    SocketChannel channel;
    App app;
    ObjectInputStream in;
    ObjectOutputStream out;
    Request request = null;
    Answer answer = null;
    public void run(){}
    public Running(App app, SocketChannel client) throws IOException{
        out = new ObjectOutputStream(Channels.newOutputStream(client));
        in = new ObjectInputStream(Channels.newInputStream(client));
        this.channel = client;
        this.app = app;

    }

    public void work(){
        try {
            app.start();
        } catch (IdException | NullException n) {
            n.printStackTrace();
        }
    }
    public void read() {
        request = app.read(channel, in);
    }
    public void write() {
        answer = app.write(channel, out);
    }

}

