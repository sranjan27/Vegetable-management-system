/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vegetabletrading;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author HARSHIT
 */

public class FaceDetection extends Thread {
    public int end = 1;
    public FaceDetection(){
       
    }
    public void run(){ 
        opencv_highgui.CvCapture capt = opencv_highgui.cvCreateCameraCapture(0);
        opencv_highgui.cvSetCaptureProperty(capt, opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT, 200);
        opencv_highgui.cvSetCaptureProperty(capt, opencv_highgui.CV_CAP_PROP_FRAME_WIDTH, 190);
        
        opencv_core.IplImage grabbedImage = opencv_highgui.cvQueryFrame(capt);
        CanvasFrame frame = new CanvasFrame("webcam");
        while(frame.isVisible() && (grabbedImage = opencv_highgui.cvQueryFrame(capt)) != null){            
            frame.showImage(grabbedImage);
            frame.setAlwaysOnTop(true);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
            frame.setLocation(x, 0);
            
            if(end == 0){
                opencv_highgui.cvReleaseCapture(capt);
                frame.setVisible(false);
                break;
            }
        }
    }
}
