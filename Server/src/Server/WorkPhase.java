package Server;
import Exception.*;

import java.util.concurrent.RecursiveTask;
import java.util.jar.JarEntry;

public class WorkPhase extends RecursiveTask {
    App app;
    protected WorkPhase(App app){
        this.app = app;
    }
    protected App compute(){
        try {
            System.out.println("метод compute");
            app.start();
        } catch (IdException | NullException n) {
            System.out.println("IdException | NullException");
            n.printStackTrace();
        } finally {
            return this.app;
        }
    }

}
