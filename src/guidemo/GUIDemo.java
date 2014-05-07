/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guidemo;

import static java.lang.System.arraycopy;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import javax.swing.JFrame;

/**
 *
 * @author John
 */
public class GUIDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MainPanel MP = new MainPanel();
        MP.setVisible(true);
        MP.setExtendedState(JFrame.MAXIMIZED_BOTH);

        LoginFrame LF = new LoginFrame();
        LF.setVisible(true);

    }

    public static ArrayList<Object[]> getBase() {
        ArrayList<Object[]> out = new ArrayList<>();
        Object[][] mas = 
            {{145, "Валуев", "к4-124м"},
            {35352, "Песочкинов", "к4-124м"},
            {236, "Песчанников", "к4-124м"},
            {3535, "Песок", "к4-124м"},
             {444, "Нагатов", "к16-244"}};
                    
        out.addAll(asList(mas));
        
        return out;
    }

    public static Object[][] findStudent(String in) {
        Object[][] out = new Object[1][3];
        
        int i = 1;
        for (Object[] a : getBase()) {
            if (((String)a[1]).substring(0, in.length()).trim().toUpperCase().equals(in.trim().toUpperCase())) {
                Object[][] tempout = out;
                out = new Object[i][3];
                arraycopy(tempout, 0, out, 0, tempout.length);
                out[i-1] = a;
                i++;
            }
        }
        
        return out;
    }
    
}
