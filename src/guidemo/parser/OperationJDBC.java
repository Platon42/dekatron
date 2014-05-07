/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guidemo.parser;

import static guidemo.LoginDB.getConnectionDB;
import static guidemo.parser.Parse.parseExam;
import static guidemo.parser.Parse.parseHalfSem;
import static guidemo.parser.Parse.parseStartBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Максим
 */
public class OperationJDBC {

    public static void insertPolusemRecord(String FileName) throws SQLException {

        Connection dbConnection = null;
        dbConnection = getConnectionDB();
        PreparedStatement preparedStatementPolusemControl = null;
        ArrayList<PrepHalf> a = parseHalfSem(FileName);

        String insertPolusem = "SELECT merge_polusemcontrol"
                //+ "(nom, sred_ozenka, kolvo_fail, nomer_semestra, polusem_raw, name_of_student, name_of_group)"
                + "(?,?,?,?,?,?,?)";

        try {
            for (PrepHalf half : a) {
                preparedStatementPolusemControl = dbConnection.prepareStatement(insertPolusem);
                preparedStatementPolusemControl.setInt(1, half.number);
                preparedStatementPolusemControl.setDouble(2, half.average);
                preparedStatementPolusemControl.setInt(3, half.fail);
                preparedStatementPolusemControl.setInt(4, half.semestr);
                preparedStatementPolusemControl.setString(5, half.disciplineRaw);
                preparedStatementPolusemControl.setString(6, half.fio);
                preparedStatementPolusemControl.setString(7, half.group);

                preparedStatementPolusemControl.execute();                //Beam UP!
                //Beam UP!
                System.out.println("Record's is inserted into 'student' and 'polusem_control' table");

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if ((preparedStatementPolusemControl != null)) {
                preparedStatementPolusemControl.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }

    }

    public static void insertExamRecord(String FileName) throws SQLException {

        Connection dbConnection = null;
        dbConnection = getConnectionDB();
        PreparedStatement preparedStatementExamControl = null;
        ArrayList<PrepExam> a = parseExam(FileName);
        String insertExam = "INSERT INTO exam_control"
                + "(nom, name_of_student, type, kolvo_fail, exam_raw, rating, sym_rating, comment) VALUES"
                + "(?,?,?,?,?,?,?,?)";

        try {
            for (PrepExam exam : a) {
                preparedStatementExamControl = dbConnection.prepareStatement(insertExam);
                preparedStatementExamControl.setInt(1, exam.number);
                preparedStatementExamControl.setString(2, exam.fio);
                preparedStatementExamControl.setString(3, exam.type);
                preparedStatementExamControl.setInt(4, exam.fail);
                preparedStatementExamControl.setString(5, exam.raw);
                preparedStatementExamControl.setInt(6, exam.rating);
                preparedStatementExamControl.setString(7, exam.symRating);
                preparedStatementExamControl.setString(8, exam.comment);

                preparedStatementExamControl.executeUpdate();                //Beam UP!                //Beam UP!
                System.out.println("Record's is inserted into 'examen_control' table");

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatementExamControl != null) {
                preparedStatementExamControl.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    public static void insertTempStudentRecord() throws SQLException {

        Connection dbConnection = null;
        dbConnection = getConnectionDB();
        PreparedStatement preparedStatementStudentTemp = null;
        ArrayList<PrepBase> a = parseStartBase("C:\\Users\\Максим\\Dropbox\\Дипломный проект\\Данные БД Деканата\\csvbase.csv");

        String insertStudentTemp = "INSERT INTO student_temp"
                + "(name_of_student, name_of_group, first_name, last_name, patronymic_name ) VALUES"
                + "(?,?,?,?,?)";
        try {
            for (PrepBase base : a) {
                preparedStatementStudentTemp = dbConnection.prepareStatement(insertStudentTemp);
                preparedStatementStudentTemp.setString(1, base.getInitials());
                preparedStatementStudentTemp.setString(2, base.group);
                preparedStatementStudentTemp.setString(3, base.name);
                preparedStatementStudentTemp.setString(4, base.lastName);
                preparedStatementStudentTemp.setString(5, base.patronymic);

                preparedStatementStudentTemp.executeUpdate();
                System.out.println("Record's is inserted into 'student_temp' table");//Beam UP!

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if ((preparedStatementStudentTemp != null)) {
                preparedStatementStudentTemp.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    public static void createStudentDir(String login, char[] password) throws SQLException, Exception {

        Connection dbConnection = null;
        dbConnection = getConnectionDB();
        Statement StatementStudentDirs = null;

        String SelectStudentForDirs = "SELECT student.nom FROM student";

        try {
            StatementStudentDirs = dbConnection.createStatement();
            ResultSet rs = StatementStudentDirs.executeQuery(SelectStudentForDirs);
            SmbFunction smb = new SmbFunction();
            smb.login(login, password);
            while (rs.next()) {

                String dirname = rs.getString("nom");
                smb.createDir(dirname, "");

                System.out.println("dirname : " + dirname);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        //finally {

        //  if (StatementStudentDirs != null) {
        //     StatementStudentDirs.close();
        //}
        //if (dbConnection != null) {
        //   dbConnection.close();
        //}
        //}
    }

    /**
     *
     * @param lastname
     * @return
     * @throws SQLException
     */
    public static ArrayList<Object[]> selectStudentFromLastname(String lastname) throws SQLException {

        Connection dbConnection = null;
        dbConnection = getConnectionDB();
        PreparedStatement preparedStatementStudent = null;
        ArrayList<Object[]> result = new ArrayList<>();
        String StudentInfoFromLastName = "SELECT nom, last_name, current_group FROM student WHERE student.last_name ^?";
              //^ lol

        try {
            //Beam UP!
            preparedStatementStudent = dbConnection.prepareStatement(StudentInfoFromLastName);
            preparedStatementStudent.setObject(1, lastname);
            ResultSet rs = preparedStatementStudent.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);

                }
                result.add(row);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatementStudent != null) {
                preparedStatementStudent.close();

            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return result;

    }

     public static ArrayList<Object[]> selectStudentFromNom(int nom) throws SQLException {

        Connection dbConnection = null;
        dbConnection = getConnectionDB();
        PreparedStatement preparedStatementStudent = null;
        ArrayList<Object[]> result = new ArrayList<>();
        String StudentInfoFromLastName = "SELECT " +
                        "  student.nomer_tel, " +
                        "  student.email, " +
                        "  student.data_rozden, " +
                        "  student.obshhezhitie, " +
                        "  student.lgota, " +
                        "  polusem_control.sred_ozenka, " +
                        "  polusem_control.kolvo_fail, " +
                        "  polusem_control.nomer_semestra, " +
                        "  polusem_control.polusem_raw, " +
                        "  polusem_control.name_of_group " +
                        "FROM public.student, public.polusem_control " +
                        "WHERE student.nom = ? AND polusem_control.nom = ?;";


        try {
            //Beam UP!
            preparedStatementStudent = dbConnection.prepareStatement(StudentInfoFromLastName);
            preparedStatementStudent.setObject(1, nom);
            preparedStatementStudent.setObject(2, nom);
            ResultSet rs = preparedStatementStudent.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);

                }
                result.add(row);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatementStudent != null) {
                preparedStatementStudent.close();

            }

            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return result;

    }
    
   
    public static String deleteBreckets(String in) {
        String result = in.substring(0, in.length());

        return result;
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());

    }

}
