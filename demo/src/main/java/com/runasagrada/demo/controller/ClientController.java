package com.runasagrada.demo.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.runasagrada.demo.service.ClientService;
import com.runasagrada.demo.service.HotelUserService;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private HotelUserService hotelUserService;

    // DEBUG
    Logger logger = Logger.getLogger(ClientController.class.getName());

    @GetMapping("/admin")
    public String showClientsAdmin(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "adminClientPage";
    }

    @GetMapping("/staff")
    public String showClientsStaff(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "staffPage";
    }
}
