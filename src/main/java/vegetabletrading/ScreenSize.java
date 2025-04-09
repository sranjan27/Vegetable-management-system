/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vegetabletrading;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author ASUS
 */
public class ScreenSize {
    public int width;
    public int height;
    
    public ScreenSize(){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)(dimension.getWidth());
        height = (int)(dimension.getHeight());
        //System.out.println(width);
        //System.out.println(height);       
    }
    
    public int width(){
        return width;
    }
    
    public int height(){
        return height;
    }
    
   
}
