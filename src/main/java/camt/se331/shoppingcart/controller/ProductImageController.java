package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.Image;
import camt.se331.shoppingcart.entity.Product;
import camt.se331.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public Product addImage(HttpServletRequest request,HttpServletResponse response, @RequestParam("productid") Long productId) {
        MultipartHttpServletRequest mRequest;
        Product product = productService.getProduct(productId);
        try {
            mRequest = (MultipartHttpServletRequest) request;
            Iterator<String> itr = mRequest.getFileNames();
            while (itr.hasNext()) {
                MultipartFile multipartFile = mRequest.getFile(itr.next());
                Image image = new Image();
                image.setFileName(multipartFile.getName());
                image.setContentType(multipartFile.getContentType());
                image.setContent(multipartFile.getBytes());

                image.setCreated(Calendar.getInstance().getTime());
                productService.addImage(product, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }
}
