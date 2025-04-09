/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wholesaler;

import Bidding.WSBid;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vegetabletrading.EmpLogin;
import vegetabletrading.HomePage;
import vegetabletrading.ParabitDB;
import vegetabletrading.ScreenSize;

/**
 *
 * @author HARSHIT
 */
public class Wholesaler extends javax.swing.JFrame {
    ParabitDB db1,db2;
    Bidding b;
    BidThread tbid;
    
    int haction = 0,vaction = 0, round, roundchange = 0,vegid;
    String wrid,date = null,mcode,scode;
    String active = "0";
  
    public Wholesaler(String a) {
        initComponents();
        wrid = a;
        
        bidok.setVisible(false);
        dashboard.setVisible(false);
        bid.setVisible(false);
        defaultbid.setVisible(false);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime now = LocalDateTime.now();  
        date = (dtf.format(now));
        
        ScreenSize s = new ScreenSize();
        this.setSize(s.width, s.height);
        
        getData();

        b = new Bidding();
        
        //tbid = new BidThread();
        //tbid.start();
          
    }
    
    private void getData()
    {      
        wsid.setText("Id: "+wrid);
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select MandiCode from wholesalerreg where WRID = "+wrid);
            db1.rs.next();
            mcode = db1.rs.getString(1); 
            mandicode.setText("Mandi Code: "+mcode);
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select * from wholesalerreg where WRID = "+wrid);
            db1.rs.next();
            wsname.setText("Name: "+db1.rs.getString("WRName")); 
            
            BufferedImage bi = ImageIO.read(db1.rs.getBinaryStream("WRPhoto"));
            Image img = bi.getScaledInstance(112, 126, Image.SCALE_SMOOTH );
            wrphoto.setIcon(new ImageIcon(img));
        }catch(Exception e)
        {
            System.out.println(e);
        }
                        
        db1 = new ParabitDB();
        try
        {
            String query = "SELECT StateCode FROM `employee` WHERE PostingID = "+mcode;
            //System.out.println(query);
            db1.rs = db1.stm.executeQuery(query);
            db1.rs.next();
            scode = db1.rs.getString("StateCode");
            //System.out.println(stcode);
        }catch(Exception e)
        {
            System.out.println(e);
        }
                                    
    }
   
    private void wsBidData(){
        DefaultTableModel panmodel = (DefaultTableModel)vegtable.getModel();
        WSBid ob = new WSBid(panmodel,wrid,scode,round);           
    }
    
    private void vegData(){
        try
        {
            db2 = new ParabitDB();
            //System.out.println("select distinct VName from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode+"' and (R"+round+">0) ORDER BY VegID");
            db2.rs = db2.stm.executeQuery("select distinct VName from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode+"' and (R"+round+">0) ORDER BY VegID");
            boolean check = db2.rs.next();
            if(check){   
                db1 = new ParabitDB();
                //System.out.println("select distinct VName from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode+"' and (R"+round+">0) ORDER BY VegID");
                db1.rs = db1.stm.executeQuery("select distinct VName from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode+"' and (R"+round+">0) ORDER BY VegID");
                while(db1.rs.next())
                {
                    cbvname.addItem(db1.rs.getString("VName")); 
                }     
        
                db1 = new ParabitDB();
                //System.out.println("select distinct HName from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode+"' and (R"+round+">0) ORDER BY VegID");
                db1.rs = db1.stm.executeQuery("select distinct HName from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode+"' and (R"+round+">0) ORDER BY VegID");
                while(db1.rs.next())
                {
                    cbhname.addItem(db1.rs.getString("HName")); 
                } 
            }
            else{
                bid.setVisible(false);
                defaultbid.setVisible(true);
                bidntst.setVisible(false);
                noveg.setVisible(true);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }        
    }
    
    private int getBundle(int vegid){
        int bundle = 0;
        db2 = new ParabitDB();
        try {
            db2 = new ParabitDB();
            db2.rs = db2.stm.executeQuery("SELECT `BundleQua` FROM `productlist` WHERE SNo = "+ vegid);
            db2.rs.next();
            bundle = db2.rs.getInt("BundleQua");
        } catch (Exception e) {
            System.out.println(e);
        }
        return bundle;
    }
    
    class BidThread extends Thread
    { 
        int i =1;
        BidThread(){
           
        }
        public void run(){ 
            db1 = new ParabitDB();
            
            while(i==1){   
                try{
                    String query = "SELECT * FROM `bidstart` WHERE StateCode = "+scode+" AND BidONDate = '"+ date +"'";
                    //System.out.println(query);
                    db1.rs = db1.stm.executeQuery(query);
                    boolean check = db1.rs.next();
                    if(check){
                        //bid.setVisible(true);
                        active = db1.rs.getString("BidActive");
                        //System.out.println(active);
                        round = db1.rs.getInt("RoundNo");
                        bidround.setText("ROUND "+round);
                    
                        if(round != roundchange){
                            defaultbid.setVisible(false);
                            bid.setVisible(true);
                            
                            wsBidData();
                            roundchange = round;
                            b.setVisible(false);
                            vegData();
                        }
                    
                        if(active.equals("1")){
                            bidok.setVisible(true);
                            sleep(1000);
                        }else {
                            bidok.setVisible(false);
                            b.setVisible(false);
                            sleep(100);  
                        } 
                    }else{
                        //i++;
                        bid.setVisible(false);
                        defaultbid.setVisible(true);
                        bidntst.setVisible(true);
                        noveg.setVisible(false);
                    }
                }catch(Exception e) {
                    System.out.println(e);
                }
            }       
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

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        dashboardbt = new javax.swing.JButton();
        biddingbt = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        wsid = new javax.swing.JLabel();
        mandicode = new javax.swing.JLabel();
        wsname = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        bid = new javax.swing.JPanel();
        bidok = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        vegtable = new javax.swing.JTable();
        cbvname = new javax.swing.JComboBox<>();
        cbvgrade = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        quantity = new javax.swing.JTextPane();
        bidround = new javax.swing.JLabel();
        cbhname = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        baseprice = new javax.swing.JTextPane();
        jButton4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        liveprice = new javax.swing.JTextPane();
        defaultbid = new javax.swing.JPanel();
        bidntst = new javax.swing.JTextField();
        noveg = new javax.swing.JTextField();
        dashboard = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        dashboardtable = new javax.swing.JTable();
        roundno = new javax.swing.JComboBox<>();
        totalprice = new javax.swing.JLabel();
        wrphoto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(106, 200, 43));

        dashboardbt.setBackground(new java.awt.Color(106, 200, 43));
        dashboardbt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dashboardbt.setText("Dashboard");
        dashboardbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardbtActionPerformed(evt);
            }
        });

        biddingbt.setBackground(new java.awt.Color(106, 200, 43));
        biddingbt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        biddingbt.setText("Bidding");
        biddingbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                biddingbtActionPerformed(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel62.setText("  VEGETABLE TRADING");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboardbt, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
            .addComponent(biddingbt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(biddingbt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(dashboardbt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel62)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(106, 200, 43));

        wsid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        mandicode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        wsname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jButton1.setBackground(java.awt.SystemColor.activeCaption);
        jButton1.setText("Log Out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("WHOLESALER");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mandicode, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wsid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wsname, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(mandicode, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(wsid, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(wsname, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        bid.setBackground(new java.awt.Color(255, 255, 255));

        bidok.setText("OK");
        bidok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bidokActionPerformed(evt);
            }
        });

        vegtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Veg ID", "Veg Name", "Hindi Name", "Regional Name", "Veg Type", "Veg Colour", "Veg Grade", "Veg Category", "Quantity (Kg)", "Base Price (Rs/Kg)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        vegtable.setGridColor(new java.awt.Color(153, 255, 153));
        vegtable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(vegtable);
        if (vegtable.getColumnModel().getColumnCount() > 0) {
            vegtable.getColumnModel().getColumn(0).setPreferredWidth(50);
            vegtable.getColumnModel().getColumn(9).setPreferredWidth(90);
        }

        cbvname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Vegetable Name" }));
        cbvname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbvnameMouseClicked(evt);
            }
        });
        cbvname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbvnameActionPerformed(evt);
            }
        });

        cbvgrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Grade" }));
        cbvgrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbvgradeActionPerformed(evt);
            }
        });

        jButton5.setText("ADD");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        quantity.setEditable(false);
        quantity.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(quantity);

        bidround.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        bidround.setText("ROUND Start");

        cbhname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Hindi Name" }));
        cbhname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbhnameMouseClicked(evt);
            }
        });
        cbhname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbhnameActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(baseprice);

        jButton4.setText("Delete Row");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(liveprice);

        javax.swing.GroupLayout bidLayout = new javax.swing.GroupLayout(bid);
        bid.setLayout(bidLayout);
        bidLayout.setHorizontalGroup(
            bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bidLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(46, 46, 46)
                .addComponent(bidok)
                .addGap(40, 40, 40))
            .addGroup(bidLayout.createSequentialGroup()
                .addGroup(bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bidLayout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(bidLayout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addGroup(bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(bidround, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(bidLayout.createSequentialGroup()
                                    .addComponent(cbvname, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(cbhname, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(20, 20, 20)
                                    .addComponent(cbvgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addGroup(bidLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        bidLayout.setVerticalGroup(
            bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bidLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(bidround)
                .addGap(19, 19, 19)
                .addGroup(bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbvname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbvgrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbhname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bidLayout.createSequentialGroup()
                        .addGroup(bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(bidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bidok)
                            .addComponent(jButton4))))
                .addGap(35, 35, 35))
        );

        defaultbid.setBackground(new java.awt.Color(255, 255, 255));

        bidntst.setEditable(false);
        bidntst.setBackground(new java.awt.Color(255, 255, 255));
        bidntst.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        bidntst.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        bidntst.setText("BIDDING NOT STARTED YET !!!!");
        bidntst.setBorder(null);
        bidntst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bidntstActionPerformed(evt);
            }
        });

        noveg.setEditable(false);
        noveg.setBackground(new java.awt.Color(255, 255, 255));
        noveg.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        noveg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        noveg.setText("NO VEGETABLE FOR THIS ROUND");
        noveg.setBorder(null);
        noveg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novegActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout defaultbidLayout = new javax.swing.GroupLayout(defaultbid);
        defaultbid.setLayout(defaultbidLayout);
        defaultbidLayout.setHorizontalGroup(
            defaultbidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bidntst, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
            .addComponent(noveg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
        );
        defaultbidLayout.setVerticalGroup(
            defaultbidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(defaultbidLayout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(bidntst, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(noveg, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
        );

        dashboard.setBackground(new java.awt.Color(255, 255, 255));

        dashboardtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Round No", "Veg ID", "Veg Name", "Hindi Name", "Regional Name", "Veg Type", "Veg Colour", "Veg Grade", "Veg Category", "Quantity (Kg)", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dashboardtable.setGridColor(new java.awt.Color(153, 255, 153));
        dashboardtable.getTableHeader().setReorderingAllowed(false);
        dashboardtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardtableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(dashboardtable);
        if (dashboardtable.getColumnModel().getColumnCount() > 0) {
            dashboardtable.getColumnModel().getColumn(1).setPreferredWidth(50);
            dashboardtable.getColumnModel().getColumn(10).setPreferredWidth(90);
        }

        roundno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Round No", "Round 1", "Round 2", "Round 3", "Round 4", "Round 5", "Round 6", "Round 7", "Round 8", "Round 9", "Round 10" }));
        roundno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundnoActionPerformed(evt);
            }
        });

        totalprice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        totalprice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalprice.setText("Total Amount :");

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(roundno, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardLayout.createSequentialGroup()
                .addGap(0, 704, Short.MAX_VALUE)
                .addComponent(totalprice, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(roundno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalprice)
                .addGap(31, 31, 31))
        );

        jLayeredPane1.setLayer(bid, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(defaultbid, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(dashboard, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 960, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(bid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(defaultbid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(bid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(defaultbid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        wrphoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wrphoto, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(wrphoto, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        HomePage hpob = new HomePage();
        hpob.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dashboardbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardbtActionPerformed
        
        if(this.tbid != null){
            tbid.i=2;
        }
        
        roundchange = 0;
        
        int totalamt = 0;        
        db1 = new ParabitDB();
        DefaultTableModel dashmodel = (DefaultTableModel)dashboardtable.getModel();
        dashmodel.setRowCount(0);
        
        for(int i = 1; i<=10; i++){
            try{
                String query = "SELECT * FROM `mst"+scode+"` WHERE `R"+i+"WRID` = "+wrid+" AND RegDate = '"+date+"'";
                System.out.println(query);
                db1.rs = db1.stm.executeQuery(query);
                while(db1.rs.next())
                {
                    int vegid = db1.rs.getInt("VegID");
                    String vegname = db1.rs.getString("VName");
                    String veghname = db1.rs.getString("HName");
                    String regname = db1.rs.getString("RName");
                    String vegtype = db1.rs.getString("VegType");
                    String vegcolour = db1.rs.getString("VegColour");
                    String veggrade = db1.rs.getString("VegGrade");
                    String vegcate = db1.rs.getString("VegCategory");
                    int bundle = getBundle(vegid);
                    int vegqua = bundle * (db1.rs.getInt("R"+i));
                    int vegprice = db1.rs.getInt("R"+i+"BidPrice");
                    totalamt = totalamt + (vegprice*vegqua);
                    
                    dashmodel.addRow(new Object[]{i,vegid,vegname,veghname,regname,vegtype,vegcolour,veggrade,vegcate,vegqua,vegprice});
                }
                
            }catch(Exception e) {
                    System.out.println(e);
            }
        }
        
        totalprice.setText("Total Amount: "+totalamt+" Rs  ");
        dashboard.setVisible(true);
        bid.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_dashboardbtActionPerformed

    private void biddingbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_biddingbtActionPerformed

        tbid = new BidThread();
        //tbid.i=1;
        tbid.start();
                          
        dashboard.setVisible(false);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_biddingbtActionPerformed

    private void bidokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bidokActionPerformed
        
            b = new Bidding(wrid,round);
            b.setVisible(true);
         
            wsBidData();
        // TODO add your handling code here:
    }//GEN-LAST:event_bidokActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    String grade = (String)cbvgrade.getSelectedItem();
    if(grade.equals("A")||grade.equals("B")||grade.equals("C")||grade.equals("D")){
        
        String q = "select * from mst"+scode+" where RegDate = '"+date+"' and MCode = '"+mcode+"' and VegID = '"+vegid+"' and VegGrade = '"+grade+"'";
        //System.out.println(q);
        
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery(q);
            db1.rs.next();
            int vegid = db1.rs.getInt("VegID");
            String vegname = db1.rs.getString("VName");
            String hindiname = db1.rs.getString("HName");
            String regname = db1.rs.getString("RName");
            String vtype = db1.rs.getString("VegType");
            String vcolour = db1.rs.getString("VegColour");
            String vgrade = db1.rs.getString("VegGrade");
            String vcate = db1.rs.getString("VegCategory");
            int passqua = db1.rs.getInt("R"+round);
            String vegprice = db1.rs.getString("Price");
            int bundle = getBundle(vegid);
            
             
            db2 = new ParabitDB();
            String chquery = "SELECT * FROM `wb"+scode+"` WHERE `WRID` = "+wrid+" AND `VegID`= '"+vegid+"' AND `VName`= '"+vegname+"' AND `VegType` = '"+vtype+"' AND `VegColour`= '"+vcolour+"' AND `VegGrade`= '"+vgrade+"' AND `VegCategory`= '"+vcate+"' AND `RoundNo`= '"+round+"' AND RegDate = '"+ date+"'";
            //System.out.println(chquery);
            db2.rs = db2.stm.executeQuery(chquery);
            boolean check = db2.rs.next();
            if(check==false){
                
                DefaultTableModel promodel = (DefaultTableModel)vegtable.getModel();
                promodel.addRow(new Object[]{vegid,vegname,hindiname,regname,vtype,vcolour,vgrade,vcate,(passqua*bundle),vegprice});
                
                String att = "`WRID`, `VegID`, `VName`, `HName`, `RName`, `VegType`, `VegColour`, `VegGrade`, `VegCategory`, bundle, `TotalQua`, `Price` , `RoundNo` ,`RegDate`";
                String val = wrid+"','"+vegid+"','"+vegname+"','"+hindiname+"','"+regname+"','"+vtype+"','"+vcolour+"','"+vgrade+"','"+vcate+"','"+passqua+"','"+(passqua*bundle)+"','"+vegprice+"','"+round+"','"+date;
           
                String qvs = "insert into wb"+scode+" ("+att+") values('"+val+"')";
                //System.out.println(qvs);
                db1.pst = db1.con.prepareStatement(qvs);
                db1.pst.executeUpdate();
            }else{
                 
                JOptionPane.showMessageDialog(this,"Already Available");
            }
            
            liveprice.setText("");
            liveprice.setBackground(Color.WHITE);
            baseprice.setText("");
            quantity.setText("");
            cbhname.setSelectedIndex(0);
            cbvname.setSelectedIndex(0);
            cbvgrade.setSelectedIndex(0);
            
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
    }else{
       JOptionPane.showMessageDialog(this,"Please Select Valid Data...");
    }
    
       
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cbvnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbvnameActionPerformed
    
        String vname = cbvname.getSelectedItem().toString();
        //System.out.println(vname);
        
        if(vaction == 1){
            db1 = new ParabitDB();
            try
            {
                String query = "select * from productlist where Name = '"+vname+"'";
                //System.out.println(query);
                db1.rs = db1.stm.executeQuery(query);
                db1.rs.next();
                String hname = db1.rs.getString("HName");
                cbhname.setSelectedItem(hname);
                
                vegid = db1.rs.getInt("SNO");
            
            }catch(Exception e)
            {
                System.out.println(e);
            }
        
            db1 = new ParabitDB();
            try
            {
                //cbvgrade.removeAllItems();
                //cbvgrade.addItem("Select Grade");
                String query = "select VegGrade from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode +"' and (R"+round+">0) and VName = '"+vname+"'";
                //System.out.println(query);
                db1.rs = db1.stm.executeQuery(query); 
                while(db1.rs.next())
                {
                    cbvgrade.addItem(db1.rs.getString(1));  
                }
            }catch(Exception e)
            {
                System.out.println(e);
            }
            vaction = 0;
        }
      
        // TODO add your handling code here:
    }//GEN-LAST:event_cbvnameActionPerformed

    private void cbvgradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbvgradeActionPerformed
        String grade = cbvgrade.getSelectedItem().toString();
        
        db1 = new ParabitDB();
        try
        {
            String query = "select R"+round+" from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode +"' and VegID = '"+vegid+"' and VegGrade = '"+grade+"'";
            //System.out.println(query);
            db1.rs = db1.stm.executeQuery(query);
            int bundle = getBundle(vegid);
            while(db1.rs.next())
            {
                quantity.setText("Quantity: "+(bundle*db1.rs.getInt(1))+" Kg");  
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        db1 = new ParabitDB();
        try
        {
            String query = "select R"+round+"BidPrice from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode +"' and VegID = '"+vegid+"' and VegGrade = '"+grade+"'";
            //System.out.println(query);
            db1.rs = db1.stm.executeQuery(query);
            
            while(db1.rs.next())
            {
                liveprice.setText("Live Price: "+db1.rs.getString(1)+" Rs / Kg");
                liveprice.setBackground(Color.GREEN);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        db1 = new ParabitDB();
        try
        {
            String query = "select Price from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode +"' and VegID = '"+vegid+"' and VegGrade = '"+grade+"'";
            //System.out.println(query);
            db1.rs = db1.stm.executeQuery(query);
            
            while(db1.rs.next())
            {
                baseprice.setText("Base Price: "+db1.rs.getString(1)+" Rs / Kg");  
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        quantity.setEditable(false);
        baseprice.setEditable(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_cbvgradeActionPerformed

    private void cbhnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbhnameActionPerformed
        String hname = cbhname.getSelectedItem().toString();
        //System.out.println(hname);
        
    if(haction == 1){
        db1 = new ParabitDB();
        try
        {
                        
            String query = "select * from productlist where HName = '"+hname+"'";
                //System.out.println(query);
                db1.rs = db1.stm.executeQuery(query);
                db1.rs.next();
                String vname = db1.rs.getString("Name");
                cbhname.setSelectedItem(vname);
                
                vegid = db1.rs.getInt("SNO");
            
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        db1 = new ParabitDB();
        try
        {
            //cbvgrade.removeAllItems();
            //cbvgrade.addItem("Select Grade");
            String query = "select VegGrade from mst"+scode+" where RegDate = '"+ date +"' and MCode = '"+mcode +"' and (R"+round+">0) and HName = '"+hname+"'";
            //System.out.println(query);
            db1.rs = db1.stm.executeQuery(query); 
            while(db1.rs.next())
            {
                cbvgrade.addItem(db1.rs.getString(1));  
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
        haction = 0;
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_cbhnameActionPerformed

    private void cbvnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbvnameMouseClicked
        vaction = 1;

        // TODO add your handling code here:
    }//GEN-LAST:event_cbvnameMouseClicked

    private void cbhnameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbhnameMouseClicked
        haction = 1;
        
        // TODO add your handling code here:
    }//GEN-LAST:event_cbhnameMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int i = vegtable.getSelectedRow();
        
        DefaultTableModel model = (DefaultTableModel)vegtable.getModel();
        
        String vegid = model.getValueAt(i,0).toString();
        String vegname = model.getValueAt(i, 1).toString();
        String vegtype = model.getValueAt(i, 4).toString();
        String vegcolour = model.getValueAt(i, 5).toString();
        String veggrade = model.getValueAt(i, 6).toString();
        String vegcat = model.getValueAt(i, 7).toString();      
        
        String query = "DELETE FROM `wb"+scode+"` WHERE `WRID`='"+wrid+"' AND `VegID`='"+vegid+"' AND `VName`='"+vegname+"' AND `VegType`='"+vegtype+"' AND `VegColour`='"+vegcolour+"' AND `VegGrade`='"+veggrade+"' AND `VegCategory`='"+vegcat+"' AND `RegDate`='"+date+"';";
        System.out.println(query);
        db1 = new ParabitDB();
        try
        {
            db1.stm.executeUpdate(query);
            JOptionPane.showMessageDialog(this,"Vegetable deleted...");
        }catch(Exception e)
        {
            System.out.println(e);
        }
                
        wsBidData();
       
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void bidntstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bidntstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bidntstActionPerformed

    private void novegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_novegActionPerformed

    private void roundnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundnoActionPerformed

        int i = roundno.getSelectedIndex();
        
        int totalamt = 0;
        DefaultTableModel dashmodel = (DefaultTableModel)dashboardtable.getModel();
        if(i > 0){
            db1 = new ParabitDB();
        
            dashmodel.setRowCount(0);
            try{
                String query = "SELECT * FROM `mst"+scode+"` WHERE `R"+i+"WRID` = "+wrid;
                //System.out.println(query);
                db1.rs = db1.stm.executeQuery(query);
                while(db1.rs.next())
                {
                    int vegid = db1.rs.getInt("VegID");
                    String vegname = db1.rs.getString("VName");
                    String veghname = db1.rs.getString("HName");
                    String regname = db1.rs.getString("RName");
                    String vegtype = db1.rs.getString("VegType");
                    String vegcolour = db1.rs.getString("VegColour");
                    String veggrade = db1.rs.getString("VegGrade");
                    String vegcate = db1.rs.getString("VegCategory");
                    int bundle = getBundle(vegid);
                    int vegqua = bundle * (db1.rs.getInt("R"+i));
                    int vegprice = db1.rs.getInt("R"+i+"BidPrice");
                    totalamt = totalamt + (vegprice*vegqua);
                  
                    dashmodel.addRow(new Object[]{i,vegid,vegname,veghname,regname,vegtype,vegcolour,veggrade,vegcate,vegqua,vegprice});
                }
              
            }catch(Exception e) {
                System.out.println(e);
            }
            totalprice.setText("Round "+i+" Total Amount: "+totalamt+" Rs  ");
        }
        
        if(i == 0){
            totalamt = 0;        
            db1 = new ParabitDB();
            
            dashmodel.setRowCount(0);
        
            for(int j = 1; j<=10; j++){
                try{
                    String query = "SELECT * FROM `mst"+scode+"` WHERE `R"+j+"WRID` = "+wrid;
                    //System.out.println(query);
                    db1.rs = db1.stm.executeQuery(query);
                    while(db1.rs.next())
                    {
                        int vegid = db1.rs.getInt("VegID");
                        String vegname = db1.rs.getString("VName");
                        String veghname = db1.rs.getString("HName");
                        String regname = db1.rs.getString("RName");
                        String vegtype = db1.rs.getString("VegType");
                        String vegcolour = db1.rs.getString("VegColour");
                        String veggrade = db1.rs.getString("VegGrade");
                        String vegcate = db1.rs.getString("VegCategory");
                        int bundle = getBundle(vegid);
                        int vegqua = bundle * (db1.rs.getInt("R"+j));
                        int vegprice = db1.rs.getInt("R"+j+"BidPrice");
                        totalamt = totalamt + (vegprice*vegqua);
                    
                        dashmodel.addRow(new Object[]{j,vegid,vegname,veghname,regname,vegtype,vegcolour,veggrade,vegcate,vegqua,vegprice});
                    }
                
                }catch(Exception e) {
                        System.out.println(e);
                }
            }
        
            totalprice.setText("Total Amount: "+totalamt+" Rs  ");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_roundnoActionPerformed

    private void dashboardtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardtableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_dashboardtableMouseClicked

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
            java.util.logging.Logger.getLogger(Wholesaler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Wholesaler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Wholesaler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Wholesaler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String a = null;
                new Wholesaler(a).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane baseprice;
    private javax.swing.JPanel bid;
    private javax.swing.JButton biddingbt;
    private javax.swing.JTextField bidntst;
    private javax.swing.JButton bidok;
    private javax.swing.JLabel bidround;
    private javax.swing.JComboBox<String> cbhname;
    private javax.swing.JComboBox<String> cbvgrade;
    private javax.swing.JComboBox<String> cbvname;
    private javax.swing.JPanel dashboard;
    private javax.swing.JButton dashboardbt;
    private javax.swing.JTable dashboardtable;
    private javax.swing.JPanel defaultbid;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextPane liveprice;
    private javax.swing.JLabel mandicode;
    private javax.swing.JTextField noveg;
    private javax.swing.JTextPane quantity;
    private javax.swing.JComboBox<String> roundno;
    private javax.swing.JLabel totalprice;
    private javax.swing.JTable vegtable;
    private javax.swing.JLabel wrphoto;
    private javax.swing.JLabel wsid;
    private javax.swing.JLabel wsname;
    // End of variables declaration//GEN-END:variables
}
