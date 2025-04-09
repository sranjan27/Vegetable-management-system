/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package panchayatoperator;

import Transport.AddVehicle;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vegetabletrading.Mail;
import vegetabletrading.ParabitDB;

/**
 *
 * @author HARSHIT
 */
public class PanchayatData extends javax.swing.JDialog {
    ParabitDB db1,db2;
    int upfarid,uptoid,emailotp;
    String transportid =null,postid = null, farpathimg = null,farpathsign = null,topathimg = null,topathsign = null,date,empid;
    Mail vm;

    /**
     * Creates new form 
     */
    public PanchayatData(java.awt.Frame parent, boolean modal,String a) {
        super(parent, modal);
        initComponents();
        empid = a;
        
        trans.setVisible(false);
        far.setVisible(false);
        topnext.setVisible(false);
        toanext.setVisible(false);
        tobnext.setVisible(false);
        tovupdate.setVisible(false);
        toemail.setEditable(false);
        farpnext.setVisible(false);
        faranext.setVisible(false);
        farqnext.setVisible(false);
        farbnext.setVisible(false);
        faraiupdate.setVisible(false);
        faremail.setEditable(false);
                
        getData();
        
        try
        {                        
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery("SELECT * FROM `transportowner` WHERE PCode = '"+ postid +"' ORDER BY TOID;");            
                      
            //to set date got from data base and set to product table
            while(db1.rs.next())
            {
                String toid = String.valueOf(db1.rs.getInt("TOID"));
                String toname = db1.rs.getString("TOName");
                String todob = db1.rs.getString("DOB");
                String toregmob = db1.rs.getString("RegMobNo");
                String toaltmob = db1.rs.getString("AltMobNo1");
                String toemailid = db1.rs.getString("EmailId");
                String toaadhar = db1.rs.getString("AadharNo");
                String topan = db1.rs.getString("PANNo");
                String todl = db1.rs.getString("DLNo");
                                         
                DefaultTableModel promodel = (DefaultTableModel)transtable.getModel();
                promodel.addRow(new Object[]{toid,toname,todob,toregmob,toaltmob,toemailid,toaadhar,topan,todl});
            }
                                    
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery("SELECT * FROM `farmerreg` WHERE PCode = '"+ postid +"' ORDER BY FID;");            
                      
            //to set date got from data base and set to product table
            while(db1.rs.next())
            {
                String fid = String.valueOf(db1.rs.getInt("FID"));
                String fname = db1.rs.getString("FName");
                String fdob = db1.rs.getString("DOB");
                String fregmob = db1.rs.getString("RegMobNo");
                String faltmob = db1.rs.getString("MobNo");
                String femailid = db1.rs.getString("EmailIDOff");
                String faadhar = db1.rs.getString("AadharNo");
                String fpan = db1.rs.getString("PANNo");
                                                         
                DefaultTableModel promodel = (DefaultTableModel)fartable.getModel();
                promodel.addRow(new Object[]{fid,fname,fdob,fregmob,faltmob,femailid,faadhar,fpan});
            }
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
            LocalDateTime now = LocalDateTime.now();  
            date = (dtf.format(now));
            
            
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
    
    private void getData(){
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select PostingID from employee where empid = "+empid);
            db1.rs.next();
            postid = db1.rs.getString(1);
                        
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select statename from statecode order by statename");
            
            while(db1.rs.next())
            {
                farstate.addItem(db1.rs.getString(1));
                tostate.addItem(db1.rs.getString(1));
            }
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

        bg1 = new javax.swing.ButtonGroup();
        bg2 = new javax.swing.ButtonGroup();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        trans = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        transdef = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transtable = new javax.swing.JTable();
        transpanel = new javax.swing.JPanel();
        transtb = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        toid = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        toname = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        todob = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        toregmob = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        toaltmob = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        toaadhar = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        topan = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        toemail = new javax.swing.JTextField();
        tophoto = new javax.swing.JLabel();
        tophupload = new javax.swing.JButton();
        tosign = new javax.swing.JLabel();
        tosignupload = new javax.swing.JButton();
        topnext = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        todl = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        tootp = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        Verify = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        tohouse = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        tostreet = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        tocity = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        tostate = new javax.swing.JComboBox<>();
        jLabel53 = new javax.swing.JLabel();
        toblock = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        todistrict = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        totehsil = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        topincode = new javax.swing.JTextField();
        tocolony = new javax.swing.JTextField();
        toanext = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        tobank = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        tobranch = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        toaccno = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        toifsc = new javax.swing.JTextField();
        tobnext = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tovtable = new javax.swing.JTable();
        tovupdate = new javax.swing.JButton();
        addveh = new javax.swing.JButton();
        delveh = new javax.swing.JButton();
        toilabel = new javax.swing.JLabel();
        emptoid = new javax.swing.JTextField();
        toibt = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        far = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        fardef = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fartable = new javax.swing.JTable();
        farpanel = new javax.swing.JPanel();
        fartb = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        farname = new javax.swing.JTextField();
        fardob = new javax.swing.JTextField();
        farregmob = new javax.swing.JTextField();
        faraltmob = new javax.swing.JTextField();
        faraadhar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        farpan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        faremail = new javax.swing.JTextField();
        farphoto = new javax.swing.JLabel();
        farsign = new javax.swing.JLabel();
        farphupload = new javax.swing.JButton();
        farsignupload = new javax.swing.JButton();
        farpnext = new javax.swing.JButton();
        farid = new javax.swing.JLabel();
        farotp = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        Verify1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        farhouse = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        farstreet = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        farcolony = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        farvillage = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        farblock = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        fartehsil = new javax.swing.JTextField();
        farpincode = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        fardistrict = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        farstate = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        faranext = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        farlandsize = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        farirr = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        faraed = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        farped = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        farted = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        farexp = new javax.swing.JTextField();
        farqnext = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        farbank = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        farbranch = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        faraccno = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        farkcc = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        farifsc = new javax.swing.JTextField();
        farbnext = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        fartrans = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        farscap = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        farscapcold = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        farsphoneyes = new javax.swing.JRadioButton();
        farsphoneno = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        farcompyes = new javax.swing.JRadioButton();
        farcompno = new javax.swing.JRadioButton();
        faraiupdate = new javax.swing.JButton();
        empfarid = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        farmer = new javax.swing.JButton();
        transport = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 1200, 600));
        setMinimumSize(new java.awt.Dimension(1200, 600));

        trans.setMinimumSize(new java.awt.Dimension(1170, 478));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("   Transport Details ");

        transtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "DOB", "Reg Mob No", "Alt Mob No", "Email ID", "Aadhar", "PAN No", "Driving Licence"
            }
        ));
        transtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transtableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(transtable);

        javax.swing.GroupLayout transdefLayout = new javax.swing.GroupLayout(transdef);
        transdef.setLayout(transdefLayout);
        transdefLayout.setHorizontalGroup(
            transdefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1157, Short.MAX_VALUE)
            .addGroup(transdefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(transdefLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1142, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        transdefLayout.setVerticalGroup(
            transdefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
            .addGroup(transdefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(transdefLayout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLabel38.setText("ID");

        jLabel39.setText("Name");

        jLabel40.setText("DOB");

        jLabel41.setText("Reg Mob No.");

        jLabel42.setText("Alt Mob No.");

        jLabel43.setText("Aadhar No.");

        jLabel44.setText("Pan No.");

        jLabel45.setText("Email ID");

        tophoto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tophoto.setText("           Photo");
        tophoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        tophoto.setPreferredSize(new java.awt.Dimension(134, 172));
        tophoto.setVerifyInputWhenFocusTarget(false);

        tophupload.setText("Upload");
        tophupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tophuploadActionPerformed(evt);
            }
        });

        tosign.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tosign.setText("           Signature");
        tosign.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        tosign.setPreferredSize(new java.awt.Dimension(151, 50));
        tosign.setVerifyInputWhenFocusTarget(false);

        tosignupload.setText("Upload");
        tosignupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tosignuploadActionPerformed(evt);
            }
        });

        topnext.setText("Next");
        topnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topnextActionPerformed(evt);
            }
        });

        jLabel46.setText("Driving Licence No.");

        jButton8.setText("Send OTP");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel60.setText("Otp :");

        Verify.setText("Verify");
        Verify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerifyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toname)
                    .addComponent(todob)
                    .addComponent(toregmob)
                    .addComponent(toaltmob)
                    .addComponent(toid, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(toaadhar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(topan)
                                    .addComponent(todl))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(109, 109, 109)
                                        .addComponent(tophupload))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addComponent(tophoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tosign, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(tosignupload)
                                        .addGap(37, 37, 37))))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(toemail, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Verify, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton8))))
                        .addContainerGap(52, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(tootp, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(topnext)
                        .addGap(50, 50, 50))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(tophoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tophupload))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addComponent(toid, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(toname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43)
                            .addComponent(toaadhar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(todob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44)
                            .addComponent(topan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(toregmob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46)
                            .addComponent(todl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(toaltmob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45)
                            .addComponent(toemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(90, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(tosign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tosignupload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel60)
                        .addComponent(tootp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Verify))
                    .addComponent(topnext))
                .addGap(40, 40, 40))
        );

        transtb.addTab("Personal Details", jPanel6);

        jLabel47.setText("House No.");

        jLabel48.setText("Street");

        jLabel49.setText("Colony");

        jLabel50.setText("City/Village");

        jLabel51.setText("State");

        tostate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose State" }));
        tostate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tostateActionPerformed(evt);
            }
        });

        jLabel53.setText("Block");

        toblock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toblockActionPerformed(evt);
            }
        });

        jLabel54.setText("District");

        todistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose District" }));
        todistrict.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todistrictActionPerformed(evt);
            }
        });

        jLabel56.setText("Tehsil");

        jLabel57.setText("PinCode");

        toanext.setText("Next");
        toanext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toanextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tocity, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tohouse, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tostate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tostreet, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toblock, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(todistrict, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tocolony, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(totehsil)
                    .addComponent(topincode))
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(toanext)
                .addGap(73, 73, 73))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(tohouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(tostreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(tocolony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(toblock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(tocity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(totehsil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(tostate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(todistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57)
                    .addComponent(topincode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(toanext)
                .addGap(57, 57, 57))
        );

        transtb.addTab("Address", jPanel7);

        jLabel52.setText("Bank Name");

        jLabel55.setText("Branch Name");

        jLabel58.setText("Account No.");

        toaccno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toaccnoActionPerformed(evt);
            }
        });

        jLabel59.setText("IFSC Code");

        tobnext.setText("Next");
        tobnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tobnextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toaccno)
                    .addComponent(tobank, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(91, 91, 91)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel55))
                .addGap(30, 30, 30)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toifsc)
                    .addComponent(tobranch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(503, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tobnext)
                .addGap(88, 88, 88))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jLabel55)
                    .addComponent(tobank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tobranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(toaccno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toifsc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                .addComponent(tobnext)
                .addGap(51, 51, 51))
        );

        transtb.addTab("Bank Details", jPanel8);

        tovtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S No.", "Vehicle Type", "Vehicle No.", "Vehicle Model ", "Fuel Type", "Reg No and Exp Date", "Ins No and Exp Date", "Capacity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tovtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tovtableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tovtable);

        tovupdate.setText("Update");
        tovupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tovupdateActionPerformed(evt);
            }
        });

        addveh.setText("Add Vehicle");
        addveh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addvehActionPerformed(evt);
            }
        });

        delveh.setText("Delete Vehicle");
        delveh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delvehActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1145, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tovupdate)
                .addGap(50, 50, 50)
                .addComponent(addveh)
                .addGap(50, 50, 50)
                .addComponent(delveh)
                .addGap(50, 50, 50))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tovupdate)
                    .addComponent(addveh)
                    .addComponent(delveh))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        transtb.addTab("Vehicle Details", jPanel9);

        javax.swing.GroupLayout transpanelLayout = new javax.swing.GroupLayout(transpanel);
        transpanel.setLayout(transpanelLayout);
        transpanelLayout.setHorizontalGroup(
            transpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(transtb)
        );
        transpanelLayout.setVerticalGroup(
            transpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(transtb)
        );

        jLayeredPane3.setLayer(transdef, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane3.setLayer(transpanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane3Layout = new javax.swing.GroupLayout(jLayeredPane3);
        jLayeredPane3.setLayout(jLayeredPane3Layout);
        jLayeredPane3Layout.setHorizontalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(transdef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(transpanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane3Layout.setVerticalGroup(
            jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(transdef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(transpanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        toilabel.setText("Transport Owner ID :");

        toibt.setText("Get");
        toibt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toibtActionPerformed(evt);
            }
        });

        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout transLayout = new javax.swing.GroupLayout(trans);
        trans.setLayout(transLayout);
        transLayout.setHorizontalGroup(
            transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transLayout.createSequentialGroup()
                .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transLayout.createSequentialGroup()
                        .addGap(497, 497, 497)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, transLayout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(toilabel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(emptoid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(toibt)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, transLayout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        transLayout.setVerticalGroup(
            transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addGap(2, 2, 2)
                .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toilabel)
                    .addComponent(emptoid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toibt)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        far.setMinimumSize(new java.awt.Dimension(1170, 478));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("   Framer Details ");

        fardef.setMaximumSize(new java.awt.Dimension(1154, 391));
        fardef.setMinimumSize(new java.awt.Dimension(1154, 391));

        fartable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "DOB", "Reg Mob No", "Alt Mob No", "Email ID", "Aadhar", "PAN No"
            }
        ));
        fartable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fartableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(fartable);

        javax.swing.GroupLayout fardefLayout = new javax.swing.GroupLayout(fardef);
        fardef.setLayout(fardefLayout);
        fardefLayout.setHorizontalGroup(
            fardefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1157, Short.MAX_VALUE)
            .addGroup(fardefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fardefLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1142, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        fardefLayout.setVerticalGroup(
            fardefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
            .addGroup(fardefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(fardefLayout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        farpanel.setMaximumSize(new java.awt.Dimension(1154, 391));
        farpanel.setMinimumSize(new java.awt.Dimension(1154, 391));

        jLabel4.setText("Alt Mob No.");

        jLabel5.setText("Reg Mob No.");

        jLabel6.setText("DOB");

        jLabel7.setText("Name");

        jLabel8.setText("Farmer ID");

        jLabel9.setText("Aadhar No.");

        jLabel10.setText("Pan No.");

        jLabel11.setText("Email ID");

        farphoto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        farphoto.setText("           Photo");
        farphoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        farphoto.setPreferredSize(new java.awt.Dimension(134, 172));
        farphoto.setVerifyInputWhenFocusTarget(false);

        farsign.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        farsign.setText("           Signature");
        farsign.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        farsign.setPreferredSize(new java.awt.Dimension(151, 50));
        farsign.setVerifyInputWhenFocusTarget(false);

        farphupload.setText("Upload");
        farphupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farphuploadActionPerformed(evt);
            }
        });

        farsignupload.setText("Upload");
        farsignupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farsignuploadActionPerformed(evt);
            }
        });

        farpnext.setText("Next");
        farpnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farpnextActionPerformed(evt);
            }
        });

        jLabel61.setText("Otp :");

        Verify1.setText("Verify");
        Verify1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Verify1ActionPerformed(evt);
            }
        });

        jButton7.setText("Send OTP");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(farname, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(fardob)
                    .addComponent(farregmob)
                    .addComponent(faraltmob)
                    .addComponent(farid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(faraadhar)
                    .addComponent(farpan)
                    .addComponent(faremail, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(farotp))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(farphoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(farsign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(farsignupload)
                                .addGap(72, 72, 72))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Verify1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addGap(63, 63, 63)
                                .addComponent(farphupload)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(farpnext)
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(farid, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(farname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(faraadhar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(fardob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(farpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(farregmob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(faremail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(faraltmob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61)
                            .addComponent(farotp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Verify1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(farphoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(farsign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(farsignupload)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(farphupload)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(farpnext)
                .addGap(40, 40, 40))
        );

        fartb.addTab("Personal Details", jPanel1);

        jLabel12.setText("House No.");

        jLabel16.setText("Street");

        jLabel14.setText("Colony");

        jLabel13.setText("City/Village");

        jLabel17.setText("Block");

        farblock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farblockActionPerformed(evt);
            }
        });

        jLabel15.setText("Tehsil");

        jLabel19.setText("PinCode");

        fardistrict.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose District" }));
        fardistrict.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fardistrictActionPerformed(evt);
            }
        });

        jLabel21.setText("District");

        farstate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose State" }));
        farstate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farstateActionPerformed(evt);
            }
        });

        jLabel18.setText("State");

        faranext.setText("Next");
        faranext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faranextActionPerformed(evt);
            }
        });

        jLabel20.setText("Land Size (Acre)");

        jLabel22.setText("Irrigation Mode");

        farirr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Irrigation Mode", "Tube Well", "River", "Canal", "Well", "Other" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(faranext)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(farstate, 0, 150, Short.MAX_VALUE)
                            .addComponent(farhouse)
                            .addComponent(farvillage)
                            .addComponent(farlandsize))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fardistrict, 0, 150, Short.MAX_VALUE)
                            .addComponent(farstreet)
                            .addComponent(farblock)
                            .addComponent(farirr, 0, 1, Short.MAX_VALUE))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(farcolony, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(fartehsil)
                            .addComponent(farpincode))))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14)
                    .addComponent(farhouse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(farstreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(farcolony, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(farvillage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17)
                    .addComponent(farblock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(fartehsil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(farstate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(fardistrict, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(farpincode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(farlandsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(farirr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(faranext)
                .addGap(47, 47, 47))
        );

        fartb.addTab("Address", jPanel2);

        jLabel26.setText("Acadmic Qualification");

        faraed.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Academic Qualification", "10th", "12th", " " }));

        jLabel29.setText("Profesional Qualifcation");

        jLabel27.setText("Technical Qualification");

        jLabel28.setText("Experience");

        farqnext.setText("Next");
        farqnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farqnextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(farted, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(faraed, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(farped)
                    .addComponent(farexp, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(301, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(farqnext)
                .addGap(81, 81, 81))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel29)
                    .addComponent(farped, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(faraed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(farexp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(farted, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                .addComponent(farqnext)
                .addGap(63, 63, 63))
        );

        fartb.addTab("Qualificaton", jPanel3);

        jLabel23.setText("Bank Name");

        jLabel24.setText("Branch Name");

        jLabel25.setText("Account No.");

        jLabel35.setText("Kisan Credit  Card");

        jLabel36.setText("IFSC Code");

        farbnext.setText("Next");
        farbnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farbnextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(faraccno, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(farbank, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(farifsc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(farbranch)
                    .addComponent(farkcc, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(398, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(farbnext)
                .addGap(59, 59, 59))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(farbank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(farbranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel25)
                        .addComponent(faraccno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel35)
                        .addComponent(farkcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(farifsc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(farbnext)
                .addGap(49, 49, 49))
        );

        fartb.addTab("Bank Details", jPanel4);

        jLabel30.setText("Transport");

        fartrans.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Transport Mode", "Truck", "Mini Truck", "Pickup", "Loading Auto", "Tractor with Trolly", " " }));

        jLabel31.setText("Storage Capacity");

        jLabel32.setText("Cold Storage Capacity");

        jLabel33.setText("SmartPhone");

        bg1.add(farsphoneyes);
        farsphoneyes.setText("Yes");

        bg1.add(farsphoneno);
        farsphoneno.setText("No");

        jLabel34.setText("Computer");

        bg2.add(farcompyes);
        farcompyes.setText("Yes");

        bg2.add(farcompno);
        farcompno.setText("No");

        faraiupdate.setText("Update");
        faraiupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faraiupdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(farscap, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fartrans, javax.swing.GroupLayout.Alignment.LEADING, 0, 180, Short.MAX_VALUE)
                    .addComponent(farscapcold))
                .addGap(102, 102, 102)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(farsphoneyes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(farcompyes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(farcompno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(farsphoneno, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(383, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(faraiupdate)
                .addGap(53, 53, 53))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(fartrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(farscap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(farsphoneyes)
                            .addComponent(farsphoneno))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(farcompyes)
                            .addComponent(farcompno))))
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(farscapcold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addComponent(faraiupdate)
                .addGap(50, 50, 50))
        );

        fartb.addTab("Additonal Information", jPanel5);

        javax.swing.GroupLayout farpanelLayout = new javax.swing.GroupLayout(farpanel);
        farpanel.setLayout(farpanelLayout);
        farpanelLayout.setHorizontalGroup(
            farpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fartb)
        );
        farpanelLayout.setVerticalGroup(
            farpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fartb)
        );

        jLayeredPane2.setLayer(fardef, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(farpanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fardef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(farpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fardef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(farpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setText("Farmer ID");

        jButton5.setText("Get");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout farLayout = new javax.swing.GroupLayout(far);
        far.setLayout(farLayout);
        farLayout.setHorizontalGroup(
            farLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(farLayout.createSequentialGroup()
                .addGroup(farLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(farLayout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(farLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(farLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(empfarid, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(24, 24, 24))
        );
        farLayout.setVerticalGroup(
            farLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(farLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addGap(2, 2, 2)
                .addGroup(farLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(farLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(empfarid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jButton5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jLayeredPane1.setLayer(trans, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(far, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(trans, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(far, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(trans, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(far, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        farmer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        farmer.setText("Farmer");
        farmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farmerActionPerformed(evt);
            }
        });

        transport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        transport.setText("Transport");
        transport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transportActionPerformed(evt);
            }
        });

        jLabel37.setBackground(new java.awt.Color(0, 0, 0));
        jLabel37.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel37.setText("  PANCHAYAT DATA");
        jLabel37.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(268, 268, 268)
                .addComponent(farmer, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(transport)
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transport)
                    .addComponent(farmer)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void transportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transportActionPerformed

        far.setVisible(false);
        trans.setVisible(true);
        transpanel.setVisible(false);
        transdef.setVisible(true);
        topnext.setVisible(false);
        toanext.setVisible(false);
        tobnext.setVisible(false);
        tovupdate.setVisible(false);
        toemail.setEditable(false);
        farpnext.setVisible(false);
        faranext.setVisible(false);
        farqnext.setVisible(false);
        farbnext.setVisible(false);
        faraiupdate.setVisible(false);
        faremail.setEditable(false);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_transportActionPerformed

    private void farmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farmerActionPerformed

        trans.setVisible(false);
        far.setVisible(true);
        farpanel.setVisible(false);
        fardef.setVisible(true);
        topnext.setVisible(false);
        toanext.setVisible(false);
        tobnext.setVisible(false);
        tovupdate.setVisible(false);
        toemail.setEditable(false);
        farpnext.setVisible(false);
        faranext.setVisible(false);
        farqnext.setVisible(false);
        farbnext.setVisible(false);
        faraiupdate.setVisible(false);
        faremail.setEditable(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_farmerActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       
        String farmerid = empfarid.getText();
        String fid = null,fname =null,fdob = null,fregmob = null,faltmob = null,femailid = null,faadhar = null,fpan = null,fhouse = null,fstreet =null,fcolony = null,fcity = null,fblock = null,ftehsil = null,fdist = null,fstate = null,fpincode = null,flandsize = null,firr = null,faed = null,fped = null,fted = null,fexp = null,fbank = null,fbranch = null,faccno = null,fkcc = null,fifsc = null,ftrans = null,fscap = null,fscapcold = null,fsmart = null,fcomp = null;
        try
        {
            String sql = ("SELECT * FROM `farmerreg` WHERE FID = '"+ farmerid +"' and PCode = '"+ postid +"';");
            db1 = new ParabitDB();
            db1.rs = db1.stm.executeQuery(sql);            
                     
            //to set date got from data base for farmer
            while(db1.rs.next())
            {
                fid = String.valueOf(db1.rs.getInt("FID"));
                fname = db1.rs.getString("FName");
                fdob = db1.rs.getString("DOB");
                fregmob = db1.rs.getString("RegMobNo");
                faltmob = db1.rs.getString("MobNo");
                femailid = db1.rs.getString("EmailIDOff");
                faadhar = db1.rs.getString("AadharNo");
                fpan = db1.rs.getString("PANNo");
                BufferedImage pbi = ImageIO.read(db1.rs.getBinaryStream("FPhoto"));
                Image pimg = pbi.getScaledInstance(134, 172, Image.SCALE_SMOOTH );
                BufferedImage sbi = ImageIO.read(db1.rs.getBinaryStream("Signature"));
                Image simg = sbi.getScaledInstance(151, 50, Image.SCALE_SMOOTH );
                fhouse = db1.rs.getString("HouseNo");
                fstreet = db1.rs.getString("Street");
                fcolony = db1.rs.getString("Colony");
                fcity = db1.rs.getString("CityVillage");
                fblock = db1.rs.getString("Block");
                ftehsil = db1.rs.getString("Tehsil");
                fdist = db1.rs.getString("Dist");
                fstate = db1.rs.getString("State");
                fpincode = db1.rs.getString("Pincode");
                flandsize = db1.rs.getString("TotalSize");
                firr = db1.rs.getString("Irrigation");
                faed = db1.rs.getString("FAEdu");
                fped = db1.rs.getString("FPEdu");
                fted = db1.rs.getString("FTEdu");
                fexp = db1.rs.getString("FExp");
                fbank = db1.rs.getString("BName");
                fbranch = db1.rs.getString("BBranch");
                faccno = db1.rs.getString("BAccNo");
                fkcc = db1.rs.getString("KCC");
                fifsc = db1.rs.getString("BIFSC");
                ftrans = db1.rs.getString("TVehical");
                fscap = db1.rs.getString("SCap");
                fscapcold = db1.rs.getString("SCapCold");
                fsmart = db1.rs.getString("SmartPhone");
                fcomp = db1.rs.getString("Comp");

                farid.setText(fid);
                farname.setText(fname);
                fardob.setText(fdob);
                farregmob.setText(fregmob);
                faraltmob.setText(faltmob);
                faremail.setText(femailid);
                faraadhar.setText(faadhar);
                farpan.setText(fpan);
                farphoto.setIcon(new ImageIcon(pimg));
                farsign.setIcon(new ImageIcon(simg));
                farhouse.setText(fhouse);
                farstreet.setText(fstreet);
                farcolony.setText(fcolony);
                farvillage.setText(fcity);
                farblock.setText(fblock);
                fartehsil.setText(ftehsil);                
                farstate.setSelectedItem(fstate);
                fardistrict.setSelectedItem(fdist);
                farpincode.setText(fpincode);
                farlandsize.setText(flandsize);
                farirr.setSelectedItem(firr);
                faraed.setSelectedItem(faed);
                farped.setText(fped);
                farexp.setText(fexp);
                farted.setText(fted);
                farbank.setText(fbank);
                farbranch.setText(fbranch);
                faraccno.setText(faccno);
                farkcc.setText(fkcc);
                farifsc.setText(fifsc);
                fartrans.setSelectedItem(ftrans);
                farscap.setText(fscap);
                farscapcold.setText(fscapcold);
                if(fsmart == "Y"){
                    farsphoneyes.setSelected(true);
                    farsphoneno.setSelected(false);
                }else{
                    farsphoneno.setSelected(true);
                    farsphoneyes.setSelected(false);
                }
                
                if(fcomp == "Y"){
                    farcompyes.setSelected(true);
                }else{
                    farcompno.setSelected(true);
                }      
            }
            
            db2 = new ParabitDB();
            db2.rs = db2.stm.executeQuery(sql);
            boolean check = db2.rs.next();
            if(check){
                fardef.setVisible(false);
                farpanel.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Farmer Id "+farmerid+" not found...");
                fardef.setVisible(true);
                farpanel.setVisible(false);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
   
        farpnext.setVisible(false);
        faranext.setVisible(false);
        farqnext.setVisible(false);
        farbnext.setVisible(false);
        faraiupdate.setVisible(false);
        faremail.setEditable(false);
        farphupload.setVisible(false);
        farsignupload.setVisible(false);
        fartb.setSelectedIndex(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void toibtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toibtActionPerformed
        
        transportid = emptoid.getText();
        String tid = null,tname =null,tdob = null,tregmob = null,taltmob = null,temailid = null,taadhar = null,tpan = null,tdl = null,thouse = null,tstreet =null,tcolony = null,tcity = null,tblock = null,ttehsil = null,tdist = null,tstate = null,tpincode = null,tphoto = null,tsign = null,tbank = null,tbranch = null,taccno = null,tifsc = null;
        try
        {    
            String sql = ("SELECT * FROM `transportowner` WHERE TOID = '"+ transportid +"' and PCode = '"+ postid +"';");
            db1 = new ParabitDB();
            
            db1.rs = db1.stm.executeQuery(sql);
            
            //to set date got from data base for transport owner
            while(db1.rs.next())
            {
                tid = String.valueOf(db1.rs.getInt("TOID"));
                tname = db1.rs.getString("TOName");
                tdob = db1.rs.getString("DOB");
                tregmob = db1.rs.getString("RegMobNo");
                taltmob = db1.rs.getString("AltMobNo1");
                temailid = db1.rs.getString("EmailId");
                taadhar = db1.rs.getString("AadharNo");
                tpan = db1.rs.getString("PANNo");
                tdl = db1.rs.getString("DLNo");
                BufferedImage pbi = ImageIO.read(db1.rs.getBinaryStream("TOPhoto"));
                Image pimg = pbi.getScaledInstance(134, 172, Image.SCALE_SMOOTH );
                BufferedImage sbi = ImageIO.read(db1.rs.getBinaryStream("Signature"));
                Image simg = sbi.getScaledInstance(151, 50, Image.SCALE_SMOOTH );
                thouse = db1.rs.getString("HouseNo");
                tstreet = db1.rs.getString("Street");
                tcolony = db1.rs.getString("Colony");
                tcity = db1.rs.getString("CityVillage");
                tblock = db1.rs.getString("Block");
                ttehsil = db1.rs.getString("Tehsil");
                tdist = db1.rs.getString("Dist");
                tstate = db1.rs.getString("State");
                tpincode = db1.rs.getString("Pincode");
                tbank = db1.rs.getString("BName");
                tbranch = db1.rs.getString("BBranch");
                taccno = db1.rs.getString("BAccNo");
                tifsc = db1.rs.getString("BIFSC");
                               
                toid.setText(tid);
                toname.setText(tname);
                todob.setText(tdob);
                toregmob.setText(tregmob);
                toaltmob.setText(taltmob);
                toemail.setText(temailid);
                toaadhar.setText(taadhar);
                topan.setText(tpan);
                todl.setText(tdl);
                tophoto.setIcon(new ImageIcon(pimg));
                tosign.setIcon(new ImageIcon(simg));
                tohouse.setText(thouse);
                tostreet.setText(tstreet);
                tocolony.setText(tcolony);
                tocity.setText(tcity);
                toblock.setText(tblock);
                totehsil.setText(ttehsil);                
                tostate.setSelectedItem(tstate);
                todistrict.setSelectedItem(tdist);
                topincode.setText(tpincode);
                tobank.setText(tbank);
                tobranch.setText(tbranch);
                toaccno.setText(taccno);
                toifsc.setText(tifsc);
                uptoid = Integer.parseInt(toid.getText());          
            }
            
            db2 = new ParabitDB();
            db2.rs = db2.stm.executeQuery(sql);
            boolean check = db2.rs.next();
            if(check){
                transdef.setVisible(false);
                transpanel.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Transport Owner Id "+transportid+" not found...");
                transdef.setVisible(true);
                transpanel.setVisible(false);
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }       
        
        //for vehicle details
        try {
            String sql = ("SELECT * FROM `vehicledetails` WHERE TOID = '"+ transportid +"' and PCode = '"+ postid +"';");
            db1 = new ParabitDB();
            
            DefaultTableModel model = (DefaultTableModel)tovtable.getModel();
            model.setRowCount(0);
            
            db1.rs = db1.stm.executeQuery(sql);
            int sno = 0;
            //to set date got from data base for transport owner vehicles
            while(db1.rs.next())
            {
                sno = sno + 1;
                String vehtype = db1.rs.getString("VehicleType");
                String vehno = db1.rs.getString("VehicleNo");
                String vehmodel = db1.rs.getString("VehicleModel");
                String fueltype = db1.rs.getString("FuelType");
                String regnodt = db1.rs.getString("VehRegNo")+","+db1.rs.getString("RegExpDate");
                String insnodt = db1.rs.getString("VehInsNo")+","+db1.rs.getString("InsExpDate");
                String cap = db1.rs.getString("Capacity");
                                                         
                DefaultTableModel promodel = (DefaultTableModel)tovtable.getModel();
                promodel.addRow(new Object[]{sno,vehtype,vehno,vehmodel,fueltype,regnodt,insnodt,cap});
            }  
        } catch (Exception e)
        {
            System.out.println(e);      
        }
        
        
        tophupload.setVisible(false);
        tosignupload.setVisible(false);
        topnext.setVisible(false);
        toanext.setVisible(false);
        tobnext.setVisible(false);
        tovupdate.setVisible(false);
        addveh.setVisible(false);
        delveh.setVisible(false);
        toemail.setEditable(false);
        
        
        transtb.setSelectedIndex(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_toibtActionPerformed

    private void farphuploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farphuploadActionPerformed
              
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        farpathimg = f.getAbsolutePath();
        //pathimg = path;
        //System.out.println(farpathimg);
                        
        try {
            BufferedImage bi = ImageIO.read(new File(farpathimg));
            Image img = bi.getScaledInstance(134, 172, Image.SCALE_SMOOTH );
            ImageIcon icon = new ImageIcon(img);
            farphoto.setIcon(icon);
          
            // TODO add your handling code here:
        } catch (Exception e) {
            System.out.println(e);
        }

        String query = "UPDATE `farmerreg` SET Fphoto = ?";
        db1 = new ParabitDB();
        try {
            db1.pst = db1.con.prepareStatement(query);
            
            InputStream img = new FileInputStream(farpathimg);
            
            db1.pst.setBlob(1, img);
            
            db1.pst.executeUpdate();
            
            // TODO add your handling code here:
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }//GEN-LAST:event_farphuploadActionPerformed

    private void farsignuploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farsignuploadActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        farpathsign = f.getAbsolutePath();
        //pathsign = path;

        //System.out.println(farpathsign);
        try {
            BufferedImage bi = ImageIO.read(new File(farpathsign));
            Image im = bi.getScaledInstance(151, 50, Image.SCALE_SMOOTH );
            ImageIcon icon = new ImageIcon(im);
            farsign.setIcon(icon);

            // TODO add your handling code here:
        } catch (IOException ex) {
            //Logger.getLogger(FarmerReg.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "UPDATE `farmerreg` SET Signature = ?";
        db1 = new ParabitDB();
        try {
            db1.pst = db1.con.prepareStatement(query);
            
            InputStream sign = new FileInputStream(farpathsign);
            
            db1.pst.setBlob(1, sign);
            
            db1.pst.executeUpdate();
            
            // TODO add your handling code here:
        } catch (Exception e) {
            System.out.println(e);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_farsignuploadActionPerformed

    private void farblockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farblockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_farblockActionPerformed

    private void fardistrictActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fardistrictActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fardistrictActionPerformed

    private void farstateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farstateActionPerformed

        String state = farstate.getSelectedItem().toString();
                
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select distinct distname from districtcode where statecode = (select statecode from statecode where statename = '" + state + "') order by distname");
            
            while(db1.rs.next())
            {
                fardistrict.addItem(db1.rs.getString(1));  
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_farstateActionPerformed

    private void farpnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farpnextActionPerformed

        String s1,s2,s3,s4,s5,s6,s7,s8;
        
        upfarid = Integer.parseInt(farid.getText());
        s2 = farname.getText();
        s3 = fardob.getText();
        s4 = farregmob.getText();
        s5 = faraltmob.getText();
        s6 = faraadhar.getText();        
        s7 = farpan.getText();
        s8 = faremail.getText();
        //s9 = "hi";
                                
        String sql = "UPDATE `farmerreg` SET FName ='"+s2+"', DOB ='"+s3+"', RegMobNo ='"+s4+"', MobNo ='"+s5+"', AadharNo ='"+s6+"', PANNo ='"+s7+"', EmailIdOff ='"+s8+"', RegDate ='"+date+"' where FID = " + upfarid;
        //System.out.println(sql);
                
        db1 = new ParabitDB();
        try
        {
             db1.stm.executeUpdate(sql);
            
        }catch(Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(this,"Fill All The Details...");
        }
        
        int i = fartb.getSelectedIndex();
        fartb.setSelectedIndex(i+1);
        // TODO add your handling code here:
    }//GEN-LAST:event_farpnextActionPerformed

    private void faranextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faranextActionPerformed

        String s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11;
        
        s1 = farhouse.getText();
        s2 = farstreet.getText();
        s3 = farcolony.getText();
        s4 = farvillage.getText();
        s5 = farblock.getText();
        s6 = fartehsil.getText();        
        s7 = (String)farstate.getSelectedItem();
        s8 = (String)fardistrict.getSelectedItem();
        s9 = farpincode.getText();
        s10 = farlandsize.getText(); 
        s11 = (String)farirr.getSelectedItem();
        //s9 = "hi";
                
        String sql = "UPDATE farmerreg SET HouseNo ='"+s1+"', Street ='"+s2+"', Colony ='"+s3+"', CityVillage ='"+s4+"', Block ='"+s5+"', Tehsil ='"+s6+"', State ='"+s7+"', Dist ='"+s8+"', Pincode ='"+s9+"', TotalSize ='"+s10+"', Irrigation ='"+s11+"', RegDate ='"+date+"' where FID = " + upfarid;
        //System.out.println(sql);
                
        db1 = new ParabitDB();
        try
        {
            db1.stm.executeUpdate(sql);
            
        }catch(Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(this,"Fill All The Details...");
        }
        
        int i = fartb.getSelectedIndex();
        fartb.setSelectedIndex(i+1);
        // TODO add your handling code here:
    }//GEN-LAST:event_faranextActionPerformed

    private void farqnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farqnextActionPerformed

        String s1,s2,s3,s4;
        
        s1 = (String)faraed.getSelectedItem();
        s2 = farted.getText();
        s3 = farped.getText();
        s4 = farexp.getText();
                        
        String iqs = "UPDATE farmerreg SET FAEdu ='"+s1+"', FTEdu ='"+s2+"', FPEdu ='"+s3+"', FExp ='"+s4+"', RegDate ='"+date+"' where FID = " + upfarid;
        //System.out.println(iqs);
        
        // To insert values of this tab in database
        db1 = new ParabitDB();
        try
        {
            db1.stm.executeUpdate(iqs);
        
        }catch(Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(this,"Fill All The Details...");
        }
        
        int i = fartb.getSelectedIndex();
        fartb.setSelectedIndex(i+1);
        // TODO add your handling code here:
    }//GEN-LAST:event_farqnextActionPerformed

    private void farbnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farbnextActionPerformed

        String s1,s2,s3,s4,s5;
        
        s1 = farbank.getText();
        s2 = faraccno.getText();
        s3 = farifsc.getText();
        s4 = farbranch.getText();
        s5 = farkcc.getText();
        
        String ibs = "UPDATE farmerreg SET BName ='"+s1+"', BAccNo ='"+s2+"', BIFSC ='"+s3+"', BBranch ='"+s4+"', KCC ='"+s5+"', RegDate ='"+date+"' where FID = " + upfarid;
        //System.out.println(ibs);
        
        // To insert values of this tab in database
        db1 = new ParabitDB();
        try
        {
            db1.stm.executeUpdate(ibs);
        
        }catch(Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(this,"Fill All The Details...");
        }
        
        int i = fartb.getSelectedIndex();
        fartb.setSelectedIndex(i+1);
        // TODO add your handling code here:
    }//GEN-LAST:event_farbnextActionPerformed

    private void faraiupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faraiupdateActionPerformed

        String s1,s2,s3,s4,s5,s6,s7,sp ="",comp="";
        
        s1 = (String)fartrans.getSelectedItem();
        s2 = farscap.getText();
        s3 = farscapcold.getText();
        if(farsphoneyes.isSelected())
        {
            sp = "Y";
        }
        if(farsphoneno.isSelected())
        {
            sp = "N";
        }
        s5 = sp ;
        if(farcompyes.isSelected())
        {
            comp = "Y";
        }
        if(farcompno.isSelected())
        {
            comp = "N";
        }
        s6 = comp;
              
        String iais = "UPDATE farmerreg SET TVehical ='"+s1+"', SCap ='"+s2+"', SCapCold ='"+s3+"', SmartPhone ='"+s5+"', Comp ='"+s6+"', RegDate ='"+date+"' where FID = " + upfarid;
        //System.out.println(iais);
        
        // To insert values of this tab in database
        db1 = new ParabitDB();
        try
        {
            db1.stm.executeUpdate(iais);
            JOptionPane.showMessageDialog(this,"Updated Sucessfully...");
        
        }catch(Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(this,"Fill All The Details...");
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_faraiupdateActionPerformed

    private void tophuploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tophuploadActionPerformed
 
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        topathimg = f.getAbsolutePath();
        //pathimg = path;
        //System.out.println(topathimg);
                        
        try {
            BufferedImage bi = ImageIO.read(new File(topathimg));
            Image img = bi.getScaledInstance(134, 172, Image.SCALE_SMOOTH );
            ImageIcon icon = new ImageIcon(img);
            tophoto.setIcon(icon);
          
            // TODO add your handling code here:
        } catch (Exception e) {
            System.out.println(e);
        }

        String query = "UPDATE `transportowner` SET TOPhoto = ?";
        db1 = new ParabitDB();
        try {
            db1.pst = db1.con.prepareStatement(query);
            
            InputStream img = new FileInputStream(topathimg);
            
            db1.pst.setBlob(1, img);
            
            db1.pst.executeUpdate();
            
            // TODO add your handling code here:
        } catch (Exception e) {
            System.out.println(e);
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_tophuploadActionPerformed

    private void tosignuploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tosignuploadActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        topathsign = f.getAbsolutePath();
        //pathsign = path;

        //System.out.println(topathsign);
        try {
            BufferedImage bi = ImageIO.read(new File(topathsign));
            Image im = bi.getScaledInstance(151, 50, Image.SCALE_SMOOTH );
            ImageIcon icon = new ImageIcon(im);
            tosign.setIcon(icon);

            // TODO add your handling code here:
        } catch (IOException ex) {
            //Logger.getLogger(FarmerReg.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "UPDATE `transportowner` SET Signature = ?";
        db1 = new ParabitDB();
        try {
            db1.pst = db1.con.prepareStatement(query);
            
            InputStream sign = new FileInputStream(topathsign);
            
            db1.pst.setBlob(1, sign);
            
            db1.pst.executeUpdate();
            
            // TODO add your handling code here:
        } catch (Exception e) {
            System.out.println(e);
        }
        
// TODO add your handling code here:
    }//GEN-LAST:event_tosignuploadActionPerformed

    private void topnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topnextActionPerformed

        String s1,s2,s3,s4,s5,s6,s7,s8,s9;
        
        s2 = toname.getText();
        s3 = todob.getText();
        s4 = toregmob.getText();
        s5 = toaltmob.getText();
        s6 = toaadhar.getText();        
        s7 = topan.getText();
        s8 = toemail.getText();
        s9 = todl.getText();
        //s9 = "hi";
                                
        String sql = "UPDATE `transportowner` SET TOName ='"+s2+"', DOB ='"+s3+"', RegMobNo ='"+s4+"', AltMobNo1 ='"+s5+"', AadharNo ='"+s6+"', PANNo ='"+s7+"', EmailId ='"+s8+"', DLNo ='"+s9+"', RegDate ='"+date+"' where TOID = " + uptoid;
        System.out.println(sql);
                
        db1 = new ParabitDB();
        try
        {
             db1.stm.executeUpdate(sql);
            
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        int i = transtb.getSelectedIndex();
        transtb.setSelectedIndex(i+1);
        
        // TODO add your handling code here:
    }//GEN-LAST:event_topnextActionPerformed

    private void tostateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tostateActionPerformed

        String state = tostate.getSelectedItem().toString();
                
        db1 = new ParabitDB();
        try
        {
            db1.rs = db1.stm.executeQuery("select distinct distname from districtcode where statecode = (select statecode from statecode where statename = '" + state + "') order by distname");
            
            while(db1.rs.next())
            {
                fardistrict.addItem(db1.rs.getString(1));  
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tostateActionPerformed

    private void toblockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toblockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toblockActionPerformed

    private void todistrictActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todistrictActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_todistrictActionPerformed

    private void toanextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toanextActionPerformed

        String s1,s2,s3,s4,s5,s6,s7,s8,s9;

        s1 = tohouse.getText();
        s2 = tostreet.getText();
        s3 = tocolony.getText();
        s4 = tocity.getText();
        s5 = toblock.getText();
        s6 = totehsil.getText();
        s7 = (String)tostate.getSelectedItem();
        s8 = (String)todistrict.getSelectedItem();
        s9 = topincode.getText();
        
        String sql = "UPDATE transportowner SET HouseNo ='"+s1+"', Street ='"+s2+"', Colony ='"+s3+"', CityVillage ='"+s4+"', Block ='"+s5+"', Tehsil ='"+s6+"', State ='"+s7+"', Dist ='"+s8+"', Pincode ='"+s9+"', RegDate ='"+date+"' where TOID = " + uptoid;
        System.out.println(sql);

        // To insert values of this tab in database
        db1 = new ParabitDB();
        try
        {
            db1.stm.executeUpdate(sql);

        }catch(Exception e)
        {
            System.out.println(e);
        }

        //To go to next tab
        int i = transtb.getSelectedIndex();
        transtb.setSelectedIndex(i+1);

        // TODO add your handling code here:
    }//GEN-LAST:event_toanextActionPerformed

    private void toaccnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toaccnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toaccnoActionPerformed

    private void tobnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tobnextActionPerformed

        String s1,s2,s3,s4;
        s1 = tobank.getText();
        s2 = toaccno.getText();
        s3 = toifsc.getText();
        s4 = tobranch.getText();

        String sql = "UPDATE transportowner SET BName ='"+s1+"', BAccNo ='"+s2+"', BIFSC ='"+s3+"', BBranch ='"+s4+"', RegDate ='"+date+"' where TOID = " + uptoid;
        System.out.println(sql);

        // To insert values of this tab in database
        db1 = new ParabitDB();
        try
        {
            db1.stm.executeUpdate(sql);
            
        }catch(Exception e)
        {
            System.out.println(e);
        }

        //To go to next tab
        int i = transtb.getSelectedIndex();
        transtb.setSelectedIndex(i+1);

        // TODO add your handling code here:
    }//GEN-LAST:event_tobnextActionPerformed

    private void tovupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tovupdateActionPerformed

        DefaultTableModel model = (DefaultTableModel)tovtable.getModel();
        
        db1 = new ParabitDB();
        try{            
            for(int i =0; i<model.getRowCount(); i++){
                
                int sno = Integer.parseInt(model.getValueAt(i,0).toString());
                //String vehtype = model.getValueAt(i, 1).toString();
                String vehno = model.getValueAt(i, 2).toString();
                String vehmodel = model.getValueAt(i, 3).toString();
                String fueltype = model.getValueAt(i, 4).toString();
                String regnodt = model.getValueAt(i, 5).toString();
                String insnodt = model.getValueAt(i, 6).toString();
                String cap = model.getValueAt(i, 7).toString();
                              
                String[] reg = regnodt.split(",");
                String[] ins = insnodt.split(",");
                                                                                                   
                String sql = "UPDATE vehicledetails SET VehicleNo ='"+vehno+"', VehicleModel ='"+vehmodel+"', FuelType ='"+fueltype+"', VehRegNo ='"+reg[0]+"', RegExpDate ='"+reg[1]+"', VehInsNo ='"+ins[0]+"', InsExpDate ='"+ins[1]+"', Capacity ='"+cap+"', VehRegDate ='"+date+"' where TOID = "+uptoid+" AND SNo = '"+sno+"'" ;
                System.out.println(sql);
                
                db1.stm.executeUpdate(sql); 
                
            }
            JOptionPane.showMessageDialog(this,"Update Successfully...");
        }catch(Exception e)
        {
            System.out.println(e);
            
        }   
        // TODO add your handling code here:
    }//GEN-LAST:event_tovupdateActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String email = toemail.getText();

        if(email!= "null")
        {
            vm = new Mail(email);
            emailotp = vm.oneTimePass();
            System.out.println(emailotp);
        }
        else{
            JOptionPane.showMessageDialog(this,"Enter valid email...");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void VerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerifyActionPerformed

        int otpval = Integer.parseInt(tootp.getText());
        try
        {
            if(otpval==emailotp){
                JOptionPane.showMessageDialog(null,"OTP Verified...");
                topnext.setVisible(true);
                toanext.setVisible(true);
                tobnext.setVisible(true);
                tovupdate.setVisible(true);
                toemail.setEditable(true);
                addveh.setVisible(true);
                delveh.setVisible(true);
                tophupload.setVisible(true);
                tosignupload.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null,"Enter Valid OTP!!!");
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        tootp.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_VerifyActionPerformed

    private void Verify1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Verify1ActionPerformed

        int otpval = Integer.parseInt(farotp.getText());
        try
        {
            if(otpval==emailotp){
                JOptionPane.showMessageDialog(null,"OTP Verified...");
                farpnext.setVisible(true);
                faranext.setVisible(true);
                farqnext.setVisible(true);
                farbnext.setVisible(true);
                faraiupdate.setVisible(true);
                faremail.setEditable(true);
                farphupload.setVisible(true);
                farsignupload.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null,"Enter Valid OTP!!!");
            }

        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        
        farotp.setText("");

        // TODO add your handling code here:
    }//GEN-LAST:event_Verify1ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       String email = faremail.getText();

        if(email!= "null")
        {
            vm = new Mail(email);
            emailotp = vm.oneTimePass();
            System.out.println(emailotp);
        }
        else{
            JOptionPane.showMessageDialog(this,"Enter valid email...");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void transtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transtableMouseClicked
        int i = transtable.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)transtable.getModel();
        String id = model.getValueAt(i,0).toString();
        emptoid.setText(id);


        // TODO add your handling code here:
    }//GEN-LAST:event_transtableMouseClicked

    private void fartableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fartableMouseClicked
        int i = fartable.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)fartable.getModel();
        String id = model.getValueAt(i,0).toString();
        empfarid.setText(id);

        // TODO add your handling code here:
    }//GEN-LAST:event_fartableMouseClicked

    private void addvehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addvehActionPerformed
        AddVehicle vh = new AddVehicle(null,false,transportid,empid);
        vh.setVisible(true);
        this.setVisible(false);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_addvehActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        transpanel.setVisible(false);
        emptoid.setText("");
        transdef.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        farpanel.setVisible(false);
        empfarid.setText("");
        fardef.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tovtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tovtableMouseClicked
        

        // TODO add your handling code here:
    }//GEN-LAST:event_tovtableMouseClicked

    private void delvehActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delvehActionPerformed
        int i = tovtable.getSelectedRow();
        
        DefaultTableModel model = (DefaultTableModel)tovtable.getModel();
        
        String vehno = model.getValueAt(i,2).toString();
        
        String query = "DELETE FROM `vehicledetails` WHERE TOID = '"+ transportid +"' and `VehicleNo`='"+vehno+"'";
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
        
        //for vehicle details
        try {
            String sql = ("SELECT * FROM `vehicledetails` WHERE TOID = '"+ transportid +"' and PCode = '"+ postid +"';");
            db1 = new ParabitDB();
            
            model.setRowCount(0);
            
            db1.rs = db1.stm.executeQuery(sql);
            int sno = 0;
            //to set date got from data base for transport owner vehicles
            while(db1.rs.next())
            {
                sno = sno + 1;
                String vehtype = db1.rs.getString("VehicleType");
                vehno = db1.rs.getString("VehicleNo");
                String vehmodel = db1.rs.getString("VehicleModel");
                String fueltype = db1.rs.getString("FuelType");
                String regnodt = db1.rs.getString("VehRegNo")+","+db1.rs.getString("RegExpDate");
                String insnodt = db1.rs.getString("VehInsNo")+","+db1.rs.getString("InsExpDate");
                String cap = db1.rs.getString("Capacity");
                                                         
                DefaultTableModel promodel = (DefaultTableModel)tovtable.getModel();
                promodel.addRow(new Object[]{sno,vehtype,vehno,vehmodel,fueltype,regnodt,insnodt,cap});
            }  
        } catch (Exception e)
        {
            System.out.println(e);      
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_delvehActionPerformed

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
            java.util.logging.Logger.getLogger(PanchayatData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanchayatData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanchayatData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanchayatData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String a = null;
                PanchayatData dialog = new PanchayatData(new javax.swing.JFrame(), true,a);
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
    private javax.swing.JButton Verify;
    private javax.swing.JButton Verify1;
    private javax.swing.JButton addveh;
    private javax.swing.ButtonGroup bg1;
    private javax.swing.ButtonGroup bg2;
    private javax.swing.JButton delveh;
    private javax.swing.JTextField empfarid;
    private javax.swing.JTextField emptoid;
    private javax.swing.JPanel far;
    private javax.swing.JTextField faraadhar;
    private javax.swing.JTextField faraccno;
    private javax.swing.JComboBox<String> faraed;
    private javax.swing.JButton faraiupdate;
    private javax.swing.JTextField faraltmob;
    private javax.swing.JButton faranext;
    private javax.swing.JTextField farbank;
    private javax.swing.JTextField farblock;
    private javax.swing.JButton farbnext;
    private javax.swing.JTextField farbranch;
    private javax.swing.JTextField farcolony;
    private javax.swing.JRadioButton farcompno;
    private javax.swing.JRadioButton farcompyes;
    private javax.swing.JPanel fardef;
    private javax.swing.JComboBox<String> fardistrict;
    private javax.swing.JTextField fardob;
    private javax.swing.JTextField faremail;
    private javax.swing.JTextField farexp;
    private javax.swing.JTextField farhouse;
    private javax.swing.JLabel farid;
    private javax.swing.JTextField farifsc;
    private javax.swing.JComboBox<String> farirr;
    private javax.swing.JTextField farkcc;
    private javax.swing.JTextField farlandsize;
    private javax.swing.JButton farmer;
    private javax.swing.JTextField farname;
    private javax.swing.JTextField farotp;
    private javax.swing.JTextField farpan;
    private javax.swing.JPanel farpanel;
    private javax.swing.JTextField farped;
    private javax.swing.JLabel farphoto;
    private javax.swing.JButton farphupload;
    private javax.swing.JTextField farpincode;
    private javax.swing.JButton farpnext;
    private javax.swing.JButton farqnext;
    private javax.swing.JTextField farregmob;
    private javax.swing.JTextField farscap;
    private javax.swing.JTextField farscapcold;
    private javax.swing.JLabel farsign;
    private javax.swing.JButton farsignupload;
    private javax.swing.JRadioButton farsphoneno;
    private javax.swing.JRadioButton farsphoneyes;
    private javax.swing.JComboBox<String> farstate;
    private javax.swing.JTextField farstreet;
    private javax.swing.JTable fartable;
    private javax.swing.JTabbedPane fartb;
    private javax.swing.JTextField farted;
    private javax.swing.JTextField fartehsil;
    private javax.swing.JComboBox<String> fartrans;
    private javax.swing.JTextField farvillage;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField toaadhar;
    private javax.swing.JTextField toaccno;
    private javax.swing.JTextField toaltmob;
    private javax.swing.JButton toanext;
    private javax.swing.JTextField tobank;
    private javax.swing.JTextField toblock;
    private javax.swing.JButton tobnext;
    private javax.swing.JTextField tobranch;
    private javax.swing.JTextField tocity;
    private javax.swing.JTextField tocolony;
    private javax.swing.JComboBox<String> todistrict;
    private javax.swing.JTextField todl;
    private javax.swing.JTextField todob;
    private javax.swing.JTextField toemail;
    private javax.swing.JTextField tohouse;
    private javax.swing.JButton toibt;
    private javax.swing.JLabel toid;
    private javax.swing.JTextField toifsc;
    private javax.swing.JLabel toilabel;
    private javax.swing.JTextField toname;
    private javax.swing.JTextField tootp;
    private javax.swing.JTextField topan;
    private javax.swing.JLabel tophoto;
    private javax.swing.JButton tophupload;
    private javax.swing.JTextField topincode;
    private javax.swing.JButton topnext;
    private javax.swing.JTextField toregmob;
    private javax.swing.JLabel tosign;
    private javax.swing.JButton tosignupload;
    private javax.swing.JComboBox<String> tostate;
    private javax.swing.JTextField tostreet;
    private javax.swing.JTextField totehsil;
    private javax.swing.JTable tovtable;
    private javax.swing.JButton tovupdate;
    public javax.swing.JPanel trans;
    private javax.swing.JPanel transdef;
    private javax.swing.JPanel transpanel;
    private javax.swing.JButton transport;
    private javax.swing.JTable transtable;
    private javax.swing.JTabbedPane transtb;
    // End of variables declaration//GEN-END:variables
}
