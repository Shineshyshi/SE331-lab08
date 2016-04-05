package camt.se331.shoppingcart.service;

/**
 * Created by Shine on 2/4/2559.
 */

import java.awt.*;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        String resourcePath = "pic/aa.jpg";
        ClassLoader classLoader = ImageUtil.getInstance().getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourcePath).getFile());
        img.setFileName(file.getPath());

        File originalImage = new File(img.getFileName());
        //read as buffered image
        BufferedImage image = read(originalImage);
        //resize
        resize(image);
        //write water mark
        //watermark(image);
        //save
        write(image, file);
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
        Scalr.resize(image, Method.SPEED, Scalr.Mode.AUTOMATIC, 640, 480);
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
        Color color = Color.red;
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
    public static void write(BufferedImage image, File resourcePath) throws IllegalArgumentException, ImagingOpException, IOException {

        System.out.println(resourcePath);

        String[] full = resourcePath.toString().split("pic\\\\");
        String part1 = full[0];
        String part2 = "modified_" + full[1];
        String renamed = part1 + "pic\\" + part2;

        System.out.println(part1);
        System.out.println(part2);
        System.out.println(renamed);
        ImageIO.write(image, "JPG", new File(renamed));

    }
}


