package com.runasagrada.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.runasagrada.demo.entities.ServiceOffering;
import com.runasagrada.demo.service.ServiceOfferingService;

@RequestMapping("/service")
@Controller
public class ServiceOfferingController {

    private final ServiceOfferingService service;

    public ServiceOfferingController(ServiceOfferingService service) {
        this.service = service;
    }

    @GetMapping("/admin")
    public String showServices(Model model) {
        model.addAttribute("services", service.getAllServices());
        return "adminPage";
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
    public String showServiceDetail(Model model, @PathVariable("id") Long idenfier) {
        ServiceOffering serviceDetail = service.searchById(idenfier);
        model.addAttribute("serviceDetail", serviceDetail);
        return "service_detail";
    }

}
