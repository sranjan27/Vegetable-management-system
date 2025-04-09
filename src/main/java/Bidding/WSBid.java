/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bidding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import vegetabletrading.ParabitDB;

/**
 *
 * @author HARSHIT
 */
public class WSBid {
    ParabitDB db1;
    String date;
    
    
    public WSBid(DefaultTableModel model,String wrid,String scode,int round){
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        date = (dtf.format(now));
        
        model.setRowCount(0);
        
        db1 = new ParabitDB();
        try
        {
            String getquery = "SELECT * FROM `wb"+scode+"` WHERE `WRID` = "+wrid+" and RegDate = '"+ date +"' and RoundNo = '"+ round +"' order by VegId";
            //System.out.println(getquery);
            db1.rs = db1.stm.executeQuery(getquery);
            
            while(db1.rs.next())
            {
                String vegid =String.valueOf(db1.rs.getInt("VegID"));
                String vegname = db1.rs.getString("VName");
                String veghname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String vegtype = db1.rs.getString("VegType");
                String vegcolour = db1.rs.getString("VegColour");
                String veggrade = db1.rs.getString("VegGrade");
                String vegcate = db1.rs.getString("VegCategory");
                int vegqua = db1.rs.getInt("TotalQua");
                int vegprice = db1.rs.getInt("Price");
                
                model.addRow(new Object[]{vegid,vegname,veghname,regname,vegtype,vegcolour,veggrade,vegcate,vegqua,vegprice});
            }
                 
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
}
