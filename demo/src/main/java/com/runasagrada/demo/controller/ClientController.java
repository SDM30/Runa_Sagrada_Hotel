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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runasagrada.demo.entities.Client;
import com.runasagrada.demo.entities.HotelUser;
import com.runasagrada.demo.service.ClientService;
import com.runasagrada.demo.service.HotelUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
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
    // http://localhost:8080/ops/client
    @PostMapping("/ops/client/search")
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
            return "redirect:/ops/client";
        }

        model.addAttribute("clients", clients);
        return "opsPage";
    }

    // http://localhost:8080/ops/client
    @GetMapping("/ops/client")
    public String showClientsOperator(Model model) {
        model.addAttribute("newclient", new Client());
        model.addAttribute("newclientuser", new HotelUser());
        model.addAttribute("clients", clientService.getAllClients());
        return "opsPage";
    }

    // Create client
    // http://localhost:8080/ops/client
    @PostMapping("/ops/client/add")
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

        return "redirect:/ops/client";
    }

    // Client sign up
    // http://localhost:8080/client/signup
    @GetMapping("/client/signup")
    public String signUpForm(Model model) {
        HotelUser user = new HotelUser(null, null, null, null, null, null);
        model.addAttribute("newuser", user);
        return "signup";
    }

    @PostMapping("/client/signup")
    public String signupClient(@ModelAttribute("newuser") HotelUser user, RedirectAttributes redirectAttributes) {
        logger.info(user.toString());
        try {
            user.setProfileIcon("/images/icons/icono3.png");
            userService.save(user);
            clientService.save(new Client(user));
            redirectAttributes.addFlashAttribute("successMessage",
                    "El cliente fue registrado correctamente.");
            // Redirigir al perfil del usuario recién creado
            return "redirect:/client/profile/" + user.getId();
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "El correo, teléfono o ID nacional ya está registrado. Por favor verifica los datos.");
            return "redirect:/client/signup";
        }
    }

    // Login general del sistema (admin, operador, clientes)
    // http://localhost:8080/client/login
    @GetMapping("/client/login")
    public String login() {
        return "login";
    }

    @PostMapping("/client/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        HotelUser user = userService.searchByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);

            if ("admin@runasagrada.com".equals(email) && "admin123".equals(password)) {
                return "redirect:/rooms/staff";
            } else if ("operador@runasagrada.com".equals(email) && "operador123".equals(password)) {
                return "redirect:/ops/client";
            } else {
                return "redirect:/client/profile/" + user.getId();
            }
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Update client
    // http://localhost:8080/ops/client
    @GetMapping("/ops/client/update/{id}")
    @ResponseBody
    public HotelUser getClient(@PathVariable Long id) {
        logger.info("Client id: " + id);
        return userService.searchById(id);
    }

    // Update client
    // http://localhost:8080/ops/client
    @PostMapping("/ops/client/update")
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

        return "redirect:/ops/client";
    }

    // Delete client
    // http://localhost:8080/ops/client
    @GetMapping("/ops/client/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return "redirect:/ops/client";
    }

    // Creates a model atribute aviable to all views
    @ModelAttribute("newclientuser")
    public HotelUser getNewClientUser() {
        return new HotelUser();
    }

    // Mostrar perfil
    @GetMapping("/client/profile/{id}")
    public String mostrarPerfil(@PathVariable Long id, Model model) {
        HotelUser hotelUser = userService.searchById(id);
        model.addAttribute("hotelUser", hotelUser);
        return "hotelUserProfile";
    }

    // Editar perfil
    @PostMapping("/client/profile/edit")
    public String editarPerfil(@ModelAttribute("hotelUser") HotelUser hotelUser, Model model) {

        HotelUser original = userService.searchById(hotelUser.getId());
        if (original == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "hotelUserProfile";
        }

        original.setName(hotelUser.getName());
        original.setEmail(hotelUser.getEmail());
        original.setPhone(hotelUser.getPhone());
        original.setNationalId(hotelUser.getNationalId());
        original.setPassword(hotelUser.getPassword());
        original.setProfileIcon(hotelUser.getProfileIcon());

        userService.save(original);
        return "redirect:/client/profile/" + original.getId();
    }
    // Actualizar Cliente funcion privada
}
