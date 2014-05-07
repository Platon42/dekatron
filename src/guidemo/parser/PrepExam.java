package guidemo.parser;

/**
 * Created by John on 08.04.14.
 */
public class PrepExam {
    int number;
    String fio;
    String comment;
    String type;
    String raw;
    int rating;
    int fail;
    String symRating;
    String group;
    int semestr;

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        out.append("number: ").append(number).append(" fio: ").append(fio).append(" comment: ").append(comment)
                .append(" type: ").append(type).append(" rating: ").append(rating).append(" fail: ").append(fail).append(" raw: ").append(raw).append(" group: ").append(group)
                .append(" semestr: ").append(semestr);

        return out.toString();
    }

}
