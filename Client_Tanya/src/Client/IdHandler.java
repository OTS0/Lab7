package Client;

import Exception.IdException;
import Exception.IndexNotFoundException;
import Exception.NullException;
import FromTask.HumanBeing;
import Server.Answer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class IdHandler {
    private Command commandManager;
    public IdHandler(){
    }

    public void setCommand(Command commandManager){
        this.commandManager = commandManager;
    }
    public void setStream(){
    }

    /**
     * Метод выбрасывает исключение, если элемент с таким id уже есть в коллекции
     * @param str
     * @throws IdException
     * @throws NumberFormatException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void checkId(LinkedList<HumanBeing> fromFile, String str, ObjectOutputStream out, ObjectInputStream in) throws IdException, NumberFormatException, IOException, ClassNotFoundException {
        int id = Integer.parseInt(str);
        Request request = new Request();
        request.setCommand("check id");
        request.setId(id);
        out.writeObject(request);
        Answer answer = (Answer)in.readObject();
        if (answer.getCheck()){
            System.out.println("Ошибка");
            throw new IdException();
        }
        for (HumanBeing human: fromFile){
            if (human.getId() == id){
                throw new IdException();
            }
        }

    }
    /**
     * Метод для поиска индекса элемента коллекции с данным id
     * @param id
     * @return
     * @throws NumberFormatException
     * @throws NullException
     * @throws IndexNotFoundException
     */
    public  int findById(int id, ObjectOutputStream out, ObjectInputStream in) throws IndexNotFoundException, IOException, ClassNotFoundException {
        int index = -1;
        Request request = new Request();
        request.setCommand("find_by_id");
        request.setId(id);
        out.writeObject(request);
        Answer answer = (Answer)in.readObject();
        index = answer.getIndex();
        if (index == -1){
            throw new IndexNotFoundException();
        }
        return index;
    }


    /**
     *
     * @param collection
     * @return
     * @throws IdException
     */
    public  int findGoodId(LinkedList<HumanBeing> collection) {
        int num = 0;
        Boolean check;
        while (true) {
            check = true;
            for (HumanBeing human : collection) {
                Integer id = human.getId();
                if (num == id) {
                    check = false;
                    break;
                }
            }
            if (check){
                break;
            }
            else {
                num = num + 1;
            }
        }
        return num;
    }

}
