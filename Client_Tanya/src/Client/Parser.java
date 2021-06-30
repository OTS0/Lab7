package Client;

import Exception.*;
import FromTask.HumanBeing;
import Server.Answer;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Класс Parcer для прочтения файла
 */
public class Parser {
IdHandler idHandler;
Command commandManager;
LinkedList<HumanBeing> humanFromFile = new LinkedList<>();
public Parser(Command commandManager, IdHandler idHandler){
    this.commandManager = commandManager;
    this.idHandler = idHandler;
}
    /**
     * метод для получения значения поля из json файла
     *
     * @param json   - строка из файла json
     * @param trim   - поле, значение которого содержится в строке
     * @param quotes - есть ли кавычки (то есть значение поля не числовое)
     * @return преобразованную строку, содержащую лишь значение поля
     * @throws NullException - если значение поля равно пустой строке
     */
    public  String trimJson(String json, String trim, Boolean quotes) throws NullException {
        json = StringHandler.trimString(json, trim);
        if (StringHandler.equals(json, "")) {
            throw new NullException("");
        }
        json = json.trim();
        if (StringHandler.looseEquals(json.substring(json.length() - 1), ",")) {
            if (quotes) {
                json = json.substring(1, json.length() - 2);
            } else {
                json = json.substring(0, json.length() - 1);
            }
        } else if (quotes) {
            json = json.substring(1, json.length() - 1);
        }
        json = json.trim();
        json = json.substring(1);
        json = json.trim();
        json = json.substring(1);
        json = json.trim();
        return json;
    }



    /**
     * Присваивание значения определенному полю элемента коллекции, найденному по id
     * Использую шаблон FactoryMethod
     *
     * @param str        - строка, содержащая значение поля
     * @param type       - название поля, которому хотим присвоить значение
     * @throws NullException          - если str является пустой строкой
     * @throws IndexNotFoundException - если не существует элемента коллекции с таким id
     */
    public  void setNames(HumanBeing human, String str, String type) throws NullException, IndexNotFoundException {
        if (StringHandler.looseEquals(type, "name")) {
            human.setName(str);
        } else if (StringHandler.looseEquals(type, "soundtrackName")) {
            human.setSoundtrackName(str);
        } else if (StringHandler.looseEquals(type, "carName")) {
            human.setCarName(str);
        }
    }

    /**
     * определяем поля, имеющие числовое значение
     * Использую шаблон FactoryMethod
     *
     * @param str        - строка, содержащая значение поля
     * @param type       - название поля, которому хотим присвоить значение
     * @throws NullException          - если str является пустой строкой
     * @throws IndexNotFoundException - если str нельзя преобразовать в число, если не найдем элемент с таким id
     * @throws SizeException          - если значение str выходит за рамки допустимых значений поля
     */
    public  void setNumber(HumanBeing human, String str, String type) throws NullException, IndexNotFoundException, SizeException {
        if (StringHandler.looseEquals(type, "x")) {
            human.setCoordx(Integer.parseInt(str));
        } else if (StringHandler.looseEquals(type, "y")) {
            human.setCoordy(Float.parseFloat(str));
        } else if (StringHandler.looseEquals(type, "impactSpeed")) {
            int impactSpeed = Integer.parseInt(str);
            if (impactSpeed > 967) {
                throw new SizeException(967);
            }
            if (StringHandler.equals(str, "")) {
                throw new NullException("impactSpeed");
            }
            human.setImpactSpeed(impactSpeed);
        }
    }

    /**
     * Определяем id элемента
     * Использую шаблон FactoryMethod
     *
     * @param str        - строка, содержащая значение поля
     * @throws IndexNotFoundException - если не найдем элемент с таким id
     * @throws IdException            - если значение id не является уникальным (т.к. чтение производится из файла)
     * @throws NumberFormatException  - если значение str выходит за рамки допустимых значений поля
     */
    public void setId(HumanBeing human, String str) throws NumberFormatException {
        int id = Integer.parseInt(str);
        human.setId(id);
    }

    /**
     * Определяем поля элемента, имеющие логическое значение
     *
     * @param str - строка, содержащая значение поля
     * @param type - имя поля, которому хотим присвоить значение
     * @throws TypeException - если str не может быть преобразована в тип, соответствующий полю
     */

    public void setBoolean(HumanBeing human, String str, String type) throws TypeException {
        boolean var;
        if (StringHandler.looseEquals(str, "true")) {
            var = true;
        } else if (StringHandler.looseEquals(str, "false")) {
            var = false;
        } else {
            throw new TypeException("");
        }
        if (StringHandler.looseEquals(type, "RealHero")) {
            human.setRealHero(var);
        } else if (StringHandler.looseEquals(type, "HasToothpick")) {
            human.setHasToothpick(var);
        } else if (StringHandler.looseEquals(type, "carCool")) {
            human.setCarCool(var);
        }
    }

    /**
     * Определяем значение поля weaponType
     *
     * @param str        - строка, содержащая значение поля
     * @throws TypeException          - если str не может быть преобразована в тип, соответствующий полю
     * @throws NullException          - если str пустая
     */
    public  void setWeaponType(HumanBeing human, String str) throws NullException, TypeException {
        human.setWeaponType(commandManager.getWeapon(str));
    }

    /**
     * Определяем значение поля mood
     *
     * @param str        - строка, содержащая значение поля
     * @throws TypeException          - если str не может быть преобразована в тип, соответствующий полю
     * @throws NullException          - если str пустая
     */
    public  void setMoodType(HumanBeing human, String str) throws NullException, TypeException {
        human.setMood(commandManager.getMood(str));
    }


    /**
     * Определяем, какому полю нужно присвоить значение (по str) и вызываем один из методов, делающих это
     *
     * @param str - строка, содержащая значение поля и его имя
     * @return уникальное значение id элемента
     * @throws TypeException          - если str не может быть преобразована в тип, соответствующий полю
     * @throws NullException          - если str пустая
     * @throws IndexNotFoundException - если не найдем элемент с таким id
     * @throws IdException            - если значение id элемента не уникально
     * @throws TypeException          - если значение поля в str не может быть преобразовано в нужный тип (для enum)
     * @throws SizeException          - если значение поля в str выходит за рамки допустимого
     * @throws NumberFormatException  - если значение поля в str не может быть преобразовано в число (если это нужно)
     */
    public void setVar(HumanBeing human, String str, ObjectOutputStream out, ObjectInputStream in) throws NullException, IndexNotFoundException, IdException, TypeException, SizeException, NumberFormatException, IOException, ClassNotFoundException {
        if (StringHandler.equalsPart(str, "name")) {
            str = trimJson(str, "name", true);
            setNames(human, str, "name");

        } else if (StringHandler.equalsPart(str, "soundtrackName")) {
            str = trimJson(str, "soundtrackName", true);
            setNames(human, str, "soundtrackName");

        } else if (StringHandler.equalsPart(str, "carName")) {
            str = trimJson(str, "carName", true);
            setNames(human, str, "carName");

        } else if (StringHandler.equalsPart(str, "x")) {
            str = trimJson(str, "x", false);
            setNumber(human, str, "x");

        } else if (StringHandler.equalsPart(str, "y")) {
            str = trimJson(str, "y", false);
            setNumber(human, str, "y");

        } else if (StringHandler.equalsPart(str, "impactSpeed")) {
            str = trimJson(str, "impactSpeed", false);
            setNumber(human, str, "impactSpeed");

        } else if (StringHandler.equalsPart(str, "id")) {
            str = trimJson(str, "id", false);
            idHandler.checkId(humanFromFile, str, out, in);
            setId(human, str);
        } else if (StringHandler.equalsPart(str, "realHero")) {
            str = trimJson(str, "realHero", false);
            setBoolean(human, str, "realHero");

        } else if (StringHandler.equalsPart(str, "HasToothpick")) {
            str = trimJson(str, "HasToothpick", false);
            setBoolean(human, str, "HasToothpick");

        } else if (StringHandler.equalsPart(str, "carCool")) {
            str = trimJson(str, "carCool", false);
            setBoolean(human, str, "carCool");

        } else if (StringHandler.equalsPart(str, "weaponType")) {
            str = trimJson(str, "weaponType", true);
            setWeaponType(human, str);

        } else if (StringHandler.equalsPart(str, "mood")) {
            str = trimJson(str, "mood", true);
            setMoodType(human, str);
        }
    }

    /**
     * Метод дл прочтения файла
     *
     * @param path
     * @return
     */
    public  boolean read(String path, ObjectInputStream in, ObjectOutputStream out) throws ClassNotFoundException, IOException{
        String str;
        Boolean check = true;
        try {
            path = Command.findGoodFile(path, true);
            if (path == null) {
                throw new NullException();
            }
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                if (str.contains("{")) {
                    HumanBeing human = new HumanBeing();
                    str = "null";
                    while (!str.contains("}")) {
                        str = scanner.nextLine();
                        str = str.trim();
                        processHuman(human, str, out, in);
                    }
                    humanFromFile.add(human);
                }
            }
            for (HumanBeing human: humanFromFile) {
                Request request = new Request();
                request.setCommand("add");
                request.setHuman(human);
                request.setId(human.getId());
                out.writeObject(request);
                Answer answer = (Answer)in.readObject();
            }
            scanner.close();
            System.out.println("The file was read!");
        } catch (NullException n) {
            System.out.println("You have chosen not to download the collection. Accept the consequences: the collection is empty.");
        }catch (IndexNotFoundException | IdException | TypeException | SizeException | NumberFormatException | FileNotFoundException e) {
            System.out.println("An error was found in the file.");
            read("", in, out);
            check = false;
            humanFromFile.clear();
        }
        return check;
    }
    public void processHuman(HumanBeing human, String str, ObjectOutputStream out, ObjectInputStream in) throws IdException, SizeException, NullException, TypeException, IndexNotFoundException, IOException, ClassNotFoundException {
        if (!StringHandler.equals(str, "")) {
            str = str.trim();
            str = str.substring(1);
            setVar(human, str, out, in);
        }
    }

}