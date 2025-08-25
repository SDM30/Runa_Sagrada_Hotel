package com.runasagrada.demo.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    // Search
    // http://localhost:8080/client/staff
    @PostMapping("/staff/search")
    public String searchClient(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String nationalId,
            @RequestParam(required = false) String name,
            Model model,
            RedirectAttributes redirectAttributes) {

        List<Client> clients = clientService.search(email, phone, nationalId, name);

        if (clients.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No se encontraron clientes con los criterios ingresados.");
            clients = (List<Client>) clientService.getAllClients(); // fallbackç
            return "redirect:/client/staff";
        }

        model.addAttribute("clients", clients);
        return "staffPage";
    }

    // Read all clients, TODO: Load clients per hotel
    // http://localhost:8080/client/staff
    @GetMapping("/staff")
    public String showClientsStaff(Model model) {
        model.addAttribute("newclient", new Client());
        model.addAttribute("newclientuser", new HotelUser());
        model.addAttribute("clients", clientService.getAllClients());
        return "staffPage";
    }

    // Create client
    // http://localhost:8080/client/staff
    @PostMapping("/staff/add")
    public String registerClient(@ModelAttribute("newclientuser") HotelUser newClientUser,
            RedirectAttributes redirectAttributes) {
        if (newClientUser.getPassword() == null || newClientUser.getPassword().isBlank()) {
            newClientUser.setPassword("123456");
        }

        try {
            userService.save(newClientUser);

            Client newClient = new Client();
            newClient.setUser(newClientUser);
            clientService.save(newClient);

            redirectAttributes.addFlashAttribute("successMessage",
                    "El cliente fue registrado con contraseña por defecto: 123456. " +
                            "Recuérdale que la cambie en su primer ingreso.");

        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "El correo, teléfono o ID nacional ya están registrados. Por favor verifica los datos.");
        }

        return "redirect:/client/staff";
    }

    // Client sign up
    // http://localhost:8080/client/login
    @GetMapping("/signup")
    public String signUpForm(Model model) {
        HotelUser user = new HotelUser(null, null, null, null, null, null);
        model.addAttribute("newuser", user);
        return "signup_client";
    }

    @PostMapping("/signup")
    public String login(@ModelAttribute("newuser") HotelUser user, RedirectAttributes redirectAttributes) {
        logger.info(user.toString());
        try {
            userService.save(user);
            clientService.save(new Client(user));
            redirectAttributes.addFlashAttribute("successMessage",
                    "El cliente fue registrado correctamente.");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "El correo, teléfono o ID nacional ya está registrado. Por favor verifica los datos.");
            return "redirect:/client/signup";
        }

        return "redirect:/client/main";
    }

    // Client login
    // http://localhost:8080/client/login
    @GetMapping("/login")
    public String loginForm(Model model) {
        HotelUser user = new HotelUser(null, null, null, null, null, null);
        model.addAttribute("newuser", user);
        return "login_client";
    }

    @PostMapping("/login")
    public String loginClient(@ModelAttribute("newuser") HotelUser user) {
        Client clientFound = clientService.login(user.getEmail(), user.getPassword());
        if (clientFound != null) {
            return "redirect:/client/main";
        } else {
            return "redirect:/client/login";
        }
    }

    // Update client
    // http://localhost:8080/client/staff
    @GetMapping("/staff/update/{id}")
    @ResponseBody
    public HotelUser getClient(@PathVariable Long id) {
        logger.info("Client id: " + id);
        return userService.searchById(id);
    }

    // Update client
    // http://localhost:8080/client/staff
    @PostMapping("/staff/update")
    public String updateClient(@ModelAttribute("newclientuser") HotelUser updatedUser,
            RedirectAttributes redirectAttributes) {
        try {
            HotelUser user = userService.searchById(updatedUser.getId());
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setNationalId(updatedUser.getNationalId());

            userService.save(user);

            redirectAttributes.addFlashAttribute("successMessage",
                    "El cliente fue actualizado correctamente.");

        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No se pudo actualizar: el correo, teléfono o ID nacional ya están registrados.");
        }

        return "redirect:/client/staff";
    }

    // Delete client
    // http://localhost:8080/client/staff
    @GetMapping("/staff/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return "redirect:/client/staff";
    }

    // Creates a model atribute aviable to all views
    @ModelAttribute("newclientuser")
    public HotelUser getNewClientUser() {
        return new HotelUser();
    }

}
