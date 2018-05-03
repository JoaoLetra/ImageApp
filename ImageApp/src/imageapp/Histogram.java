package imageapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public class Histogram extends JFrame {


    public Histogram (BufferedImage newimg) {
        setSize(500,500);
        setTitle("Histogram");
        myimg=newimg;
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D=(Graphics2D)g;
        Rfreq=new int[256];
        Gfreq=new int[256];
        Bfreq=new int[256];
        int width = myimg.getWidth();
        int height = myimg.getHeight();
        for(int i=0;i<width;i++) {
            for(int j=0;j<height;j++) {

                rgb =myimg.getRGB(i,j);
                rcolor = (rgb >> 16) & 0xff;
                gcolor = (rgb >> 8 ) & 0xff;
                bcolor = (rgb >> 0) & 0xff;
                Rfreq[rcolor]++;
                Gfreq[gcolor]++;
                Bfreq[bcolor]++;
            }
        }

        int iw=w/2;
        int ih=h/2;



        for(int i=0;i<256;i++) {

            g2D.setColor(Color.RED);
            g2D.drawLine(20+i,h-20,20+i,h-(Rfreq[i]/10)-20 );

            g2D.setColor(Color.GREEN);
            g2D.drawLine(20+i,h-20,20+i,h-(Gfreq[i]/10)-20);
            g2D.setColor(Color.BLUE);
            g2D.drawLine(20+i,h-20,20+i,h-(Bfreq[i]/10)-20);

            sumR+=Rfreq[i]*i;
            sumG+=Gfreq[i]*i;
            sumB+=Bfreq[i]*i;

        }
        g.setColor(Color.BLACK);
        g2D.drawLine(10,h-10,10,h-256);
        g2D.drawLine(10,h-10,256,h-10);
        g2D.drawString("0-->255",260,h-10);
        g2D.setFont(new Font("Aril",Font.BOLD,12));

        g2D.drawString("RGB Histogram",300,100);
        g2D.drawString("Red Mean :",300,120);
        g2D.drawString(Long.toString( sumR/(height*width)),400,120);
        g2D.drawString("Green Mean :",300,140);
        g2D.drawString(Long.toString(( sumG/(height*width))),400,140);
        g2D.drawString("Blue Mean :",300,160);
        g2D.drawString(Long.toString(( sumB/(height*width))),400,160);



    }

    BufferedImage myimg;
    int w=500;
    int h=500;
    int[] Rfreq;
    int[] Gfreq;
    int[] Bfreq;
    int rcolor;
    int gcolor;
    int bcolor;
    int rgb;
    long sumR;
    long sumB;
    long sumG;
}