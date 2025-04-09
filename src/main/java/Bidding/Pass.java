/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bidding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;
import vegetabletrading.ParabitDB;

/**
 *
 * @author HARSHIT
 */
public class Pass {
    int bundel;
    ParabitDB db1;
    
    public Pass(String scode){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        String date = (dtf.format(now));
        String get = "SELECT * FROM `mst"+scode+"` WHERE RegDate = '"+date+"'";
        //System.out.println(get);
        db1 = new ParabitDB();
        try {
            db1.rs = db1.stm.executeQuery(get);
            while(db1.rs.next())
            {                
                int count = 1,avgqua,srange = bundel+1,erange;
                bundel = db1.rs.getInt("TotalBundle");
                System.out.println(bundel);
                String vegid = String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String vgrade = db1.rs.getString("VegGrade");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                                             
                if(bundel>200){
                    avgqua = (bundel/10);
                    srange = (avgqua - (int)(avgqua * 0.1))+1;
                    erange = avgqua + 1 + (int)(avgqua * 0.1);   
                }
                else if(bundel>60){
                    avgqua = (bundel/10);
                    srange = (avgqua - (int)(avgqua * 0.1));
                    erange = avgqua + 1 + (int)(avgqua * 0.2);                         
                }
                else if(bundel>20){
                    avgqua = (bundel/10);
                    srange = (avgqua - (int)(avgqua * 0.5)) +1;
                    erange = avgqua + 2 + (int)(avgqua * 0.3);                         
                }else{
                    srange = 1;
                    erange = (int)(bundel * 0.235);
                }
                if(srange==0){
                    srange = 1;
                }
                if(erange<=srange){
                    erange = 2;
                }
                
                while(bundel >srange && count<=9){
                    int passqua = ThreadLocalRandom.current().nextInt(srange, erange);
                    //System.out.println("pass "+count+" = "+passqua);
                                       
                    String send = "UPDATE `mst"+scode+"` SET `R"+count+"`= '"+passqua+"' WHERE  RegDate = '"+date+"' and VegID = '"+vegid+"' and VName = '"+vname+"' and VegGrade = '"+vgrade+"' and VegType = '"+vtype+"' and VegCategory = '"+vcate+"' and VegColour = '"+vcolour+"'";
                    System.out.println(send);
                    db1.pst = db1.con.prepareStatement(send);
                    db1.pst.executeUpdate();
                   
                    bundel = bundel - passqua;
                    count++;     
                }
                if(bundel>0){
                    String send = "UPDATE `mst"+scode+"` SET `R"+count+"`= '"+bundel+"' WHERE  RegDate = '"+date+"' and VegID = '"+vegid+"' and VName = '"+vname+"' and VegGrade = '"+vgrade+"' and VegType = '"+vtype+"' and VegCategory = '"+vcate+"' and VegColour = '"+vcolour+"'";
                    db1.pst = db1.con.prepareStatement(send);
                    //System.out.println(send);
                    db1.pst.executeUpdate();  
                }
            }
            //System.out.println("Passes created");
            JOptionPane.showMessageDialog(null, "Passes created");
        } catch (Exception e) {
            System.out.println(e);
        }   
    }
}
