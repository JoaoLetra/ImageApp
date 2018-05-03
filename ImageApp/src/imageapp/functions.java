package imageapp;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

public class functions {


    private Stage stage;
    @FXML
    ImageView myImage,originalImg;

    @FXML
    Slider satSlider, brightSlider, hueSlider, contrastSlider;

    @FXML
    Label errorLabel,modLabel,originalLabel;

    Image image1,image2,orig;

    public void initialize (){
        modLabel.setVisible(false);
        originalLabel.setVisible(false);
        errorLabel.setText("");
    }


    public void save (){
        if (!isEmpty(myImage)){
            errorLabel.setText("");
            Image ff = myImage.getImage();
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Files", "PNG");
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File("/home/"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage img = SwingFXUtils.fromFXImage(ff, null);
                    ImageIO.write(img, "png", chooser.getSelectedFile());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        else {
            errorLabel.setText("Error: No image available to save!");
        }
    }
    public void openFile (){
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
            originalImg.setImage(image1);
            myImage.setImage(image1);
            modLabel.setVisible(true);
            originalLabel.setVisible(true);
            orig = image1;
        }

    }

    public void setGrey (ActionEvent event){
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
        }
        else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    public void setNeg (ActionEvent event){
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
        }
        else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    public void setBr (ActionEvent event){
        if (!isEmpty(originalImg)) {
            errorLabel.setText("");
            image2 = originalImg.getImage();
            double value = brightSlider.getValue() + 1.00;
            String p = value + "f";
            BufferedImage img = SwingFXUtils.fromFXImage(image2, null);
            float scaleFactor = Float.valueOf(p);
            RescaleOp rescaleOp = new RescaleOp(scaleFactor, 0, null);
            img = rescaleOp.filter(img, null);
            Image s = SwingFXUtils.toFXImage(img, null);
            myImage.setImage(s);
        }
        else{
            errorLabel.setText("Error: No image available to transform!");
        }

    }

    public void setSat (){
        if (!isEmpty(originalImg)){
            errorLabel.setText("");
            double a = satSlider.getValue();
            ColorAdjust blackout = new ColorAdjust();
            blackout.setSaturation(a);
            myImage.setEffect(blackout);
        }
        else {
            errorLabel.setText("Error: No image available to transform!");
        }

    }

    public void setContrast (ActionEvent event){
        if (!isEmpty(originalImg)) {
            errorLabel.setText("");
            double a = contrastSlider.getValue();
            ColorAdjust blackout = new ColorAdjust();
            blackout.setContrast(a);
            myImage.setEffect(blackout);
        }
        else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    public void setHue (ActionEvent event){
        if (!isEmpty(originalImg)) {
            errorLabel.setText("");
            double a = hueSlider.getValue();
            ColorAdjust blackout = new ColorAdjust();
            blackout.setHue(a);
            myImage.setEffect(blackout);
        }
        else {
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    public void exit(){
        System.exit(0);
    }

    public void clear (){
        errorLabel.setText("");
        myImage.setImage(null);
        originalImg.setImage(null);
        modLabel.setVisible(false);
        originalLabel.setVisible(false);
    }

    public void setBlur(ActionEvent event){
        if (!isEmpty(originalImg)) {
            if (isEmpty(myImage)) {
                image2 = image1;
            } else {
                image2 = myImage.getImage();
            }
            errorLabel.setText("");
            Image a = Blur.main(image2);
            myImage.setImage(a);
        }
        else{
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    public void edge (ActionEvent event){
        if (!isEmpty(originalImg)) {
            errorLabel.setText("");
            image2 = image1;
            BufferedImage img = SwingFXUtils.fromFXImage(image2, null);
            Edge edge = new Edge();
            edge.setSourceImage(img);
            edge.process();
            BufferedImage img2 = edge.getEdgesImage();
            Image s = SwingFXUtils.toFXImage(img2, null);
            myImage.setImage(s);
        }
        else{
            errorLabel.setText("Error: No image available to transform!");
        }
    }

    public void hist (ActionEvent event){
        if (!isEmpty(myImage)) {
            errorLabel.setText("");
            image2 = myImage.getImage();
            BufferedImage img = SwingFXUtils.fromFXImage(image2, null);
            Histogram histogram = new Histogram(img);
        }
        else{
            errorLabel.setText("Error: No image available to show Histogram!");
        }
    }


    public void original (){
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

    public static boolean isEmpty(ImageView imageView) {
        Image image = imageView.getImage();
        return image == null || image.isError();
    }
    
    functions() {
    
    }
}
