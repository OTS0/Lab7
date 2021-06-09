package Server;

import FromTask.HumanBeing;
import FromTask.*;
import Exception.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedList;

public class IdHandler {

    public void checkId(LinkedList<HumanBeing> collection, String str) throws IdException{
        int id = Integer.parseInt(str);
        if (id < 0) {
            throw new IdException();
        }
        for (HumanBeing human: collection){
            if (human.getId() == id){
                throw new IdException();
            }
        }
    }
    /**
     * Метод,
     * @param collection
     * @param num
     * @return
     * @throws NumberFormatException
     */
    public int findById(LinkedList<FromTask.HumanBeing> collection, int num) {
        int index = -1;
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId() == num) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Проверка, есть ли в коллекции элемент в данным id
     * @param collection
     * @param id
     * @return
     * @throws NullException
     */

    public  boolean existId(LinkedList<HumanBeing> collection, int id) {
        try {
            checkId(collection, id + "");
            return false;
        } catch (IdException i) {
            return true;
        }
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
