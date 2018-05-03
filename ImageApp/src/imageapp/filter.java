/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageapp;

/**
 *
 * @author joao_
 */
import java.awt.image.Kernel;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.io.*;
import javax.imageio.*;

public class filter {

    public static void highPass() {
        try {
            BufferedImage buff_original;
            buff_original = ImageIO.read(new File("Baby.jpg"));
            float val = 1f / 9f;
            float[] data = {val, val, val, val, val, val, val, val, val};
            Kernel kernel = new Kernel(3, 3, data);
            BufferedImageOp ConOp = new ConvolveOp(kernel);
            buff_original = ConOp.filter(buff_original, null);
            JPanel content = new JPanel();
            content.setLayout(new FlowLayout());
            // label to load image
            content.add(new JLabel(new ImageIcon(buff_original)));
            JFrame f = new JFrame("Convolution Image ");
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            f.setContentPane(content);
            f.pack();
            f.setVisible(true);
        } catch (IOException e) {
        }
    }
}//end class
