package guidemo.parser;

import java.io.*;
import java.util.*;

public class Parse {

    public static ArrayList<PrepExam> parseExam(String fileName) {

        ArrayList<String> all = new ArrayList<>();
        ArrayList<PrepExam> result;

        try {
            all = read(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Not found");
            return null;
        }

        all = eraseFirst(all);
        all = eraseFirst(all);
        all = eraseLast(all);
        result = getDataExam(all);

        return result;
    }

    static ArrayList<PrepHalf> getDataHalf(ArrayList<String> in) {
        ArrayList<PrepHalf> out = new ArrayList<>();

        PrepHalf prep = new PrepHalf();

        for (String cur : in) {
            StringTokenizer st = new StringTokenizer(cur, ",\n");

            /*
            String[] splitted = cur.split(",");

            System.out.println(cur + " have " + st.countTokens() + " or " + splitted.length + " tokens");*/
            
            
            switch (st.countTokens()) {
                
                
                case 8 :                                                        //220104,БУХАРОВ В.А.,,8,60 ,47,0,80,4.0
                    prep.number = Integer.parseInt(st.nextToken().trim());     
                    prep.fio = st.nextToken().trim();
                    prep.disciplineRaw = st.nextToken().trim();
                    st.nextToken();
                    prep.rating = st.nextToken().trim();
                    prep.fail = Integer.parseInt(st.nextToken().trim());
                    st.nextToken();
                    prep.average = Double.parseDouble(st.nextToken().trim());
                    out.add(prep);
                    prep = new PrepHalf();
                    break;

                case 9 :                                                          //220104,БУХАРОВ В.А.,Б1.ДВ1,9,50 ,42,0,80,4.0
                    prep.number = Integer.parseInt(st.nextToken().trim());
                    prep.fio = st.nextToken().trim();
                    st.nextToken();
                    prep.disciplineRaw = st.nextToken().trim();
                    st.nextToken();
                    prep.rating = st.nextToken().trim();
                    prep.fail = Integer.parseInt(st.nextToken().trim());
                    st.nextToken();
                    prep.average = Double.parseDouble(st.nextToken().trim());
                    out.add(prep);
                    prep = new PrepHalf();
                    break;

            }
        }

        return out;
    }

    static ArrayList<PrepExam> getDataExam(ArrayList<String> in) {
        ArrayList<PrepExam> out = new ArrayList<>();

        PrepExam prep = new PrepExam();

        for (String cur : in) {
            StringTokenizer st = new StringTokenizer(cur, ",\n");
            
            
            String[] splitted = cur.split(",");

            System.out.println(cur + " have " + st.countTokens() + " or " + splitted.length + " tokens");

            switch (st.countTokens()) {
                case 3:                             //1,121902,"АНДРЕЕВ П.А.
                    st.nextToken();
                    prep.number = Integer.parseInt(st.nextToken().trim());
                    prep.fio = st.nextToken().trim();
                    break;
                case 5:                             //",Экзамены,,30,Кратные интегралы и ряды,,6,
                    
                    
                    prep.comment = st.nextToken().trim();
                    prep.type = st.nextToken().trim();
                    st.nextToken();
                    prep.raw = st.nextToken().trim();
                    prep.rating = 0;

                    prep.fail = Integer.parseInt(st.nextToken().trim());

                    prep.symRating = "0";
                    out.add(prep);
                    prep = new PrepExam();
                    break;
                case 6:                             //",Экзамены,,30,Линейная алгебра,#1,6,

                    prep.comment = st.nextToken().trim();
                    prep.type = st.nextToken().trim();
                    st.nextToken();
                    prep.raw = st.nextToken().trim();

                    prep.rating = 0;
                    st.nextToken();

                    prep.fail = Integer.parseInt(st.nextToken().trim());

                    prep.symRating = "0";
                    out.add(prep);
                    prep = new PrepExam();
                    break;
                case 7:                             //",Зачеты,,15,Физическая культура,3,0,B

                    if (cur.contains("►")) {

                        if (cur.contains("#")) { //отч. 06.02.14",Зачеты,Б1.ДВ1,52,Культурология1,#1►н/а,9,

                            if (Character.isDigit(cur.charAt(cur.indexOf("►") + 1))) {
                                //",Экзамены,,12,Программирование (объектно-ори,#1►4,0,C

                                prep.comment = st.nextToken().trim();
                                prep.type = st.nextToken().trim();
                                st.nextToken();

                                prep.raw = st.nextToken().trim();

                                String str = st.nextToken();

                                prep.rating = Integer.parseInt(String.valueOf(str.charAt(str.indexOf("►") + 1)));

                                prep.fail = Integer.parseInt(st.nextToken().trim());
                                prep.symRating = st.nextToken().trim();

                                out.add(prep);
                                prep = new PrepExam();
                                break;

                            } else {

                                prep.comment = st.nextToken().trim();
                                prep.type = st.nextToken().trim();
                                st.nextToken();
                                st.nextToken();
                                prep.raw = st.nextToken().trim();
                                prep.rating = 0;
                                st.nextToken();
                                prep.fail = Integer.parseInt(st.nextToken().trim());
                                prep.symRating = "";

                                out.add(prep);
                                prep = new PrepExam();
                                break;

                            }

                        } else {        //",Экзамены,,12,Программирование (объектно-ори,н/а►4,0,D

                            prep.comment = st.nextToken().trim();
                            prep.type = st.nextToken().trim();
                            st.nextToken();
                            prep.raw = st.nextToken().trim();

                            String str = st.nextToken().trim();

                            prep.rating = Integer.parseInt(Character.toString(str.charAt(str.indexOf("►")+1)));

                            prep.fail = Integer.parseInt(st.nextToken().trim());
                            prep.symRating = st.nextToken().trim();
                            out.add(prep);
                            prep = new PrepExam();
                            break;

                        }
                    }

                    if (cur.contains("*")) {       // ",Зачеты,Б1.ДВ1,52,Культурология2,*,0,
                        prep.comment = st.nextToken().trim();
                        prep.type = st.nextToken().trim();
                        st.nextToken();
                        st.nextToken();
                        prep.raw = st.nextToken().trim();
                        
                        
                        prep.rating = 0;
                        st.nextToken();
                        prep.fail = Integer.parseInt(st.nextToken().trim());
                        prep.symRating = "0";
                        out.add(prep);
                        prep = new PrepExam();
                        break;
                    }
                    
                    
                    if (cur.contains("#")) {       // ",Зачеты,,08,"Электротехника, электроника и ",#1,6,
                        prep.comment = st.nextToken().trim();
                        prep.type = st.nextToken().trim();
                        st.nextToken();
                        st.nextToken();
                        prep.raw = st.nextToken().trim();
                        
                        
                        prep.rating = 0;
                        st.nextToken();
                        prep.fail = Integer.parseInt(st.nextToken().trim());
                        prep.symRating = "0";
                        out.add(prep);
                        prep = new PrepExam();
                        break;
                    }
                    
                    
                    prep.comment = st.nextToken().trim();
                    prep.type = st.nextToken().trim();
                    st.nextToken();
                    prep.raw = st.nextToken().trim();
                    prep.rating = Integer.parseInt(st.nextToken().trim());
                    prep.fail = Integer.parseInt(st.nextToken().trim());
                    prep.symRating = st.nextToken().trim();
                    out.add(prep);
                    prep = new PrepExam();
                    break;
                case 8:                     //",Зачеты,,08,"Электротехника, электроника и ",3,0,A

                    prep.comment = st.nextToken().trim();
                    prep.type = st.nextToken().trim();
                    st.nextToken();

                    prep.raw = st.nextToken().trim();
                    prep.raw += st.nextToken();

                    prep.rating = Integer.parseInt(st.nextToken().trim());
                    prep.fail = Integer.parseInt(st.nextToken().trim());
                    prep.symRating = st.nextToken().trim();
                    out.add(prep);
                    prep = new PrepExam();
                    break;
            }
        }

        return out;
    }

    public static ArrayList<PrepHalf> parseHalfSem(String fileName) {

        ArrayList<String> all;

        try {
            all = read(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Not found");
            return null;
        }

        String group = getGroup(all);
        int semestr = getSem(group);

        all = eraseFirst(all);
        ArrayList<String> last = eraseFirst(all);
        last = eraseLast(last);
        ArrayList<DisIndex> discipline = getDis(last);
        all = eraseLast(all);


        ArrayList<PrepHalf> result = getDataHalf(all);



        result = changeDis(result, discipline);
        result = trimHalf(result);
        result = getUniq(result);
        result = addStatic(result, group, semestr);
        return result;
    }

    private static ArrayList<PrepHalf> addStatic(ArrayList<PrepHalf> result, String group, int semestr) {
        for (PrepHalf a : result) {
            a.group = group;
            a.semestr = semestr;
        }
        return result;
    }

    static int getSem(String group) {
        return Integer.parseInt(Character.toString(group.charAt(2)));
    }

    static String getGroup(ArrayList<String> in) {
        String out = "none";
        for (String a : in) {
            if (a.contains("Группа")) {
                out = a.substring((a.indexOf("Группа") + 6), a.indexOf("\""));
            }
        }

        return out.trim();
    }

    static ArrayList<String> eraseFirst(ArrayList<String> in) {
        List<String> out;
        ArrayList<String> real = new ArrayList<>();
        out = in.subList(in.indexOf("") + 2, in.size());

        real.addAll(out);
        return real;
    }

    static ArrayList<String> eraseLast(ArrayList<String> in) {
        List<String> out;
        ArrayList<String> real = new ArrayList<>();

        out = in.subList(0, in.indexOf(""));

        real.addAll(out);
        return real;
    }

    static ArrayList<PrepHalf> changeDis(ArrayList<PrepHalf> prep, ArrayList<DisIndex> dis) {

        for (PrepHalf prep1 : prep) {
            prep1.disciplineRaw = dis.get(Integer.parseInt(prep1.disciplineRaw) - 1).Dis;
        }

        return prep;
    }

    static ArrayList<PrepExam> trimExam(ArrayList<PrepExam> in) {

        ArrayList<Integer> nums = new ArrayList<>();

        ArrayList<PrepExam> out = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        PrepExam buf = new PrepExam();

        //number: 120318 fio: "ЮРОВСКИЙ П.С. comment: " type: Зачеты rating: 0
        // fail: 6 raw: 52 group: null semestr: 0

        //number: 120318 fio: "ЮРОВСКИЙ П.С. comment: " type: Зачеты rating: 0
        // fail: 6 raw: 52 group: null semestr: 0

        //number: 120318 fio: "ЮРОВСКИЙ П.С. comment: " type: Экзамены rating: 0
        // fail: 6 raw: Общ. физ.:электрич. и магнет. group: null semestr: 0

        //number: 120318 fio: "ЮРОВСКИЙ П.С. comment: " type: Экзамены rating: 0
        // fail: 6 raw: Программирование (объектно-ори group: null semestr: 0

        for (int i = 0; i < in.size(); i++) {
            PrepExam a = in.get(i);

            if (!nums.contains(a.number)) {
                nums.add(a.number);
                buf.number = a.number;
                buf.fio = a.fio;
                buf.comment = a.comment;
                buf.fail = a.fail;

            }

            builder.append(a.raw);

            if ((in.size()<i+1) && (a.number != in.get(i+1).number)) {

            }

        }

        return out;
    }

    static ArrayList<PrepHalf> trimHalf(ArrayList<PrepHalf> in) {

        ArrayList<String> str = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        int i = 0;
        while (i < in.size()) {                                   //get RAW

            int checkCurrentNumber = in.get(i).number;

            while (in.get(i).number == checkCurrentNumber && i < in.size() - 1) {

                builder.append(in.get(i).disciplineRaw).append(", Оценка: ").append(in.get(i).rating).append("\n");
                i++;
                if (i == in.size() - 1)
                    builder.append(in.get(i).disciplineRaw).append(", Оценка: ").append(in.get(i).rating).append("\n"); //coz you'll lose last discipline, lol

            }
            str.add(builder.toString());
            builder.setLength(0);
            if (i == in.size() - 1) i++;
        }


        int j = 0;
        i = 0;
        while (i < in.size()) {                                     //put RAW

            int checkCurrentNumber = in.get(i).number;

            while (in.get(i).number == checkCurrentNumber && i < in.size() - 1) {
                in.get(i).rating = "";
                in.get(i).disciplineRaw = str.get(j);
                i++;

            }
            j++;
            if (i == in.size() - 1) i++;
            ;
        }

        return in;
    }

    static ArrayList<PrepHalf> getUniq(ArrayList<PrepHalf> in) {

        ArrayList<PrepHalf> out = new ArrayList<>();

        for (int i = 0; i < in.size(); i++) {
            int checkCurrentNumber = in.get(i).number;

            out.add(in.get(i));

            while (in.get(i).number == checkCurrentNumber && i < in.size() - 1) {
                i++;
            }
        }
        return out;
    }

    static ArrayList<DisIndex> getDis(ArrayList<String> in) {

        ArrayList<DisIndex> dat = new ArrayList<>();

        for (String cur : in) {
            DisIndex dis = new DisIndex();

            StringTokenizer st = new StringTokenizer(cur, "\n,");

            dis.index = Integer.parseInt(st.nextToken().trim());
            st.nextToken();
            dis.Dis = st.nextToken();
            
            
            dat.add(dis);
        }

        return dat;
    }



    static ArrayList<String> read(String fileName) throws FileNotFoundException {

        File file = new File(fileName);

        ArrayList<String> all = new ArrayList<>();

        try {
            try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                String s;
                while ((s = in.readLine()) != null) {
                    all.add(s);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return all;
    }

    public static ArrayList<PrepBase> parseStartBase(String fileName) {

        ArrayList<String> all;
        ArrayList<PrepBase> result = new ArrayList<>();

        try {
            all = read(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Not found");
            return null;
        }

        PrepBase buf = new PrepBase();

        for (String cur : all) {
            StringTokenizer st = new StringTokenizer(cur, ",");

            //16,К02-121,Хабибулин,Ринат,Ильдарович,общ
            //17,К02-121,Чиркин,Андрей,Владимирович,

            if (st.countTokens() > 0) {

                st.nextToken();
                buf.group = st.nextToken().trim();
                buf.lastName = st.nextToken().trim();
                buf.name = st.nextToken().trim();
                try {
                    buf.patronymic = st.nextToken().trim();
                } catch (NoSuchElementException e) {
                    buf.patronymic = "";
                }

                result.add(buf);
                buf = new PrepBase();
            }

        }

        return result;
    }
}
