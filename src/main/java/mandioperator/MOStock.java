/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package mandioperator;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vegetabletrading.ParabitDB;
import vegetabletrading.print;

/**
 *
 * @author HARSHIT
 */
public class MOStock extends javax.swing.JDialog {
    ParabitDB db1,db2;

    String empid,name,postid,pcodes,scode,date;
    

    /**
     * Creates new form stock
     */
    public MOStock(java.awt.Frame parent, boolean modal,String a) {
        super(parent, modal);
        initComponents();
        empid = a;
        getData();
            
        //set valuse to product based table
        try
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now();  
            date = (dtf.format(now));            
            String getquery = "SELECT VegID, VName, HName, RName, VegGrade, VegType, VegCategory, VegColour, SUM(TotalQua), AVG(AvgPrice), SUM(BundleQua), SUM(QuaAfBundle) FROM `pst"+scode+"` WHERE RegDate = '"+ date +"' AND ("+pcodes+") GROUP BY VegID,VegCategory,VegColour,VegType,VegGrade ORDER BY VegID";
            //System.out.println(getquery);
            
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery(getquery);
                      
            //to set date got from data base and set to product table
            while(db1.rs.next())
            {
                String vegid = String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String hname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String veggrade = db1.rs.getString("VegGrade");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                String qua = db1.rs.getString("SUM(TotalQua)");
                int avgprice = db1.rs.getInt("AVG(AvgPrice)");
                int vegprice = defPrice(avgprice);
                //System.out.println(vegprice);
                
                String bundle = db1.rs.getString("SUM(BundleQua)");
                String remqua = db1.rs.getString("SUM(QuaAfBundle)");
                
                DefaultTableModel promodel = (DefaultTableModel)producttable.getModel();
                promodel.addRow(new Object[]{vegid,vname,hname,regname,veggrade,vtype,vcate,vcolour,qua,bundle,remqua});
                              
                      
                db2 = new ParabitDB();
                db2.rs = db2.stm.executeQuery("SELECT * FROM `mst"+scode+"` WHERE RegDate = '"+ date +"' AND `MCode`= '"+postid+"' AND `VegID`= "+vegid+" AND `VName`= '"+vname+"' AND `VegGrade`= '"+veggrade+"' AND `VegType`= '"+vtype+"' AND `VegCategory`= '"+vcate +"' AND `VegColour`= '"+vcolour+"';");
                //System.out.println("SELECT * FROM `mst"+scode+"` WHERE RegDate = '"+ date +"' AND `MCode`= '"+postid+"' AND `VegID`= "+vegid+" AND `VName`= '"+vname+"' AND `VegGrade`= '"+veggrade+"' AND `VegType`= '"+vtype+"' AND `VegCategory`= '"+vcate +"' AND `VegColour`= '"+vcolour+"';");

                boolean check = db2.rs.next();
                //System.out.println(check);
                if(check){
                    String query = "UPDATE `mst"+scode+"` SET `TotalQua`= "+qua+",`R1BidPrice`= "+vegprice+",`R2BidPrice`= "+vegprice+",`R3BidPrice`= "+vegprice+",`R4BidPrice`= "+vegprice+",`R5BidPrice`= "+vegprice+",`R6BidPrice`= "+vegprice+",`R7BidPrice`= "+vegprice+",`R8BidPrice`= "+vegprice+",`R9BidPrice`= "+vegprice+",`R10BidPrice`= "+vegprice+",`Price`= "+vegprice+",`TotalBundle`= "+bundle+", `QuaAfBundle`= "+remqua+" Where RegDate = '"+ date +"' AND `MCode`= '"+postid+"' AND `VegID`= "+vegid+" AND `VName`= '"+vname+"' AND `VegGrade`= '"+veggrade+"' AND `VegType`= '"+vtype+"' AND `VegCategory`= '"+vcate +"' AND `VegColour`= '"+vcolour+"';";
                    //System.out.println(query);
                    db1.pst = db1.con.prepareStatement(query);
                    db1.pst.executeUpdate();      
                }
                else{
                    String att = "(`MCode`, `VegID`, `VName`, `HName`, `RName`, `VegGrade`, `VegType`, `VegCategory`, `VegColour`, `TotalQua`, `R1BidPrice`, `R2BidPrice`, `R3BidPrice`, `R4BidPrice`, `R5BidPrice`, `R6BidPrice`, `R7BidPrice`, `R8BidPrice`, `R9BidPrice`, `R10BidPrice`, `Price`, `TotalBundle`, `QuaAfBundle`, RegDate)";
                    String val = postid+"','"+vegid+"','"+vname+"','"+hname+"','"+regname+"','"+veggrade+"','"+vtype+"','"+vcate+"','"+vcolour+"','"+qua+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+vegprice+"','"+bundle+"','"+remqua+"','"+date;
                    String qvs = "insert into mst"+scode+" "+att+" values('"+val+"')";
                    //System.out.println(qvs);
                    db1.pst = db1.con.prepareStatement(qvs);
                    db1.pst.executeUpdate();
                }
            }
            
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery("SELECT * FROM `pst"+scode+"` WHERE RegDate = '"+ date +"' AND ("+pcodes+") ORDER BY PCode;");
           
            while(db1.rs.next())
            {
                String pid = db1.rs.getString("PCode");
                String vegid =String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String hname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String vgrade = db1.rs.getString("VegGrade");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                String qua = db1.rs.getString("TotalQua");
                String bundle = db1.rs.getString("BundleQua");
                String remqua = db1.rs.getString("QuaAfBundle");
                
                                                         
                DefaultTableModel panmodel = (DefaultTableModel)panchayattable.getModel();
                panmodel.addRow(new Object[]{pid,vegid,vname,hname,regname,vgrade,vtype,vcate,vcolour,qua,bundle,remqua});
            }     
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    private void getData()
    {
        moid.setText("Operator Id : "+empid);
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select name from employee where empid = "+empid);
            db1.rs.next();
            name = db1.rs.getString(1);
            moname.setText("Operator Name : "+name);           
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select PostingID from employee where empid = "+empid);
            db1.rs.next();
            postid = db1.rs.getString(1);
            mcode.setText("Mandi Code : "+ postid);
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        //to get employee post state code
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select StateCode from employee where PostingID = "+postid);
            while(db1.rs.next())
            {
                scode = db1.rs.getString(1);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        //to get panchayat codes link to this mandi
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select PCode from vegetablesource where MCode = "+postid);
            db1.rs.next();
            stpanchid.addItem(db1.rs.getString(1));
            pcodes = "PCode = "+db1.rs.getString(1);
            while(db1.rs.next())
            {
                stpanchid.addItem(db1.rs.getString(1));
                pcodes = pcodes +" OR PCode = "+ db1.rs.getString(1);
            }  
            System.out.println(pcodes);
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    private int defPrice(int price){
        int vegprice = 0;
        try {
            String getquery = "Select Total FROM defaultrates where StateCode = " + scode;
                        
            db2 = new ParabitDB();
            db2.rs = db2.stm.executeQuery(getquery);
            db2.rs.next();
            int defcharges = db2.rs.getInt("Total");
            //System.out.println(defcharges);
            vegprice  = (int)(price +(price * (defcharges/100.0)));    
                          
        }catch (Exception e){
            System.out.println(e);
        }
        return vegprice;   
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
        producttable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panchayattable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        stpanchid = new javax.swing.JComboBox<>();
        stockdate = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        mcode = new javax.swing.JLabel();
        moid = new javax.swing.JLabel();
        moname = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mandi Operator Stock");
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel1.setText(" STOCK ");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        producttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Veg ID", "Vegetable Name", "Hindi Name", "Regional Name", "Veg Grade", "Veg Type", "Veg Category", "Veg Colour", "Quantity (Kg)", "Total Bundle", "Extra Qua"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(producttable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Product Based", jPanel1);

        panchayattable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Panchayat ID", "Veg ID", "Vegetable Name", "Hindi Name", "Regional Name", "Veg Grade", "Veg Type", "Veg Category", "Veg Colour", "Quantity", "Bundle", "Extra Qua"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(panchayattable);
        if (panchayattable.getColumnModel().getColumnCount() > 0) {
            panchayattable.getColumnModel().getColumn(0).setPreferredWidth(70);
            panchayattable.getColumnModel().getColumn(1).setPreferredWidth(50);
            panchayattable.getColumnModel().getColumn(2).setPreferredWidth(100);
            panchayattable.getColumnModel().getColumn(3).setPreferredWidth(100);
            panchayattable.getColumnModel().getColumn(4).setPreferredWidth(100);
            panchayattable.getColumnModel().getColumn(5).setPreferredWidth(50);
            panchayattable.getColumnModel().getColumn(9).setPreferredWidth(50);
        }

        jLabel2.setText("Panchayat ID");

        stpanchid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Panchayat ID" }));
        stpanchid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stpanchidActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(stpanchid, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(stpanchid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Panchayat Based", jPanel2);

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

        mcode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        moid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        moname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(stockdate, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(136, 136, 136)
                                .addComponent(mcode, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(moid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(24, 24, 24)
                .addComponent(moname, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(49, 49, 49)
                                .addComponent(jButton3)))
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jButton2)
                    .addComponent(mcode, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(moname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(moid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stockdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton3)
                        .addComponent(jButton4)))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void stockdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockdateMouseClicked
       

        // TODO add your handling code here:
    }//GEN-LAST:event_stockdateMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                
        DefaultTableModel pmodel = (DefaultTableModel)producttable.getModel();
        pmodel.setRowCount(0);
        
        DefaultTableModel panmodel = (DefaultTableModel)panchayattable.getModel();
        panmodel.setRowCount(0);
        
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(stockdate.getDate());
            
            String getquery = "SELECT VegID, VName, HName, RName, VegGrade, VegType, VegCategory, VegColour, SUM(TotalQua), SUM(BundleQua), SUM(QuaAfBundle) FROM `pst"+scode+"` WHERE RegDate = '"+ date +"' AND ("+pcodes+") GROUP BY VegID,VegCategory,VegColour,VegType,VegGrade ORDER BY VegID";
            //System.out.println(getquery);
            
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery(getquery);            
                      
            //to set date got from data base and set to product table
            while(db1.rs.next())
            {
                String vegid = String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String hname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String veggrade = db1.rs.getString("VegGrade");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                String qua = db1.rs.getString("SUM(TotalQua)");
                String bundle = db1.rs.getString("SUM(BundleQua)");
                String remqua = db1.rs.getString("SUM(QuaAfBundle)");
              
                pmodel.addRow(new Object[]{vegid,vname,hname,regname,veggrade,vtype,vcate,vcolour,qua,bundle,remqua});
            }
            
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery("SELECT * FROM `pst"+scode+"` WHERE RegDate = '"+ date +"' AND ("+pcodes+") ORDER BY PCode;");
           
            while(db1.rs.next())
            {
                String pid = db1.rs.getString("PCode");
                String vegid =String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String hname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String vgrade = db1.rs.getString("VegGrade");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                String qua = db1.rs.getString("TotalQua");
                String bundle = db1.rs.getString("BundleQua");
                String remqua = db1.rs.getString("QuaAfBundle");
                
                panmodel.addRow(new Object[]{pid,vegid,vname,hname,regname,vgrade,vtype,vcate,vcolour,qua,bundle,remqua});
            }
            
        }catch(Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(this,"Please Select Date...");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /*String a = "Product Based";
        ch = new ParabitChart(a);
        DefaultTableModel model = (DefaultTableModel)producttable.getModel();
        for(int i =0; i<model.getRowCount(); i++){
            
            String vegname = model.getValueAt(i, 1).toString();
            int quantity = Integer.parseInt(model.getValueAt(i,5).toString());
            
            ch.piedataset.setValue(vegname, Integer.valueOf(quantity));
        }
                    
        ch.chartpanel.setVisible(true);
        ch.frame.setVisible(true);
        this.setVisible(false);*/
           
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void stpanchidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stpanchidActionPerformed
       
        String pcode = (String) stpanchid.getSelectedItem();
        //System.out.println(pcode);
  
        try {
            DefaultTableModel panmodel = (DefaultTableModel)panchayattable.getModel();
            panmodel.setRowCount(0);
            
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery("SELECT * FROM `pst"+scode+"` WHERE RegDate = '"+ date +"' AND PCode = '"+ pcode +"' ORDER BY PCode;");
                       
            while(db1.rs.next())
            {
                String pid = db1.rs.getString("PCode");
                String vegid =String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String hname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String vgrade = db1.rs.getString("VegGrade");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                String qua = db1.rs.getString("TotalQua");
                String bundle = db1.rs.getString("BundleQua");
                String remqua = db1.rs.getString("QuaAfBundle");
         
                panmodel.addRow(new Object[]{pid,vegid,vname,hname,regname,vgrade,vtype,vcate,vcolour,qua,bundle,remqua});                                           
            }             
        // TODO add your handling code here:
        } catch (Exception e) {
            System.out.println(e);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_stpanchidActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        JTable t = producttable;
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
            java.util.logging.Logger.getLogger(MOStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MOStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MOStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MOStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String a = null;
                MOStock dialog = new MOStock(new javax.swing.JFrame(), true,a);
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
    private javax.swing.JLabel mcode;
    private javax.swing.JLabel moid;
    private javax.swing.JLabel moname;
    private javax.swing.JTable panchayattable;
    private javax.swing.JTable producttable;
    private com.toedter.calendar.JDateChooser stockdate;
    private javax.swing.JComboBox<String> stpanchid;
    // End of variables declaration//GEN-END:variables
}
