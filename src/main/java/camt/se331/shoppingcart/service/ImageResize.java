package camt.se331.shoppingcart.service;

/**
 * Created by Shine on 2/4/2559.
 */

import java.awt.*;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import camt.se331.shoppingcart.entity.Image;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;


public class ImageResize {
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(String[] args) throws IOException {
        Image img = new Image();
        img.setFileName("C:/lab/SE331-lab08/src/main/resources/pic/bg.png");
        File originalImage = new File(img.getFileName());
        //read as buffered image
        BufferedImage image = read(originalImage);
        //resize
        resize(image);
        //write water mark
        watermark(image);
        //save
        write(image, img);
    }

    /**
     * Read.
     *
     * @param originalImage the original image
     * @return the buffered image
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static BufferedImage read(File originalImage) throws IOException {
        return ImageIO.read(originalImage);
    }

    /**
     * Resize.
     *
     * @param image the image
     * @return the buffered image
     */
    public static void resize(BufferedImage image) {
        Scalr.resize(image, Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 150, 100, Scalr.OP_ANTIALIAS);
    }

    /**
     * Watermark.
     *
     * @param image the image
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void watermark(BufferedImage image) throws IOException {
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setFont(new Font("Arial", Font.BOLD, 200));
        Color color = Color.black;
        graphics2D.setColor(color);
        graphics2D.drawString("shine!", image.getWidth() / 2, image.getHeight() / 2);
    }

    /**
     * Write.
     *
     * @param image the image
     * @throws IllegalArgumentException the illegal argument exception
     * @throws ImagingOpException       the imaging op exception
     * @throws IOException              Signals that an I/O exception has occurred.
     */
    public static void write(BufferedImage image, Image img) throws IllegalArgumentException, ImagingOpException, IOException {
        String path = img.getFileName();
        String[] full = path.split("pic/");
        String part1 = full[0];
        String part2 = "modified_" + full[1];
        String renamed = part1 + "pic/" + part2;
        ImageIO.write(image, "PNG", new File(renamed));

    }
}


