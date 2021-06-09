/**
 * @author i'MAXA
 * Enum, содержащий возможные значения поля weaponType объекта класса HumanBeing
 */
package FromTask;

import java.io.Serializable;

public enum WeaponType implements Serializable {

    SHOTGUN("SHOTGUN"),
    RIFLE("SHOTGUN"),
    KNIFE("SHOTGUN"),
    MACHINE_GUN("SHOTGUN");

    private String type;


    WeaponType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
