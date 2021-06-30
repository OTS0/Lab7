package Server;

import FromTask.HumanBeing;
import FromTask.*;
import Exception.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.LinkedList;

/**
 * Class for work with id
 */
public class IdHandler {
    /**
     * check id, if there is such id or less than 0, then it generate new IdException
     * @param collection
     * @param str
     * @throws IdException
     */
    public void checkId(List<HumanBeing> collection, String str) throws IdException{
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
     * Find index for work with an element
     * @param collection
     * @param num
     * @return
     * @throws NumberFormatException
     */
    public static int findById(List<FromTask.HumanBeing> collection, int num) {
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

    public  boolean existId(List<HumanBeing> collection, int id) {
        try {
            checkId(collection, id + "");
            return false;
        } catch (IdException i) {
            return true;
        }
    }

    /**
     *find id for numbering of elements
     * @param collection
     * @return
     * @throws IdException
     */
    public static int findGoodId(List<HumanBeing> collection) {
        int num = 1;
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
