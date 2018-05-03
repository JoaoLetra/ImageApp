/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageapp;


import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author joao_
 */
public class FXMLDocumentController implements Initializable {

    private Stage stage;
    
    @FXML
    ImageView originalImg, myImage;

    @FXML
    MenuItem loadBtn, exitBtb;

    @FXML
    Slider brightSlider, hueSlider, contrastSlider, satSlider;

    @FXML
    Label errorLabel;
    

    Image image1, image2, orig;
                

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myImage.setVisible(false);

        // TODO
    }

    @FXML
    private void handleLoad(ActionEvent event) {

        errorLabel.setText("");
        clear();
        System.out.println("OpenFile");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg")

        );
        File file = fileChooser.showOpenDialog(stage);


        if(file != null){
            System.out.println("Choosen File: "+file);
            image1 = new Image(file.toURI().toString());
            
            //buff_original = ImageIO.read(new File(file + ));
            originalImg.setImage(image1);
            myImage.setImage(image1);
            
            
            orig = image1;
        }
        

    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void setBright(ActionEvent event) {
        if (!isEmpty(originalImg)) {

            image2 = originalImg.getImage();
            double value = brightSlider.getValue() + 1.00;
            String p = value + "f";
            BufferedImage img = SwingFXUtils.fromFXImage(image2, null);
            float scaleFactor = Float.valueOf(p);
            RescaleOp rescaleOp = new RescaleOp(scaleFactor, 0, null);
            img = rescaleOp.filter(img, null);
            Image s = SwingFXUtils.toFXImage(img, null);
            myImage.setImage(s);
            myImage.setVisible(true);
        } else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    @FXML
    private void setContrast(ActionEvent event) {
        if (!isEmpty(originalImg)) {

            double a = contrastSlider.getValue();
            ColorAdjust blackout = new ColorAdjust();
            blackout.setContrast(a);
            myImage.setEffect(blackout);
            myImage.setVisible(true);
        } else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    @FXML
    private void setSat(ActionEvent event) {
        if (!isEmpty(originalImg)) {

            double a = satSlider.getValue();
            ColorAdjust blackout = new ColorAdjust();
            blackout.setSaturation(a);
            myImage.setEffect(blackout);
            myImage.setVisible(true);
        } else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    @FXML
    private void setHue(ActionEvent event) {
        if (!isEmpty(originalImg)) {

            double a = hueSlider.getValue();
            ColorAdjust blackout = new ColorAdjust();
            blackout.setHue(a);
            myImage.setEffect(blackout);
            myImage.setVisible(true);
        } else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    @FXML
    private void setGray(ActionEvent event) {
        if (!isEmpty(originalImg)) {

            if (isEmpty(myImage)) {
                image2 = image1;
            } else {
                image2 = myImage.getImage();
            }
            errorLabel.setText("");
            BufferedImage img = SwingFXUtils.fromFXImage(image2, null);
            int width = img.getWidth();
            int height = img.getHeight();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int p = img.getRGB(j, i);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    int avg = (r + g + b) / 3;
                    p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                    img.setRGB(j, i, p);
                }
            }
            Image s = SwingFXUtils.toFXImage(img, null);
            myImage.setImage(s);
            myImage.setVisible(true);
        } else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    @FXML
    private void setNeg(ActionEvent event) {
        if (!isEmpty(originalImg)) {
            if (isEmpty(myImage)) {
                image2 = image1;
            } else {
                image2 = myImage.getImage();
            }
            errorLabel.setText("");
            BufferedImage img = SwingFXUtils.fromFXImage(image2, null);
            int width = img.getWidth();
            int height = img.getHeight();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int p = img.getRGB(j, i);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    img.setRGB(j, i, p);
                }
            }
            Image s = SwingFXUtils.toFXImage(img, null);
            myImage.setImage(s);
            myImage.setVisible(true);
        } else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    @FXML
    private void setHist(ActionEvent event) {
        if (!isEmpty(myImage)) {

            image2 = myImage.getImage();
            BufferedImage img = SwingFXUtils.fromFXImage(image2, null);
            Histogram histogram = new Histogram(img);
        } else {
            errorLabel.setText("Error: No image available to show Histogram!");
        }
    }

    @FXML
    private void setBlur(ActionEvent event) {
        if (!isEmpty(originalImg)) {
            if (isEmpty(myImage)) {
                image2 = image1;
            } else {
                image2 = myImage.getImage();
            }

            Image a = Blur.main(image2);
            myImage.setImage(a);
            myImage.setVisible(true);
        } else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    @FXML
    private void setEdge(ActionEvent event) {
        if (!isEmpty(originalImg)) {

            image2 = image1;
            BufferedImage img = SwingFXUtils.fromFXImage(image2, null);
            Edge edge = new Edge();
            edge.setSourceImage(img);
            edge.process();
            BufferedImage img2 = edge.getEdgesImage();
            Image s = SwingFXUtils.toFXImage(img2, null);
            myImage.setImage(s);
            myImage.setVisible(true);
        } else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    public static boolean isEmpty(ImageView imageView) {
        Image image = imageView.getImage();
        return image == null || image.isError();
    }

    @FXML
    private void handleSave(ActionEvent event) {
        if (!isEmpty(myImage)) {

            Image ff = myImage.getImage();
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Files", "PNG");
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File("/home/"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage img = SwingFXUtils.fromFXImage(ff, null);
                    ImageIO.write(img, "jpg", chooser.getSelectedFile());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            errorLabel.setText("Error: No image available to save!");
        }
    }

    @FXML
    private void handleReset(ActionEvent event) {
         if (!isEmpty(originalImg)){
            if (!isEmpty(myImage)){
                ColorAdjust blackout = new ColorAdjust();
                myImage.setEffect(blackout);
                myImage.setImage(orig);
                hueSlider.setValue(0);
                contrastSlider.setValue(0);
                satSlider.setValue(0);
                brightSlider.setValue(0);
                ActionEvent e = new ActionEvent();
            }
        }
        else{
            errorLabel.setText("Error: No image available to Revert!");
        }
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clear();
    }
    public void clear (){
        errorLabel.setText("");
        myImage.setImage(null);
        originalImg.setImage(null);
        myImage.setVisible(false);
      
    }
    
    

    @FXML
    private void setLowPass(ActionEvent event) {
        float[] data = {1,1,1,1,2,1,1,1,1};
        filter (data);
        
           
    }
    
    private void filter(float data []){
        
        BufferedImage buff_original;

            Image ff = myImage.getImage();
          
           
            buff_original = SwingFXUtils.fromFXImage(ff, null);
            float val = 1f / 9f;
            
            Kernel kernel = new Kernel(3, 3, data);
            BufferedImageOp ConOp = new ConvolveOp(kernel);
            buff_original = ConOp.filter(buff_original, null);
            
            Image s = SwingFXUtils.toFXImage(buff_original, null);
            myImage.setImage(s);
            myImage.setVisible(true);
        
    }

    @FXML
    private void setHighPass(ActionEvent event) {
        float[] data = {-1, -1, -1, -1, 8, -1, -1, -1, -1};
        filter (data);
        
    }

    @FXML
    private void setLaplacian(ActionEvent event) {
        float[] data = {0, -1, 0, -1, 4, -1, 0, -1, 0};
        filter (data);
        
    }

    

}
