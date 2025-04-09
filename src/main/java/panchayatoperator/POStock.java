/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package panchayatoperator;

import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vegetabletrading.ParabitDB;
import vegetabletrading.print;
import piechart.ModelPieChart;
import piechart.ModelPieChart;
import vegetabletrading.print;

/**
 *
 * @author HARSHIT
 */
public class POStock extends javax.swing.JDialog {
    
    ParabitDB db1,db2,db3;

    String date = null,empid,name,postid,scode;
    

    /**
     * Creates new form stock
     */
    public POStock(java.awt.Frame parent, boolean modal,String a) {
        super(parent, modal);
        initComponents();
        update.setVisible(false);
        empid = a;      
        getEmpData();
        jButton3.setVisible(true);
      
        try
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now();  
            date = (dtf.format(now));
            //String getquery = ("SELECT * FROM `dailyproduction` WHERE RegDate = '"+ date +"' ORDER BY VegID;");
            String getquery = "SELECT VegID, VName, HName, RName, VegType, VegCategory, VegColour, SUM(Quantity), AVG(ExpRate), QuaRating FROM `dp"+scode+"` WHERE RegDate = '"+ date +"' and PCode = '"+ postid +"' GROUP BY VegID,VegCategory,VegColour,VegType,QuaRating ORDER BY VegID";
            System.out.println(getquery);
            
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery(getquery);            
                      
            //to set date got from data base and set to product table
            while(db1.rs.next())
            {
                String panccode = postid;
                String vegid = String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String hname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String vgrade = db1.rs.getString("QuaRating");
                int qua = db1.rs.getInt("SUM(Quantity)");
                int rate = db1.rs.getInt("AVG(ExpRate)");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                                         
                DefaultTableModel promodel = (DefaultTableModel)ptable.getModel();
                promodel.addRow(new Object[]{vegid,vname,hname,regname,vgrade,qua,vtype,vcate,vcolour}); 
                
                int bundle = getBundle(vegid);
                               
                db2 = new ParabitDB();
                db2.rs = db2.stm.executeQuery("SELECT * FROM `pst"+scode+"` WHERE RegDate = '"+ date +"' AND `PCode`= '"+panccode+"' AND `VegID`= "+vegid+" AND `VName`= '"+vname+"' AND `VegGrade`= '"+vgrade+"' AND `VegType`= '"+vtype+"' AND `VegCategory`= '"+vcate +"' AND `VegColour`= '"+vcolour+"';");
                boolean check = db2.rs.next();
                //System.out.println(check);
                if(check){
                    String query = "UPDATE `pst"+scode+"` SET `TotalQua`= "+qua+",`AvgPrice`= "+rate+",`BundleQua`= "+(qua/bundle)+",`QuaAfBundle`= "+ (qua -((qua/bundle)*bundle))+" Where RegDate = '"+ date +"' AND `PCode`= '"+panccode+"' AND `VegID`= "+vegid+" AND `VName`= '"+vname+"' AND `VegGrade`= '"+vgrade+"' AND `VegType`= '"+vtype+"' AND `VegCategory`= '"+vcate +"' AND `VegColour`= '"+vcolour+"';";
                    //System.out.println(query);
                    db1.pst = db1.con.prepareStatement(query);
                    db1.pst.executeUpdate();
                }
                else{
                    String att = "(`PCode`, `VegID`, `VName`, `HName`, `RName`, `VegGrade`, `VegType`, `VegCategory`, `VegColour`, `TotalQua`, `AvgPrice`, `BundleQua`, `QuaAfBundle`, RegDate)";
                    String val = panccode+"','"+vegid+"','"+vname+"','"+hname+"','"+regname+"','"+vgrade+"','"+vtype+"','"+vcate+"','"+vcolour+"','"+qua+"','"+rate+"','"+(qua/bundle)+"','"+ (qua -((qua/bundle)*bundle))+"','"+date;
                    String qvs = "insert into pst"+scode+" "+att+" values('"+val+"')";
                    db1.pst = db1.con.prepareStatement(qvs);
                    db1.pst.executeUpdate();
                }     
            }
                                     
            db2 = new ParabitDB();
            db2.rs = db2.stm.executeQuery("SELECT * FROM `dp"+scode+"` WHERE RegDate = '"+ date +"'and PCode = '"+ postid +"' ORDER BY RegTime DESC;");
            //to set date got from data base and set to  farmer table
            while(db2.rs.next())
            {
                String farid =String.valueOf(db2.rs.getInt("FID"));
                String vegid =String.valueOf(db2.rs.getInt("VegID"));
                String vname = db2.rs.getString("VName");
                String hname = db2.rs.getString("HName");
                String regname = db2.rs.getString("RName");
                String vgrade = db2.rs.getString("QuaRating");
                String qua = db2.rs.getString("Quantity");
                String vtype = db2.rs.getString("VegType");
                String vcate = db2.rs.getString("VegCategory");
                String vcolour = db2.rs.getString("VegColour");
                String dpid = db2.rs.getString("DPID");
                                         
                DefaultTableModel farmodel = (DefaultTableModel)ftable.getModel();
                farmodel.addRow(new Object[]{farid,dpid,vegid,vname,hname,regname,vgrade,qua,vtype,vcate,vcolour});  
            }
           
        }catch(Exception e)
        {
            System.out.println(e);
        }
                  
    }
    
    private void getEmpData()
    {
        poid.setText("Operator Id : "+empid);
        try
        {
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery("select name from employee where empid = "+empid);
            db1.rs.next();
            name = db1.rs.getString(1);
            poname.setText("Operator Name : "+name);   
        }catch(Exception e)
        {
            System.out.println(e);
        }
                
        try
        {
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery("select PostingID from employee where empid = "+empid);
            db1.rs.next();
            postid = db1.rs.getString(1);
            pcode.setText("Panchayat Code : "+postid); 
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        //to get employee post state code
        db3 = new ParabitDB();
        try
        {
            db3.rs = db3.stm.executeQuery("select StateCode from employee where PostingID = "+postid);
            while(db3.rs.next())
            {
                scode = db3.rs.getString(1);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    private int getBundle(String id){
        int bundle = 0;
        try {
            String vegid = id;
            db3 = new ParabitDB();
            db3.rs = db3.stm.executeQuery("SELECT `BundleQua` FROM `productlist` WHERE SNo = "+ vegid);
            db3.rs.next();
            bundle = db3.rs.getInt("BundleQua");
        } catch (Exception e) {
            System.out.println(e);
        }
        return bundle;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ptable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ftable = new javax.swing.JTable();
        update = new javax.swing.JButton();
        stfarid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        farIdBt = new javax.swing.JButton();
        stockdate = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        pcode = new javax.swing.JLabel();
        poid = new javax.swing.JLabel();
        poname = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        pieChart1 = new piechart.PieChart();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Stock");
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel1.setText(" STOCK ");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Veg ID", "Vegetable Name", "Hindi Name", "Regional Name", "Veg Grade", "Quantity (Kg)", "Veg Type", "Veg Category", "Veg Colour"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ptable);
        if (ptable.getColumnModel().getColumnCount() > 0) {
            ptable.getColumnModel().getColumn(0).setPreferredWidth(30);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Product Based", jPanel1);

        ftable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "FID", "DPID", "Veg ID", "Vegetable Name", "Hindi Name", "Regional Name", "Veg Grade", "Quantity", "Veg Type", "Veg Category", "Veg Colour"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ftable);
        if (ftable.getColumnModel().getColumnCount() > 0) {
            ftable.getColumnModel().getColumn(0).setPreferredWidth(40);
            ftable.getColumnModel().getColumn(1).setPreferredWidth(40);
            ftable.getColumnModel().getColumn(2).setPreferredWidth(50);
            ftable.getColumnModel().getColumn(3).setPreferredWidth(100);
            ftable.getColumnModel().getColumn(4).setPreferredWidth(100);
            ftable.getColumnModel().getColumn(5).setPreferredWidth(100);
            ftable.getColumnModel().getColumn(6).setPreferredWidth(50);
            ftable.getColumnModel().getColumn(7).setPreferredWidth(50);
        }

        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        jLabel2.setText("Farmer ID");

        farIdBt.setText("Get");
        farIdBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farIdBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(stfarid, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(farIdBt)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(update)))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stfarid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(farIdBt))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(update)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Farmer Based", jPanel2);

        stockdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockdateMouseClicked(evt);
            }
        });

        jButton1.setText("Get");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        pcode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        poid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        poname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jButton2.setText("Home");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Pie Chart");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Print");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(stockdate, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton1))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(238, 238, 238)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(poid, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(pcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton4)
                                        .addGap(47, 47, 47)
                                        .addComponent(jButton3))
                                    .addComponent(jButton2))
                                .addGap(53, 53, 53))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(poname, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                .addGap(15, 15, 15))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pieChart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pcode, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(poname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(poid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(stockdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3)
                            .addComponent(jButton4))
                        .addGap(47, 47, 47)
                        .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void stockdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockdateMouseClicked
       

        // TODO add your handling code here:
    }//GEN-LAST:event_stockdateMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        
        
        update.setVisible(false);
        
        DefaultTableModel pmodel = (DefaultTableModel)ptable.getModel();
        pmodel.setRowCount(0);
        
        DefaultTableModel fmodel = (DefaultTableModel)ftable.getModel();
        fmodel.setRowCount(0);
        
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(stockdate.getDate());
            String getquery = ("SELECT VegID, VName, HName, RName, VegType, VegCategory, VegColour, SUM(Quantity), QuaRating FROM `dp"+scode+"` WHERE RegDate = '"+ date +"'and PCode = '"+ postid +"' GROUP BY VegID,VegCategory,VegColour,VegType,QuaRating ORDER BY VegID;");
            System.out.println(getquery);
            
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery(getquery);            
                      
            //to set date got from data base and set to product table
            while(db1.rs.next())
            {
                String vegid = String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String hname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String veggrade = db1.rs.getString("QuaRating");
                String qua = db1.rs.getString("SUM(Quantity)");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                                         
                DefaultTableModel promodel = (DefaultTableModel)ptable.getModel();
                promodel.addRow(new Object[]{vegid,vname,hname,regname,veggrade,qua,vtype,vcate,vcolour});
            }
            
            db2 = new ParabitDB();
            db2.rs = db2.stm.executeQuery("SELECT * FROM `dp"+scode+"` WHERE RegDate = '"+ date +"' and PCode = '"+ postid +"' ORDER BY RegTime DESC;");
            //to set date got from data base and set to  farmer table
            while(db2.rs.next())
            {
                String farid =String.valueOf(db2.rs.getInt("FID"));
                String vegid =String.valueOf(db2.rs.getInt("VegID"));
                String vname = db2.rs.getString("VName");
                String hname = db2.rs.getString("HName");
                String regname = db2.rs.getString("RName");
                String vgrade = db2.rs.getString("QuaRating");
                String qua = db2.rs.getString("Quantity");
                String vtype = db2.rs.getString("VegType");
                String vcate = db2.rs.getString("VegCategory");
                String vcolour = db2.rs.getString("VegColour");
                String dpid = db2.rs.getString("DPID");
                                         
                DefaultTableModel farmodel = (DefaultTableModel)ftable.getModel();
                farmodel.addRow(new Object[]{farid,dpid,vegid,vname,hname,regname,vgrade,qua,vtype,vcate,vcolour});
            }
                  
        }catch(Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(this,"Please Select Date...");
        }
        
       
    
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime date = LocalDateTime.now();
        LocalTime time = LocalTime.now();
        String d = (dtf.format(date));
        String t = time.toString();
        
        DefaultTableModel model = (DefaultTableModel)ftable.getModel();
        
        for(int i =0; i<model.getRowCount(); i++){
            
            String fid = model.getValueAt(i, 0).toString();
            String dpid = model.getValueAt(i, 1).toString();
            String vegid = model.getValueAt(i, 2).toString();
            String vgrade = model.getValueAt(i, 6).toString();
            String qua = model.getValueAt(i, 7).toString();
            String vtype = model.getValueAt(i, 8).toString();
            String vcate = model.getValueAt(i, 9).toString();
            String vcolour = model.getValueAt(i, 10).toString();
                                               
            String qvs = "update dp"+scode+" set QuaRating ='"+vgrade+"', Quantity ='"+qua+"', VegType ='"+vtype+"', VegCategory ='"+vcate+"' , VegColour ='"+vcolour+"', RegDate ='"+d+"' , RegTime ='"+t+"' where FID = " + fid + " and PCode = '"+ postid +"' AND VegID = '" + vegid + "' AND DPID = '" + dpid + "';";
            System.out.println(qvs);
                
            // To insert values of this tab in database
            db3 = new ParabitDB();
            try
            {
                db3.stm.executeUpdate(qvs);                  
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
        JOptionPane.showMessageDialog(this,"Updated Sucessfully...");
        // TODO add your handling code here:
    }//GEN-LAST:event_updateActionPerformed

    private void farIdBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farIdBtActionPerformed
        
        String farmerid = stfarid.getText();
        String sql = ("SELECT * FROM `dp"+scode+"` WHERE RegDate = '"+ date +"' and FID = '"+ farmerid +"' and PCode = '"+ postid +"' ORDER BY VegID;");
        System.out.println(sql);
        try {
            DefaultTableModel farmodel = (DefaultTableModel)ftable.getModel();
            farmodel.setRowCount(0);
            
            db3 = new ParabitDB();
            
            db3.rs = db3.stm.executeQuery(sql);
            //to set date according to farmer id to farmer table
            while(db3.rs.next())
            {
                String farid =String.valueOf(db3.rs.getInt("FID"));
                String vegid =String.valueOf(db3.rs.getInt("VegID"));
                String vname = db3.rs.getString("VName");
                String hname = db3.rs.getString("HName");
                String regname = db3.rs.getString("RName");
                String veggrade = db3.rs.getString("QuaRating");
                String qua = db3.rs.getString("Quantity");
                String vtype = db3.rs.getString("VegType");
                String vcate = db3.rs.getString("VegCategory");
                String vcolour = db3.rs.getString("VegColour");
                String dpid = db3.rs.getString("DPID");
                                         
                //DefaultTableModel farmodel = (DefaultTableModel)ftable.getModel();
                farmodel.addRow(new Object[]{farid,dpid,vegid,vname,hname,regname,veggrade,qua,vtype,vcate,vcolour});
            }
            
            update.setVisible(true);
            
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(POStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_farIdBtActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        
        DefaultTableModel model = (DefaultTableModel)ptable.getModel();
        for(int i =0; i<model.getRowCount(); i++){
            String vegname = model.getValueAt(i, 1).toString();
            int quantity = Integer.parseInt(model.getValueAt(i,5).toString());
            
            ModelPieChart modelchart = new ModelPieChart(vegname, quantity,new Color(23, 126, 238));
            getContentPane().setBackground(new Color(255, 255, 255));
            pieChart1.setChartType(piechart.PieChart.PeiChartType.DONUT_CHART);
            pieChart1.addData(modelchart);
        }   
        jButton3.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        JTable t = ptable;
        print.printtable(t);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(POStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String a = null;
                POStock dialog = new POStock(new javax.swing.JFrame(), true,a);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton farIdBt;
    private javax.swing.JTable ftable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel pcode;
    private piechart.PieChart pieChart1;
    private javax.swing.JLabel poid;
    private javax.swing.JLabel poname;
    private javax.swing.JTable ptable;
    private javax.swing.JTextField stfarid;
    private com.toedter.calendar.JDateChooser stockdate;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
