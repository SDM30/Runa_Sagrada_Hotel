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
                                service.getAllServices().stream()
                                                .filter(service -> service.getCategory().equals("Hotel")).toList());
                model.addAttribute("gastronomy",
                                service.getAllServices().stream()
                                                .filter(service -> service.getCategory().equals("Comida")).toList());
                model.addAttribute("tours",
                                service.getAllServices().stream()
                                                .filter(service -> service.getCategory().equals("Tours")).toList());
                return "services_cards";
        }

        @GetMapping("/available/{id}")
        public String showServiceDetail(Model model, @PathVariable("id") Long idenfier) {
                ServiceOffering serviceDetail = service.searchById(idenfier);
                model.addAttribute("serviceDetail", serviceDetail);
                return "service_detail";
        }

        @GetMapping("/gastronomy")
        public String showGastronomia(Model model) {
                // Todos los servicios de comida
                model.addAttribute("gastronomy", service.getAllServices().stream()
                                .filter(service -> service.getCategory().equals("Comida"))
                                .toList());

                // Platos fuertes - buscar por subcategoría
                model.addAttribute("platosFuertes", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Comida") &&
                                                item.getSubcategory() != null &&
                                                item.getSubcategory().equals("Plato Principal"))
                                .toList());

                // Postres - buscar por subcategoría
                model.addAttribute("postres", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Comida") &&
                                                item.getSubcategory() != null &&
                                                item.getSubcategory().equals("Postre"))
                                .toList());

                // Bebidas - buscar por subcategoría
                model.addAttribute("bebidas", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Comida") &&
                                                item.getSubcategory() != null &&
                                                item.getSubcategory().equals("Bebida"))
                                .toList());
                return "gastronomyPage";
        }

        @GetMapping("/tours")
        public String showTours(Model model) {

                // Todos los tours
                model.addAttribute("tours", service.getAllServices().stream()
                                .filter(service -> service.getCategory().equals("Tours"))
                                .toList());

                // Tours culturales - buscar por subcategoría
                model.addAttribute("toursCulturales", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Tours") &&
                                                item.getSubcategory() != null &&
                                                item.getSubcategory().equals("Cultural"))
                                .toList());

                // Tours de naturaleza - buscar por subcategoría
                model.addAttribute("toursNaturaleza", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Tours") &&
                                                item.getSubcategory() != null &&
                                                item.getSubcategory().equals("Naturaleza"))
                                .toList());

                // Otros tours - los que no son Cultural ni Naturaleza
                model.addAttribute("otrosTours", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Tours") &&
                                                (item.getSubcategory() == null ||
                                                                (!item.getSubcategory().equals("Cultural") &&
                                                                                !item.getSubcategory()
                                                                                                .equals("Naturaleza"))))
                                .toList());
                return "toursPage";
        }

        @GetMapping("/amenities")
        public String showComodidades(Model model) {

                // Todos los servicios de hotel
                model.addAttribute("amenities", service.getAllServices().stream()
                                .filter(service -> service.getCategory().equals("Hotel"))
                                .toList());

                // Servicios de bienestar - solo Spa y Gimnasio
                model.addAttribute("bienestar", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Hotel") &&
                                                (item.getName().contains("Spa") ||
                                                                item.getName().contains("Gimnasio")))
                                .toList());

                // Servicios de hospedaje - solo Suite y Cabañas
                model.addAttribute("hospedaje", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Hotel") &&
                                                (item.getName().contains("Suite") ||
                                                                item.getName().contains("Cabañas")))
                                .toList());

                // Otros servicios - todo lo demás
                model.addAttribute("servicios", service.getAllServices().stream()
                                .filter(item -> item.getCategory().equals("Hotel") &&
                                                !item.getName().contains("Spa") &&
                                                !item.getName().contains("Gimnasio") &&
                                                !item.getName().contains("Suite") &&
                                                !item.getName().contains("Cabañas"))
                                .toList());
                return "amenitiesPage";
        }

}
