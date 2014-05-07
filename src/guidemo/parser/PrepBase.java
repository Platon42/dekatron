package guidemo.parser;

/**
 * Created by John on 14.04.14.
 */
public class PrepBase {

    //16,К02-121,Хабибулин,Ринат,Ильдарович,общ
    //17,К02-121,Чиркин,Андрей,Владимирович,

    String group;
    String lastName;
    String name;
    String patronymic;

    @Override
    public String toString() {
        return ("группа: " + group + ", имя: " + name + ", фамилия: " + lastName + ", отечество: " + patronymic); //should be cool
    }

    public String getInitials() {
        String out;
        out = lastName.toUpperCase() + " " + name.charAt(0) + ".";
        if (!patronymic.isEmpty()) {
            out += patronymic.charAt(0) + ".";
        }
        return out;
    }

}
