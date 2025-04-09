/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bidding;

import static java.lang.Thread.sleep;
import javax.swing.table.DefaultTableModel;
import vegetabletrading.ParabitDB;

/**
 *
 * @author HARSHIT
 */
public class LivePriceTable extends Thread {
    String query;
    int row; 
    DefaultTableModel model;
    ParabitDB db1;
    
    public LivePriceTable(String q, int i, DefaultTableModel m){
        query = q;
        row = i;
        model = m;
                                       
    }
    
    public void run(){ 
        //System.out.println(query);
        db1 = new ParabitDB();
        int i =1;
        while(i==1){ 
            try{              
                //System.out.println(query);
                db1.rs = db1.stm.executeQuery(query);
                db1.rs.next();
                int lprice = db1.rs.getInt(1);
                model.setValueAt(lprice, row, 9);
                sleep(100);  
                
            }catch(Exception e) {
                System.out.println(e);
            }
        }
    }
   
}
