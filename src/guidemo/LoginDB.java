/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guidemo;

import static guidemo.MainPanel.PasswordMsg;
import static java.lang.Class.forName;
import java.sql.Connection;
import static java.sql.DriverManager.getConnection;
import java.sql.SQLException;


/**
 *
 * @author apl20_000
 */
public class LoginDB {

    private static String DB_USER;
    private static String DB_PASSWORD;
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://109.120.173.61:5432/dekanat?"
            + "ssl=true&"
            + "sslfactory=org.postgresql.ssl.NonValidatingFactory";


    public static void OpenDB(String login, char[] password) {

        String sh = new String(password);
        DB_PASSWORD = sh;
        DB_USER = login;
        Connection dbConnection = null;

        try {
            forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("try to login");
            dbConnection = getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            PasswordMsg("Welcome" + " " + DB_USER);

        } catch (SQLException e) {

            PasswordMsg(e.getMessage());
            System.out.println(e.getMessage());

        }

    }


    public static Connection getConnectionDB() {

        Connection dbConnection = null;

        try {

            forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {
            dbConnection = getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            PasswordMsg(e.getMessage());
            //System.out.println(e.getMessage());

        }

        return dbConnection;

    }
}
