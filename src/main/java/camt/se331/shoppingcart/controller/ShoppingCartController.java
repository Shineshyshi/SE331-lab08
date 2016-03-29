package camt.se331.shoppingcart.controller;

import camt.se331.shoppingcart.entity.ShoppingCart;
import camt.se331.shoppingcart.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Dto on 4/6/2015.
 */
@CrossOrigin
@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ShoppingCart getShoppingCart(@PathVariable("id") Long id){
        return shoppingCartService.findById(id);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public ShoppingCart updateShoppingCart(@PathVariable("id")Long id,@RequestBody ShoppingCart cart, BindingResult bindingResult){
        return shoppingCartService.addShoppingCart(cart);
    }
}
