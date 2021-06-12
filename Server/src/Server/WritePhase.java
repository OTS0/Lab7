package Server;
import Exception.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveTask;

public class WritePhase extends RecursiveTask {
    App app;
    Answer answer = null;
    SocketChannel channel;
    ObjectOutputStream out;
    protected WritePhase(App app, SocketChannel channel) throws IOException {
        this.channel = channel;
        this.app = app;
        this.out = new ObjectOutputStream(Channels.newOutputStream(channel));
    }
    protected Answer compute(){
        System.out.println("метод compute");
        answer = app.write(channel, out);
        return answer;
    }
}
