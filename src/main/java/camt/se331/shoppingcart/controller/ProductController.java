package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.Product;
import camt.se331.shoppingcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Dto on 2/8/2015.
 */
@CrossOrigin
@RestController
@RequestMapping("/")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping(value = "product",method = RequestMethod.GET)
    public  List<Product> list(){
        return productService.getProducts();
    }


    @RequestMapping(value = "getProduct",method = RequestMethod.GET)
    public  List<Product> getListByName(@RequestParam("name")String name){
        return productService.getProductsByName(name);
    }
    @RequestMapping(value = "product",method = RequestMethod.POST)
    public @ResponseBody Product add(@RequestBody Product product, BindingResult bindingResult){
        return productService.addProduct(product);
    }

    @RequestMapping(value = "product/{id}",method = RequestMethod.GET)
    public  Product getProduct(@PathVariable("id") Long id){
        return productService.getProduct(id);
    }

    @RequestMapping(value = "product/{id}",method = RequestMethod.PUT)
    public  Product edit(@PathVariable("id") Long id,
                         @RequestParam("name") String name,
                         @RequestParam("description") String description,
                         @RequestParam("totalPrice") Double totalPrice){
        Product product = productService.getProduct(id);
        product.setName(name);
        product.setDescription(description);
        product.setTotalPrice(totalPrice);
        return productService.updateProduct(product);
    }

    @RequestMapping(value = "product/{id}",method = RequestMethod.DELETE)
    public  Product edit(@PathVariable("id") Long id){
        return productService.deleteProduct(id);
    }
}
