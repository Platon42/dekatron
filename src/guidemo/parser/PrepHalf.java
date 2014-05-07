package guidemo.parser;

/**
 * Created by apl20_000 on 27.03.14.
 * мар 2014
 * <p/>
 * untitled1
 */
public class PrepHalf {

    int number;
    String group;
    int semestr;
    String fio;
    int fail;
    double average;
    String disciplineRaw;
    String rating;


    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(number).append(fio).append(disciplineRaw); //можно сочно сделать
        return out.toString();
    }
}