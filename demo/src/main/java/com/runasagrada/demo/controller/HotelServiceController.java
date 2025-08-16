package com.runasagrada.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/service")
@Controller
public class HotelServiceController {
    @GetMapping()
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
}
