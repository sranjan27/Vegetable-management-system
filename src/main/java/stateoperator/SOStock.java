/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package stateoperator;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vegetabletrading.ParabitDB;
import vegetabletrading.print;
import piechart.ModelPieChart;

/**
 *
 * @author HARSHIT
 */
public class SOStock extends javax.swing.JDialog {
    
    ParabitDB db1,db2,db3;
    String empid,name,postid,dcodes,date,scode,pcodes=null;
    

    /**
     * Creates new form stock
     */
    public SOStock(java.awt.Frame parent, boolean modal,String a) {
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
            String getquery = "SELECT VegID, VName, HName, RName, VegType, VegCategory, VegColour, SUM(Quantity), QuaRating FROM `dp"+scode+"` WHERE RegDate = '"+ date +"' GROUP BY VegID,VegCategory,VegColour,VegType,QuaRating ORDER BY VegID";
            
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
                                       
                DefaultTableModel promodel = (DefaultTableModel)producttable.getModel();
                promodel.addRow(new Object[]{vegid,vname,hname,regname,veggrade,qua,vtype,vcate,vcolour});
                                                               
                db2 = new ParabitDB();
                db2.rs = db2.stm.executeQuery("SELECT * FROM `dailyproduction` WHERE RegDate = '"+ date +"' AND `StateCode`= '"+postid+"' AND `VegID`= "+vegid+" AND `VName`= '"+vname+"' AND `VegGrade`= '"+veggrade+"' AND `VegType`= '"+vtype+"' AND `VegCategory`= '"+vcate +"' AND `VegColour`= '"+vcolour+"';");
                boolean check = db2.rs.next();
                //System.out.println(check);
                if(check){
                    String query = "UPDATE `dailyproduction` SET `TotalQua`= "+qua+" Where RegDate = '"+ date +"' AND `StateCode`= '"+postid+"' AND `VegID`= "+vegid+" AND `VName`= '"+vname+"' AND `VegGrade`= '"+veggrade+"' AND `VegType`= '"+vtype+"' AND `VegCategory`= '"+vcate +"' AND `VegColour`= '"+vcolour+"';";
                    //System.out.println(query);
                    db1.pst = db1.con.prepareStatement(query);
                    db1.pst.executeUpdate();
                }
                else{
                    String att = "(`StateCode`, `VegID`, `VName`, `HName`, `RName`, `VegGrade`, `VegType`, `VegCategory`, `VegColour`, `TotalQua`, RegDate)";
                    String val = postid+"','"+vegid+"','"+vname+"','"+hname+"','"+regname+"','"+veggrade+"','"+vtype+"','"+vcate+"','"+vcolour+"','"+qua+"','"+date;
                    String qvs = "insert into dailyproduction "+att+" values('"+val+"')";
                    db1.pst = db1.con.prepareStatement(qvs);
                    db1.pst.executeUpdate();
                }
            }
            
            db2 = new ParabitDB();
            db2.rs = db2.stm.executeQuery("select distinct distcode from districtcode where statecode = "+postid +" ORDER BY distcode;");
            while(db2.rs.next())
            {
                String dcode = db2.rs.getString(1);
                //System.out.println(dcode);
                getPanchCode(dcode);
                
                if(pcodes != null){
                    db3 = new ParabitDB();
                    String query = "SELECT VegID, VName, HName, RName, VegType, VegCategory, VegColour, SUM(Quantity), QuaRating FROM `dp"+scode+"` WHERE RegDate = '"+ date +"' AND ("+pcodes+") GROUP BY VegID,VegCategory,VegColour,VegType,QuaRating ORDER BY PCode;";
                    System.out.println(query);
                    db3.rs = db3.stm.executeQuery(query);
                    boolean check = db3.rs.next();
                    if(check){
                        db1 = new ParabitDB();
                        db1.rs = db1.stm.executeQuery("SELECT VegID, VName, HName, RName, VegType, VegCategory, VegColour, SUM(Quantity), QuaRating FROM `dp"+scode+"` WHERE RegDate = '"+ date +"' AND ("+pcodes+") GROUP BY VegID,VegCategory,VegColour,VegType,QuaRating ORDER BY PCode;");
                        
                        //to set date got from data base and set to  farmer table
                        while(db1.rs.next())
                        {
                            String distid = dcode;
                            String vegid = String.valueOf(db1.rs.getInt("VegID"));
                            String vname = db1.rs.getString("VName");
                            String hname = db1.rs.getString("HName");
                            String regname = db1.rs.getString("RName");
                            String vgrade = db1.rs.getString("QuaRating");
                            String qua = db1.rs.getString("SUM(Quantity)");
                            String vtype = db1.rs.getString("VegType");
                            String vcate = db1.rs.getString("VegCategory");
                            String vcolour = db1.rs.getString("VegColour");
                    
                            DefaultTableModel panmodel = (DefaultTableModel)panchayattable.getModel();
                            panmodel.addRow(new Object[]{distid,vegid,vname,hname,regname,vgrade,qua,vtype,vcate,vcolour});
                        }
                    }
                }
            }
       
        }catch(Exception e)
        {
            System.out.println(e);
        }
     
    }
    
    private void getData()
    {
        soid.setText("Operator Id : "+empid);
        
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select name from employee where empid = "+empid);
            db1.rs.next();
            name = db1.rs.getString(1);
            soname.setText("Operator Name : "+name);           
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
            statecode.setText("State Code : "+ postid);
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
        
        //to get District Codes
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select distinct distcode from districtcode where statecode = "+postid +" ORDER BY distcode;");           
            while(db1.rs.next())
            {
                stdistid.addItem(db1.rs.getString(1));
            }  
        }catch(Exception e)
        {
            System.out.println(e);
        }
               
    }
    
    
    
    private void getPanchCode(String dcode){
        //to get Panchayat Codes
        db3 = new ParabitDB();
        try
        {
            db3.rs = db3.stm.executeQuery("select distinct PCode from panchayatreg where DistCode = "+dcode +" ORDER BY PCode;");
            boolean check = db3.rs.next();
            pcodes = null;
            if(check){
                db1.rs = db1.stm.executeQuery("select distinct PCode from panchayatreg where DistCode = "+dcode +" ORDER BY PCode;");
                db1.rs.next();
                pcodes = "PCode = "+db1.rs.getString(1);
                while(db1.rs.next())
                {
                    pcodes = pcodes +" OR PCode = "+ db1.rs.getString(1);
                }  
            }
            //System.out.println(pcodes);
        }catch(Exception e)
        {
            System.out.println(e);
        }
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
        stdistid = new javax.swing.JComboBox<>();
        stockdate = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        statecode = new javax.swing.JLabel();
        soid = new javax.swing.JLabel();
        soname = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        pieChart1 = new piechart.PieChart();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("State Stock");
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel1.setText(" STOCK ");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        producttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Veg ID", "Vegetable Name", "Hindi Name", "Regional Name", "Veg Grade", "Quantity (Kg)", "Veg Type", "Veg Category", "Veg Colour"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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

        panchayattable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "District ID", "Veg ID", "Vegetable Name", "Hindi Name", "Regional Name", "Veg Grade", "Quantity", "Veg Type", "Veg Category", "Veg Colour"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
            panchayattable.getColumnModel().getColumn(6).setPreferredWidth(50);
        }

        jLabel2.setText("District ID");

        stdistid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose District ID" }));
        stdistid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stdistidActionPerformed(evt);
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
                        .addComponent(stdistid, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(stdistid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("District Based", jPanel2);

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

        statecode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        soid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        soname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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
                                .addComponent(statecode, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(soid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(49, 49, 49)
                                .addComponent(jButton3)))
                        .addGap(55, 55, 55))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(soname, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jButton2)
                    .addComponent(statecode, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(soname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(soid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(stockdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3)
                            .addComponent(jButton4))
                        .addGap(47, 47, 47)
                        .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
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
            System.out.println(date);
            String getquery = ("SELECT VegID, VName, HName, RName, VegType, VegCategory, VegColour, SUM(Quantity), QuaRating FROM `dp"+scode+"` WHERE RegDate = '"+ date +"' GROUP BY VegID,VegCategory,VegColour,VegType,QuaRating ORDER BY VegID;");
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
                                         
                DefaultTableModel promodel = (DefaultTableModel)producttable.getModel();
                promodel.addRow(new Object[]{vegid,vname,hname,regname,veggrade,qua,vtype,vcate,vcolour});
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
        /*int alignX = (int)chartpbt.getX();
        int alignY = (int)chartpbt.getY();

        System.out.println(alignX+ "    "+ alignY);
        
        String a = "Product Based";
        ch = new ParabitChart(a,alignX,alignY,this);
        DefaultTableModel model = (DefaultTableModel)producttable.getModel();
        for(int i =0; i<model.getRowCount(); i++){
            
            String vegname = model.getValueAt(i, 1).toString();
            int quantity = Integer.parseInt(model.getValueAt(i,5).toString());
            
            ch.piedataset.setValue(vegname, Integer.valueOf(quantity));
        }
        
        ch.start();*/
        DefaultTableModel model = (DefaultTableModel)producttable.getModel();
        for(int i =0; i<model.getRowCount(); i++){
            String vegname = model.getValueAt(i, 1).toString();
            int quantity = Integer.parseInt(model.getValueAt(i,5).toString());
            
            ModelPieChart modelchart = new ModelPieChart(vegname, quantity,new Color(23, 126, 238));
            getContentPane().setBackground(new Color(255, 255, 255));
            pieChart1.setChartType(piechart.PieChart.PeiChartType.DONUT_CHART);
            pieChart1.addData(modelchart);
        }
        //pieChart1.setChartType(PieChart.PeiChartType.DONUT_CHART);
        //pieChart1.addData(new ModelPieChart("Tigher", 150, new Color(23, 126, 238)));
        //pieChart1.addData(new ModelPieChart("ABC", 100, new Color(221, 65, 65)));
        //pieChart1.addData(new ModelPieChart("Coca", 1, new Color(47, 157, 64)));
        //pieChart1.addData(new ModelPieChart("Vita", 60, new Color(196, 151, 58)));
           
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void stdistidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stdistidActionPerformed
       
        String pcode = (String) stdistid.getSelectedItem();
        //System.out.println(pcode);
        
        String sql = ("SELECT * FROM `dp"+scode+"` WHERE RegDate = '"+ date +"' and PCode = '"+ pcode +"'  GROUP BY PCode,VegID,VegCategory,VegColour,VegType,QuaRating ORDER BY VegID;");
        //System.out.println(sql);
        try {
            DefaultTableModel panmodel = (DefaultTableModel)panchayattable.getModel();
            panmodel.setRowCount(0);
            
            db1 = new ParabitDB();
            
            db1.rs = db1.stm.executeQuery(sql);
            //to set date according to panchayat id to panchayat table
            while(db1.rs.next())
            {
                String panchid = db1.rs.getString("PCode");
                String vegid =String.valueOf(db1.rs.getInt("VegID"));
                String vname = db1.rs.getString("VName");
                String hname = db1.rs.getString("HName");
                String regname = db1.rs.getString("RName");
                String veggrade = db1.rs.getString("QuaRating");
                String qua = db1.rs.getString("Quantity");
                String vtype = db1.rs.getString("VegType");
                String vcate = db1.rs.getString("VegCategory");
                String vcolour = db1.rs.getString("VegColour");
                                                         
                panmodel.addRow(new Object[]{panchid,vegid,vname,hname,regname,veggrade,qua,vtype,vcate,vcolour});
            }             
        // TODO add your handling code here:
        } catch (Exception e) {
            System.out.println(e);
        }
        

        // TODO add your handling code here:
    }//GEN-LAST:event_stdistidActionPerformed

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
            java.util.logging.Logger.getLogger(SOStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SOStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SOStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SOStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                SOStock dialog = new SOStock(new javax.swing.JFrame(), true,a);
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
    private javax.swing.JTable panchayattable;
    private piechart.PieChart pieChart1;
    private javax.swing.JTable producttable;
    private javax.swing.JLabel soid;
    private javax.swing.JLabel soname;
    private javax.swing.JLabel statecode;
    private javax.swing.JComboBox<String> stdistid;
    private com.toedter.calendar.JDateChooser stockdate;
    // End of variables declaration//GEN-END:variables
}
