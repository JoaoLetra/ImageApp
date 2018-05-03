package imageapp;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Blur {


    public static BufferedImage img;

    public static Image main(Image a)
    {
        img = SwingFXUtils.fromFXImage(a, null);

        blur(10, 49);
        Image s = SwingFXUtils.toFXImage(img, null);
        return s;
    }

    public static void blur(double sigma, int kernelsize)
    {
        double[] kernel = createKernel(sigma, kernelsize);
        for(int i = 0; i < img.getWidth(); i++)
        {
            for(int j = 0; j < img.getHeight(); j++)
            {
                double overflow = 0;
                int counter = 0;
                int kernelhalf = (kernelsize - 1)/2;
                double red = 0;
                double green = 0;
                double blue = 0;
                for(int k = i - kernelhalf; k < i + kernelhalf; k++)
                {
                    for(int l = j - kernelhalf; l < j + kernelhalf; l++)
                    {
                        if(k < 0 || k >= img.getWidth() || l < 0 || l >= img.getHeight())
                        {
                            counter++;
                            overflow += kernel[counter];
                            continue;
                        }

                        Color c = new Color(img.getRGB(k, l));
                        red += c.getRed() * kernel[counter];
                        green += c.getGreen() * kernel[counter];
                        blue += c.getBlue() * kernel[counter];
                        counter++;
                    }
                    counter++;
                }

                if(overflow > 0)
                {
                    red = 0;
                    green = 0;
                    blue = 0;
                    counter = 0;
                    for(int k = i - kernelhalf; k < i + kernelhalf; k++)
                    {
                        for(int l = j - kernelhalf; l < j + kernelhalf; l++)
                        {
                            if(k < 0 || k >= img.getWidth() || l < 0 || l >= img.getHeight())
                            {
                                counter++;
                                continue;
                            }

                            Color c = new Color(img.getRGB(k, l));
                            red += c.getRed() * kernel[counter]*(1/(1-overflow));
                            green += c.getGreen() * kernel[counter]*(1/(1-overflow));
                            blue += c.getBlue() * kernel[counter]*(1/(1-overflow));
                            counter++;
                        }
                        counter++;
                    }
                }

                img.setRGB(i, j, new Color((int)red, (int)green, (int)blue).getRGB());
            }
        }
    }

    public static double[] createKernel(double sigma, int kernelsize)
    {
        double[] kernel = new double[kernelsize*kernelsize];
        for(int i = 0;  i < kernelsize; i++)
        {
            double x = i - (kernelsize -1) / 2;
            for(int j = 0; j < kernelsize; j++)
            {
                double y = j - (kernelsize -1)/2;
                kernel[j + i*kernelsize] = 1 / (2 * Math.PI * sigma * sigma) * Math.exp(-(x*x + y*y) / (2 * sigma *sigma));
            }
        }
        float sum = 0;
        for(int i = 0; i < kernelsize; i++)
        {
            for(int j = 0; j < kernelsize; j++)
            {
                sum += kernel[j + i*kernelsize];
            }
        }
        for(int i = 0; i < kernelsize; i++)
        {
            for(int j = 0; j < kernelsize; j++)
            {
                kernel[j + i*kernelsize] /= sum;
            }
        }
        System.out.println(kernel);
        return kernel;
        
    }
}
