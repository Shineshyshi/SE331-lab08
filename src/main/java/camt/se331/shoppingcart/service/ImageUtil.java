package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.entity.Image;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

/**
 * Created by Shine on 29/3/2559.
 */
public class ImageUtil {
    static ImageUtil imageUtil = null;

    public static ImageUtil getInstance() {
        if (imageUtil == null) {
            imageUtil = new ImageUtil();
        }
        return imageUtil;
    }

    public static void main(String[] args) throws IOException {
        getImage("pic/ninja.png");
    }


    public static Image getImage(String resourcePath) {
        ImageResize imageResize = new ImageResize();
        Image image = new Image();
        ClassLoader classLoader = ImageUtil.getInstance().getClass().getClassLoader();

        File file = new File(classLoader.getResource(resourcePath).getFile());
        System.out.println(file);

        try {
            image.setFileName(file.getName());
            image.setContentType(Files.probeContentType(file.toPath()));
            BufferedImage BuffImage = ImageIO.read(file);


            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }
            image.setContent(bos.toByteArray());
            image.setCreated(Calendar.getInstance().getTime());

            imageResize.resize(BuffImage);
            imageResize.watermark(BuffImage);
          //  imageResize.write(BuffImage, file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


}