package Server;

import Client.Request;
import Exception.*;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
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

    /**
     * implementation of the thread that handles the request
     */
    public void work() {
        try {
            app.start(channel);
        }catch (IdException | NullException | IOException n) {
            n.printStackTrace();
        }
    }

    /**
     * implementation of the thread reading the request
     */
    public void read() {
        request = app.read(channel, in);
    }

    /**
     * implementation of the thread that outputs the response
     */
    public void write() {
        answer = app.write(channel, out);
    }

}

