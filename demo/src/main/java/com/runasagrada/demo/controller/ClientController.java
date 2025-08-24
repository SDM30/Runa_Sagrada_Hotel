package com.runasagrada.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runasagrada.demo.entities.Client;
import com.runasagrada.demo.entities.HotelUser;
import com.runasagrada.demo.service.ClientService;
import com.runasagrada.demo.service.HotelUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/staff/add")
    public String registerClient(@ModelAttribute("newclientuser") HotelUser newClientUser,
            RedirectAttributes redirectAttributes) {
        if (newClientUser.getPassword() == null || newClientUser.getPassword().isBlank()) {
            newClientUser.setPassword("123456"); // o mejor encriptada con BCrypt
        }

        userService.save(newClientUser);

        Client newClient = new Client();
        newClient.setUser(newClientUser);
        clientService.save(newClient);

        redirectAttributes.addFlashAttribute("successMessage",
                "El cliente fue registrado con contraseña por defecto: 123456. " +
                        "Recuérdale que la cambie en su primer ingreso.");

        return "redirect:/client/staff";
    }

    @GetMapping("/staff/update/{id}")
    @ResponseBody
    public HotelUser getClient(@PathVariable Long id) {
        logger.info("Client id: " + id);
        return userService.searchById(id);
    }

    @PostMapping("/staff/update")
    public String updateClient(@ModelAttribute("newclientuser") HotelUser updatedUser) {
        HotelUser user = userService.searchById(updatedUser.getId());
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        user.setNationalId(updatedUser.getNationalId());
        userService.save(user);
        return "redirect:/client/staff";
    }

    @GetMapping("/staff/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return "redirect:/client/staff";
    }

}
