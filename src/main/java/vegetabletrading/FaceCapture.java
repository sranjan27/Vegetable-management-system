/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vegetabletrading;

import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author HARSHIT
 */
public class FaceCapture {

    public FaceCapture(JLabel facecaptured) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        try{
            grabber.start();
            opencv_core.IplImage img = grabber.grab();
            if(img != null){
                cvSaveImage("image.jpeg",img);
                
                String path = "D:\\PROJECT\\Evoting\\evoting\\image.jpeg";
                //System.out.println(path);
                try {
                    BufferedImage bi = ImageIO.read(new File(path));
                   
                    Image iconimg = bi.getScaledInstance(120, 150, Image.SCALE_SMOOTH );
                    ImageIcon icon = new ImageIcon(iconimg);
                    facecaptured.setIcon(icon);
                    
                    
                // TODO add your handling code here:
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Image Capturing Failed !!!");
                    //Logger.getLogger(FarmerReg.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            grabber.stop();
        }catch(Exception e){
            
        }
    }
}
