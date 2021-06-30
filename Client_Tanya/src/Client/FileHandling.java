package Client;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import Exception.*;
import FromTask.HumanBeing;
import Server.Answer;

public abstract class FileHandling extends Object{
    /**
     * get command from file for command execute_script
     * @param file
     * @param in
     * @param out
     * @param app
     * @return
     * @throws IOException
     * @throws StackOverflowError
     */
    public static Boolean getFileCommand(File file, ObjectInputStream in, ObjectOutputStream out, App app) throws IOException, StackOverflowError {
        Boolean exit = false;
        try {
            String str = "";
            if (!Command.checkFile(file, true)) {
                Scanner scanner = new Scanner(file);
                while ((str != null) && (scanner.hasNextLine())) {
                    System.out.println("CТРОКА " + str);
                    try {
                        str = app.communicate(in, out, scanner, false);
                    } catch (NullPointerException n){
                        str = "exit";
                    }
                    if (equalsPart(str, "exit")) {
                        exit = true;
                        break;
                    }
                }
                scanner.close();
            }
        } catch ( ClassNotFoundException | CollectionException c){
            System.out.println("The script ended with an error. ");
        } catch (FileNotFoundException f){
            System.out.println("Wrong file path. Script isn't executed");
        }
        return exit;
    }

    public static void read(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
            Request request = new Request();
            request.setCommand("read");
            out.writeObject((java.lang.Object)request);
            Answer answer = (Answer) in.readObject();
            System.out.println(answer.getAnswer());
    }

    /**
     * @param request
     * @param in
     * @param out
     * @param app
     * @param str
     * @return
     * @throws CollectionException
     * @throws IOException
     * @throws StackOverflowError
     */
    public static String communicate(Request request, ObjectInputStream in, ObjectOutputStream out, App app, String str) throws CollectionException, IOException, StackOverflowError {
        try {
            if (equals(request.getCommand(), "script_exception")) {
                throw new CollectionException("");
            } else {
                Request reg = request;
                out.writeObject(reg);
            }
            if (equals(request.getCommand(), "execute_script")) {
                File file = Command.executeScript(trimString(str, "execute_script"));
                if (file != null)
                {
                    Boolean exit;
                    exit = FileHandling.getFileCommand(file, in, out, app);
                    if (exit) {
                        str = null;
                    }
                }

            }
        } catch (StackOverflowError s){
           return "Too many requests! This is not good. Puff up your behavior. ";
        }
        return str;
    }

    /**
     * command exit
     * @param out
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void exit(ObjectOutputStream out) throws IOException, ClassNotFoundException{

        System.out.println("");
        Request request = new Request();
        request.setCommand("exit");
        out.writeObject(request);
    }
}
