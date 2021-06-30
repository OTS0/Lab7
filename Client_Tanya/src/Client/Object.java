package Client;
import FromTask.*;

import java.util.LinkedList;

public abstract class Object {
    /**
     * сравнивает строки с учетом возможных ошибок (регистр, пробелы)
     * @param str1 - сравниваемая строка
     * @param str2 - сравниваемая строка
     * @return check = true, если строки равны,  false - если не равны / хотя бы одна из них null
     */
    public static boolean looseEquals(String str1, String str2) {
        str1 = str1.trim();
        str2 = str2.trim();
        if (equals(str1, str2)) {
            return true;
        } else {
            boolean check = true;
            if (str2 == null || str1 == null) {
                check = false;
            } else if (str1.length() != str2.length()) {
                check = false;
            } else if (!str1.regionMatches(true, 0, str2, 0, str2.length())) {
                check = false;
            }
            return check;
        }
    }
    /**
     * сравнивает, совпадает ли начальная часть введенной строки с командой
     * @param str - строка, содержащая команду
     * @param norm - команда
     * @return true, если содержит, false, если нет
     */
    public static boolean equalsPart(String str, String norm) {
        str = str.trim();
        boolean check = true;
        if (str.length() >= norm.length()) {
            for (int i = 0; i <= norm.length() - 1; i++) {
                if (!looseEquals(str.substring(i, i + 1), norm.substring(i, i + 1))) {
                    check = false;
                    break;
                }
            }
        } else {
            check = false;
        }
        return check;
    }

    /**
     * удаляет из первой строки вторую
     * @param trimmed - обрезаемая строка
     * @param norm - то, что мы хотим удалить из trimmed
     * @return обрезанную строку
     */
    public static String trimString(String trimmed, String norm) {
        trimmed=trimmed.trim();
        int index = -1;
        if (!equals(trimmed, "") && (trimmed.length() >= norm.length())) {
            for (int i = 0; i < norm.length(); i++) {
                if (looseEquals(norm.substring(i, i + 1), trimmed.substring(i, i + 1))) {
                    index = i;
                }
            }
            trimmed = trimmed.substring(index + 1);
            trimmed = trimmed.trim();
        }
        return trimmed;
    }

    /**
     * выдаем элемент колелкции в строковом представлении
     * @param human - элемент класса HumanBeing
     * @return стоку, содержащую информацию о полях объекта
     */
    public static String toString(HumanBeing human) {
        return human.getName() + " \n   id: " + human.getId() +
                ", \n   x: " + human.getCoordx() +
                ", \n   y: " + human.getCoordx() +
                ", \n   realHero: " + human.getRealHero() +
                ", \n   hasToothpick: " + human.getHasToothpick() +
                ", \n   impactSpeed: " + human.getImpactSpeed() +
                ", \n   soundtrackName: " + human.getSoundtrackName() +
                ", \n   weaponType: " + human.getWeaponType() +
                ", \n   mood: " + human.getMood() +
                ", \n   carName: " + human.getCarName() +
                ", \n   carCool: " + human.getCarCool();
    }



    /** сравниваем две строки строго
     * Строгое сравнение двух строк, не допускающее лишних пробелов, различий в регистре
     * @param obj1 - сравниваемая строка
     * @param obj2 - сравниваемая строка
     * @return true, если строки равны, false, если нет или хоть одна строка null
     */
    public static boolean equals(String obj1, String obj2) {
        boolean check = true;
        if (obj2 == null || obj1 == null) {
            check = false;
        }
        if (obj1.length() != obj2.length()) {
            check = false;
        } else {
            for (int j = 0; j < obj2.length(); j++) {
                if (obj1.charAt(j) != obj2.charAt(j)) {
                    check = false;
                }
            }
        }
        return check;
    }
}
