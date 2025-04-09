/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import vegetabletrading.ParabitDB;

/**
 *
 * @author HARSHIT
 */
public class CurrentDate {
        String date = null;
        ParabitDB db1;
    
        CurrentDate(){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now();  
            date = (dtf.format(now));
            
            String q1 = "UPDATE `wb23` SET `RegDate`='"+date+"' WHERE 1";
            String q2 = "UPDATE `dailyproduction` SET `RegDate`='"+date+"' WHERE 1";
            String q3 = "UPDATE `dp23` SET `RegDate`='"+date+"' WHERE 1";
            String q4 = "UPDATE `mst23` SET `RegDate`='"+date+"' WHERE 1"; 
           // String q5 = "UPDATE `bidstart` SET `BidONDate`='"+date+"' WHERE 1";
           String q6 = "UPDATE `pst23` SET `RegDate`='"+date+"' WHERE 1";
            
            db1 = new ParabitDB();
            try
            {
                db1.stm.executeUpdate(q1);
                db1.stm.executeUpdate(q2);
                db1.stm.executeUpdate(q3);
                db1.stm.executeUpdate(q4);
               // db1.stm.executeUpdate(q5);
               db1.stm.executeUpdate(q6);
                
                System.out.println("Date Updated");
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
}
