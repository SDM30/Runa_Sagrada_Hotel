package com.runasagrada.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.runasagrada.demo.service.HotelServiceService;

@RequestMapping("/service")
@Controller
public class HotelServiceController {

    private final HotelServiceService service;

    public HotelServiceController(HotelServiceService service) {
        this.service = service;
    }

    @GetMapping("/admin")
    public String showServices(Model model) {
        model.addAttribute("services", service.getAllServices());
        return "adminPage";
    }
}
