/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panchayatoperator;

import Transport.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import vegetabletrading.ParabitDB;

/**
 *
 * @author HARSHIT
 */
public class AllotVeh {
    ParabitDB db1;
    int count=0,totalweight;
    String date,pcode,scode;

    AllotVeh(String panchcode,int weight){
        pcode = panchcode;
        totalweight = weight;
        
        //to get employee post state code
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select StateCode from employee where PostingID = "+pcode);
            while(db1.rs.next())
            {
                scode = db1.rs.getString(1);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        getVehCount();
        String query = getData();
        setData(query);
    }
    
    private void getVehCount(){
        try {
            String sql = "SELECT COUNT(*) FROM `vehicledetails` WHERE PCode = '"+ pcode +"' ORDER BY Capacity;";
            //System.out.println(sql);
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery(sql);
            db1.rs.next();
            count = Integer.parseInt(db1.rs.getString("COUNT(*)"));
            //System.out.println(count);
            
        }catch (Exception e) 
        {
            System.out.println(e);      
        }
    }
    
    private String getData(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        date = (dtf.format(now));
        
        int index = -1;
        String vehnoarr[] =  new String[count];
        try {
            String sql = "SELECT * FROM `availveh23` WHERE PCode = '"+ pcode +"' ORDER BY Capacity;";
            System.out.println(sql);
                        
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery(sql);
            while(db1.rs.next())
            {
                String vehno = db1.rs.getString("VehicleNo");
                int cap = Integer.parseInt(db1.rs.getString("Capacity"));
                if(cap <= totalweight){
                    index = index + 1;
                    vehnoarr[index] = vehno;
                    System.out.println(vehno);
                    //System.out.println(cap);
                }
            }
            index = ThreadLocalRandom.current().nextInt(0, index+1);
            System.out.println(index);
            if(index >=0){
                sql = "SELECT * FROM `availveh23` WHERE PCode = '"+ pcode +"' and VehicleNo = '"+ vehnoarr[index] +"' ORDER BY Capacity;";
                return sql;
            }
        } catch (Exception e)
        {
            System.out.println(e);      
        }
        
        return "0";
    }
    
    private void setData(String query){
        System.out.println(query);
        if(query.equals("0")){
            String sql = "SELECT * FROM `availveh23` WHERE PCode = '"+ pcode +"' ORDER BY Rate;";
            db1 = new ParabitDB();
            try {
                db1.rs = db1.stm.executeQuery(sql);
                db1.rs.next();
                String pcode = db1.rs.getString("PCode");
                String toid = db1.rs.getString("TOID");
                String vehno = db1.rs.getString("VehicleNo");
                String vehtype = db1.rs.getString("VehicleType");
                String fueltype = db1.rs.getString("FuelType");
                int price = db1.rs.getInt("Rate");
                int weighalloted = totalweight;
                int amount = price*weighalloted;   
                
                query = "INSERT INTO `allotedveh"+scode+"`(`PCode`, `TOID`, `VehicleNo`, `VehicleType`, `FuelType`, `Rate`, `WeightAlloted`, `TotalMoney`, `AllotementDate`) VALUES ('"+pcode+"','"+toid+"','"+vehno+"','"+vehtype+"','"+fueltype+"','"+price+"','"+weighalloted+"','"+amount+"','"+date+"')";
                System.out.println(query);
                db1.stm.executeUpdate(query);
            
                query = "DELETE FROM `availveh"+scode+"` WHERE VehicleNo = '"+vehno+"';";
                System.out.println(query);
                db1.stm.executeUpdate(query);
            
                // TODO add your handling code here:
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            db1 = new ParabitDB();
            try {
                db1.rs = db1.stm.executeQuery(query);
                db1.rs.next();
            
                String pcode = db1.rs.getString("PCode");
                String toid = db1.rs.getString("TOID");
                String vehno = db1.rs.getString("VehicleNo");
                String vehtype = db1.rs.getString("VehicleType");
                String fueltype = db1.rs.getString("FuelType");
                int price = db1.rs.getInt("Rate");
                int weighalloted = db1.rs.getInt("Capacity");
                int amount = price*weighalloted;   
            
                query = "INSERT INTO `allotedveh"+scode+"`(`PCode`, `TOID`, `VehicleNo`, `VehicleType`, `FuelType`, `Rate`, `WeightAlloted`, `TotalMoney`, `AllotementDate`) VALUES ('"+pcode+"','"+toid+"','"+vehno+"','"+vehtype+"','"+fueltype+"','"+price+"','"+weighalloted+"','"+amount+"','"+date+"')";
                System.out.println(query);
                db1.stm.executeUpdate(query);
            
                query = "DELETE FROM `availveh"+scode+"` WHERE VehicleNo = '"+vehno+"';";
                System.out.println(query);
                db1.stm.executeUpdate(query);
            
                // TODO add your handling code here:
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
