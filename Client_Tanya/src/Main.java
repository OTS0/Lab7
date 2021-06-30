/**
 * @author Ilyakova Maria
 * Главный класс, в котором мы просто запускаем приложение
 */
import java.io.IOException;

import Client.Client;
import Exception.*;

public class Main {
    /**@author Ilyakova Maria and OTS
     * @param args
     */
    public static void main(String[] args) throws IOException{
        Client client = new Client();
        client.workClient();
    }
}
