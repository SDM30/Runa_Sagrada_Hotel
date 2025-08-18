package com.runasagrada.demo.controller;

import java.security.Provider.Service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.runasagrada.demo.entities.HotelService;
import com.runasagrada.demo.service.HotelServiceService;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/service")
@Controller
public class HotelServiceController {

    private final HotelServiceService service;

    public HotelServiceController(HotelServiceService service) {
        this.service = service;
    }

    @GetMapping
    public String showServices(Model model) {
        model.addAttribute("services", service.getAllServices());
        return "services";
    }

    // http://localhost:8080/service/available
    @GetMapping("/available")
    public String showAvailableServices(Model model) {
        model.addAttribute("amenities",
                service.getAllServices().stream().filter(service -> service.getCategory().equals("Hotel")).toList());
        model.addAttribute("gastronomy",
                service.getAllServices().stream().filter(service -> service.getCategory().equals("Comida")).toList());
        model.addAttribute("tours",
                service.getAllServices().stream().filter(service -> service.getCategory().equals("Tours")).toList());
        return "services_cards";
    }

    @GetMapping("/available/{id}")
    public String showServiceDetail(Model model, @PathVariable("id") Integer idenfier) {
        HotelService serviceDetail = service.searchById(idenfier);
        model.addAttribute("serviceDetail", serviceDetail);
        return "service_detail";
    }

}
