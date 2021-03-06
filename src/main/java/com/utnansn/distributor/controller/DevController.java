package com.utnansn.distributor.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Profile("dev")
public class DevController {
    
    @GetMapping("/")
    public String redirectIndexToSwagger() {
        return "redirect:/swagger-ui/";
    }

}
