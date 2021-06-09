package Server; /**
 * @author Ilyakova Maria
 * Класс-компаратор, который реализует сортировку элементов коллекции по возрастанию id
 */
import FromTask.*;
import java.util.Comparator;

class Comparator3000 implements Comparator<HumanBeing> {
    /**
     * Определение большего и меньшего элемента коллекции по id
     * @param s1 - объект класса HumanBeing
     * @param s2 - объект класса HumanBeing
     * @return 0, если s1 > s2, -1, если s1 < s2, 1, если s1 = s2
     */
    public int compare(HumanBeing s1, HumanBeing s2) {
        if (s1.getCoordy() > (s2.getCoordy()))
            return 1;
        else if (s1.getCoordy() < (s2.getCoordy()))
            return -1;
        else return 0;
    }
}