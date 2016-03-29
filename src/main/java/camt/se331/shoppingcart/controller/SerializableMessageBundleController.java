package camt.se331.shoppingcart.controller;


import camt.se331.shoppingcart.common.SerializableResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Properties;

@CrossOrigin
@Controller
@RequestMapping("/messageBundle")
public class SerializableMessageBundleController {
    @Autowired
    SerializableResourceBundleMessageSource messageBundle;
    /**
     * ReadAll
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Properties list(@RequestParam String lang) {
        return messageBundle.getAllProperties(new Locale(lang));
    }

}
