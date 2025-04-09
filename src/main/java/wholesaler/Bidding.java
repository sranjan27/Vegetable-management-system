/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wholesaler;

import Bidding.LivePriceTable;
import Bidding.WSBid;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import vegetabletrading.ParabitDB;



/**
 *
 * @author HARSHIT
 */
public class Bidding extends javax.swing.JFrame {
    ParabitDB db1;
    String wrid,date,time,mcode,scode;
    int round,liveprice;
    
    public Bidding(){
        
    }
    
    public Bidding(String id,int r) {
        initComponents();
        wrid = id;
        round = r;
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
        LocalDateTime ldate = LocalDateTime.now();
        LocalTime ltime = LocalTime.now();
        date = (dtf.format(ldate));
        time = ltime.toString();
        
        getData();
        wsBidData();
        tableLivePrice();
        
        vid.setText("Select Vegetable");
        PanelLivePrice ob = new PanelLivePrice();
        ob.start();
        
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
        DefaultTableModel panmodel = (DefaultTableModel)bidtable.getModel();
        WSBid ob = new WSBid(panmodel,wrid,scode,round);
        
        int datarow = panmodel.getRowCount();
        //System.out.println(datarow);
        
        if(datarow == 0){                       
            jPanel2.setVisible(false);
            jPanel4.setVisible(true); 
        }else{
            jPanel2.setVisible(true);
            jPanel4.setVisible(false);
        }
    }
    
    private void tableLivePrice(){
        DefaultTableModel model = (DefaultTableModel)bidtable.getModel();
        int datarow = model.getRowCount();
        if(datarow > 0){   
            for(int i =0; i<model.getRowCount(); i++){
                String vegid = model.getValueAt(i,0).toString();
                String vegtype = model.getValueAt(i, 4).toString();
                String vegcolour = model.getValueAt(i, 5).toString();
                String veggrade = model.getValueAt(i, 6).toString();
                String vegcate = model.getValueAt(i, 7).toString();
               
                String getquery = "Select `R"+round+"BidPrice` from mst"+scode+" where MCode = '"+mcode+"' and VegID = '"+vegid+"' and VegType = '"+vegtype+"' and VegColour = '"+vegcolour+"' and VegGrade = '"+veggrade+"' and VegCategory = '"+vegcate+"' and RegDate = '"+ date +"'";
                         
                LivePriceTable ob = new LivePriceTable(getquery,i,model);
                ob.start();
                            
            }
        }
    }
    
    public class PanelLivePrice extends Thread {
    
        PanelLivePrice(){
            
        }
        public void run(){
            db1 = new ParabitDB();
            int i =1;
            while(i==1){ 
                try {
                    if(vid.getText().equals("Select Vegetable")){
                        sleep(100);
                    
                    }else{
                        String[] vegid = vid.getText().split(": ");
                        String[] vegtype = vtype.getText().split(": ");
                        String[] vegcolour = vcolour.getText().split(": ");
                        String[] veggrade = vgrade.getText().split(": ");
                        String[] vegcate = vcat.getText().split(": ");
                    
                        String getquery = "Select `R"+round+"WRID`, `R"+round+"BidPrice` from mst"+scode+" where MCode = '"+mcode+"' and VegID = '"+vegid[1]+"' and VegType = '"+vegtype[1]+"' and VegColour = '"+vegcolour[1]+"' and VegGrade = '"+veggrade[1]+"' and VegCategory = '"+vegcate[1]+"' and RegDate = '"+ date +"'";
                        //System.out.println(getquery);
                        db1.rs = db1.stm.executeQuery(getquery);
                        db1.rs.next();
                        
                        if(wrid.equals(db1.rs.getString(1))){
                            finalprice.setBackground(Color.green);
                            finalprice.setText("Live Price: "+db1.rs.getString(2)+" Rs");
                        }else{
                            finalprice.setBackground(Color.white);
                            finalprice.setText("Live Price: "+db1.rs.getString(2)+" Rs");
                        }
                        
                        sleep(100);
                    } 
                }catch (Exception e) {
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

        jPanel1 = new javax.swing.JPanel();
        wrphoto = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        mandicode = new javax.swing.JLabel();
        wsid = new javax.swing.JLabel();
        wsname = new javax.swing.JLabel();
        rowPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bidtable = new javax.swing.JTable();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        vimage = new javax.swing.JLabel();
        vid = new javax.swing.JTextField();
        vtype = new javax.swing.JTextField();
        finalprice = new javax.swing.JTextField();
        vname = new javax.swing.JTextField();
        vhindi = new javax.swing.JTextField();
        vregional = new javax.swing.JTextField();
        vcolour = new javax.swing.JTextField();
        vcat = new javax.swing.JTextField();
        vgrade = new javax.swing.JTextField();
        bidprice = new javax.swing.JSpinner();
        priceok = new javax.swing.JButton();
        vqua = new javax.swing.JTextField();
        baseprice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        wrphoto.setText(" WholeSaler Image");
        wrphoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel3.setBackground(new java.awt.Color(106, 200, 43));

        jButton1.setText("Home");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText(" WHOLESALER ");

        mandicode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        wsid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        wsname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mandicode, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(wsid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wsname, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(93, 93, 93))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(wsid, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(wsname, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap(21, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(mandicode, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wrphoto, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(wrphoto, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rowPanel.setBackground(new java.awt.Color(255, 255, 255));

        bidtable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bidtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Veg ID", "Veg Name", "Hindi Name", "Regional Name", "Veg Type", "Veg Colour", "Veg Grade", "Veg Category", "Quantity (Kg)", "Price (Rs/Kg)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        bidtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bidtableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bidtable);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("NO  VEGETABLE  SELECTED  FOR  THIS  ROUND");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 3, 14)); // NOI18N
        jLabel4.setText("Please Selecte Vegetable For Bidding :");

        jButton2.setText("Select Vegetable");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton2)
                .addGap(40, 40, 40))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton2))
                .addGap(12, 12, 12))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        vimage.setText("      Veg Image");
        vimage.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        vid.setEditable(false);
        vid.setBackground(new java.awt.Color(255, 255, 255));

        vtype.setEditable(false);
        vtype.setBackground(new java.awt.Color(255, 255, 255));
        vtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vtypeActionPerformed(evt);
            }
        });

        finalprice.setEditable(false);
        finalprice.setBackground(new java.awt.Color(255, 255, 255));
        finalprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalpriceActionPerformed(evt);
            }
        });

        vname.setEditable(false);
        vname.setBackground(new java.awt.Color(255, 255, 255));
        vname.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vname.setToolTipText("");
        vname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vnameActionPerformed(evt);
            }
        });

        vhindi.setEditable(false);
        vhindi.setBackground(new java.awt.Color(255, 255, 255));
        vhindi.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vhindi.setToolTipText("");

        vregional.setEditable(false);
        vregional.setBackground(new java.awt.Color(255, 255, 255));
        vregional.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vregional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vregionalActionPerformed(evt);
            }
        });

        vcolour.setEditable(false);
        vcolour.setBackground(new java.awt.Color(255, 255, 255));
        vcolour.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vcolour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vcolourActionPerformed(evt);
            }
        });

        vcat.setEditable(false);
        vcat.setBackground(new java.awt.Color(255, 255, 255));
        vcat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vcat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vcatActionPerformed(evt);
            }
        });

        vgrade.setEditable(false);
        vgrade.setBackground(new java.awt.Color(255, 255, 255));
        vgrade.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vgrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vgradeActionPerformed(evt);
            }
        });

        priceok.setText("OK");
        priceok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceokActionPerformed(evt);
            }
        });

        vqua.setEditable(false);
        vqua.setBackground(new java.awt.Color(255, 255, 255));
        vqua.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        vqua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vquaActionPerformed(evt);
            }
        });

        baseprice.setEditable(false);
        baseprice.setBackground(new java.awt.Color(255, 255, 255));
        baseprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                basepriceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(vimage, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(vtype, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(vid, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(vqua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(vname, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(vhindi, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(vregional, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vcolour)
                            .addComponent(baseprice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vgrade, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(finalprice, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vcat, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(bidprice))
                        .addGap(33, 33, 33)
                        .addComponent(priceok, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vhindi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vregional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vcolour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vgrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(finalprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bidprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(priceok)
                            .addComponent(vqua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(baseprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(vimage, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayer(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout rowPanelLayout = new javax.swing.GroupLayout(rowPanel);
        rowPanel.setLayout(rowPanelLayout);
        rowPanelLayout.setHorizontalGroup(
            rowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1082, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLayeredPane1)
        );
        rowPanelLayout.setVerticalGroup(
            rowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rowPanelLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(rowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void vtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vtypeActionPerformed

    private void finalpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_finalpriceActionPerformed

    private void vcolourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vcolourActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vcolourActionPerformed

    private void vcatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vcatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vcatActionPerformed

    private void vgradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vgradeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vgradeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void vnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vnameActionPerformed

    private void vregionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vregionalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vregionalActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bidtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bidtableMouseClicked

        int i = bidtable.getSelectedRow();
        //System.out.println(i);
        
        DefaultTableModel model = (DefaultTableModel)bidtable.getModel();
        
        String vegid = model.getValueAt(i,0).toString();
        db1 = new ParabitDB();
        try {
          
            db1.rs = db1.stm.executeQuery("SELECT Image FROM productlist WHERE SNo = '"+vegid+"'");
            db1.rs.next();
            BufferedImage bi = ImageIO.read(db1.rs.getBinaryStream("Image"));
            Image img = bi.getScaledInstance(110, 125, Image.SCALE_SMOOTH );
            vimage.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }  
        
        String vegtype = model.getValueAt(i, 4).toString();
        String vegcolour = model.getValueAt(i, 5).toString();
        String veggrade = model.getValueAt(i, 6).toString();
        String vegcate = model.getValueAt(i, 7).toString();
        
        
        vid.setText("Vegetable ID: "+vegid);
        vname.setText("Vegetable Name: "+model.getValueAt(i, 1).toString());
        vhindi.setText("Hindi Name: "+model.getValueAt(i, 2).toString());
        vregional.setText("Regional Name: "+model.getValueAt(i, 3).toString());
        vtype.setText("Veg Type: "+vegtype);
        vcolour.setText("Veg Colour: "+vegcolour);
        vgrade.setText("Veg Grade: "+veggrade);
        vcat.setText("Veg Category: "+vegcate);
        vqua.setText("Quantity: "+model.getValueAt(i, 8).toString()+" Kg");
        
        liveprice = (int) model.getValueAt(i, 9);
        bidprice.setValue(liveprice);
        
        //finalprice.setText("Live Price: "+liveprice+" Rs");
        
        db1 = new ParabitDB();
        try {
          
            String query = "SELECT `R"+round+"WRID`, Price FROM mst"+scode+" WHERE MCode = '"+mcode+"' and VegID = '"+vegid+"' and VegType = '"+vegtype+"' and VegColour = '"+vegcolour+"' and VegGrade = '"+veggrade+"' and VegCategory = '"+vegcate+"' and RegDate = '"+ date +"'";
            //System.out.println(query);
            db1.rs = db1.stm.executeQuery(query);
            db1.rs.next();
            if(wrid.equals(db1.rs.getString(1))){
                finalprice.setBackground(Color.green);
            }else{
               finalprice.setBackground(Color.white); 
            }
            baseprice.setText("Base Price: "+db1.rs.getInt(2)+" Rs");
            
        } catch (Exception e) {
            System.out.println(e);
        }
       
        // TODO add your handling code here:
    }//GEN-LAST:event_bidtableMouseClicked

    private void vquaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vquaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vquaActionPerformed

    private void priceokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceokActionPerformed
        int yourbid = (int) bidprice.getValue();
        
        if(liveprice<yourbid){
            liveprice = yourbid;
          
            String[] id = vid.getText().split(": ");
            String[] vegtype = vtype.getText().split(": ");
            String[] vegcolour = vcolour.getText().split(": ");
            String[] veggrade = vgrade.getText().split(": ");
            String[] vegcate = vcat.getText().split(": ");
            
            db1 = new ParabitDB();
            try
            {
                String getquery = "Select * from mst"+scode+" where MCode = '"+mcode+"' and VegID = '"+id[1]+"' and VegType = '"+vegtype[1]+"' and VegColour = '"+vegcolour[1]+"' and VegGrade = '"+veggrade[1]+"' and VegCategory = '"+vegcate[1]+"' and (`R"+round+"BidPrice` < "+liveprice+") and RegDate = '"+ date +"'";
                //System.out.println(getquery);
                db1.rs = db1.stm.executeQuery(getquery);
                boolean check = db1.rs.next();
                if(check){
                    String setquery = "UPDATE `mst"+scode+"` SET `R"+round+"BidPrice`= "+liveprice+", `R"+round+"WRID`= '"+wrid+"', `R"+round+"BidTime`= '"+time+"' WHERE MCode = '"+mcode+"' and VegID = '"+id[1]+"' and VegType = '"+vegtype[1]+"' and VegColour = '"+vegcolour[1]+"' and VegGrade = '"+veggrade[1]+"' and VegCategory = '"+vegcate[1]+"' and RegDate = '"+ date +"'";
                    //System.out.println(setquery);
                    db1.stm.executeUpdate(setquery);
                    finalprice.setText("Live Price: "+liveprice+" Rs");
                    finalprice.setBackground(Color.GREEN);
                }
            }catch(Exception e)
            {
                System.out.println(e);
            }
         
            
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_priceokActionPerformed

    private void basepriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_basepriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_basepriceActionPerformed

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
            java.util.logging.Logger.getLogger(Bidding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bidding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bidding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bidding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String a = null;
                int r = 0;
                
                new Bidding(a,r).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField baseprice;
    private javax.swing.JSpinner bidprice;
    private javax.swing.JTable bidtable;
    private javax.swing.JTextField finalprice;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mandicode;
    private javax.swing.JButton priceok;
    private javax.swing.JPanel rowPanel;
    private javax.swing.JTextField vcat;
    private javax.swing.JTextField vcolour;
    private javax.swing.JTextField vgrade;
    private javax.swing.JTextField vhindi;
    private javax.swing.JTextField vid;
    private javax.swing.JLabel vimage;
    private javax.swing.JTextField vname;
    private javax.swing.JTextField vqua;
    private javax.swing.JTextField vregional;
    private javax.swing.JTextField vtype;
    private javax.swing.JLabel wrphoto;
    private javax.swing.JLabel wsid;
    private javax.swing.JLabel wsname;
    // End of variables declaration//GEN-END:variables
}
