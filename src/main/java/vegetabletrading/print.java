/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vegetabletrading;

import java.text.MessageFormat;
import javax.print.attribute.*;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;

import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author HARSHIT
 */
public class print {             
    
    public static void printtable(JTable t){
        
        MessageFormat header = new MessageFormat("STOCK");
        MessageFormat footer = new MessageFormat("ParabitVMS");
        try{
            PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
            set.add(OrientationRequested.LANDSCAPE);
            set.add(MediaSize.findMedia(1000, 100, 2));
            t.print(JTable.PrintMode.FIT_WIDTH, header, footer,true,set,false);                        
            
            JOptionPane.showMessageDialog(null, "Printed Sucessfully");
        } catch (Exception e) {
           System.out.println(e);
        }
    }
}
