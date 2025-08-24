package com.runasagrada.demo.controller;

import java.util.logging.Logger;

import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.runasagrada.demo.entities.Client;
import com.runasagrada.demo.entities.HotelUser;
import com.runasagrada.demo.service.ClientService;
import com.runasagrada.demo.service.HotelUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private HotelUserService userService;

    // DEBUG
    Logger logger = Logger.getLogger(ClientController.class.getName());

    @GetMapping("/admin")
    public String showClientsAdmin(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "adminClientPage";
    }

    @GetMapping("/staff")
    public String showClientsStaff(Model model) {
        model.addAttribute("newclient", new Client());
        model.addAttribute("newclientuser", new HotelUser());
        model.addAttribute("clients", clientService.getAllClients());
        return "staffPage";
    }

    @PostMapping("/staff")
    public String registerOrUpdateClient(@ModelAttribute("newclientuser") HotelUser newClientUser) {
        logger.info(newClientUser.toString());
        userService.save(newClientUser);

        Client newClient = new Client();
        newClient.setUser(newClientUser);
        clientService.save(newClient);

        return "redirect:/client/staff";
    }

}
