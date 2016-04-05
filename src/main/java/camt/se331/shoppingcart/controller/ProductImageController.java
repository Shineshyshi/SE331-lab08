package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.Product;
import camt.se331.shoppingcart.service.ProductService;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by Shine on 29/3/2559.
 */
@CrossOrigin
@Controller
@RequestMapping("/productImage")
public class ProductImageController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Product addImage(HttpServletRequest request,
                            HttpServletResponse response, @RequestParam("productid") Long productId) {
        MultipartHttpServletRequest mRequest;
        Product product = productService.getProduct(productId);
        try {
            mRequest = (MultipartHttpServletRequest) request;
            Iterator<String> itr = mRequest.getFileNames();
            while (itr.hasNext()) {
                MultipartFile multipartFile = mRequest.getFile(itr.next());
                InputStream inputStream = null;
                inputStream = multipartFile.getInputStream();
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                BufferedImage scaledImg = Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, 640, 480);

                byte[] imageBytes;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                try {
                    Image image = new Image();
                    ImageIO.write(scaledImg, "png", bos);
                    imageBytes = bos.toByteArray();
                    image.setFileName(multipartFile.getName());
                    image.setContentType(multipartFile.getContentType());
                    image.setContent(imageBytes);
                    image.setCreated(Calendar.getInstance().getTime());
                    inputStream.close();
                    bos.close();
                    productService.addImage(product, image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return product;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Product edit(@RequestParam("productid") Long productId, @RequestParam("imageid") Long imageid) {
        Product product = productService.getProduct(productId);
        //System.out.println("----------- " + productId + " --------" + imageid);
        return productService.deleteImage(product, imageid);
    }
}



