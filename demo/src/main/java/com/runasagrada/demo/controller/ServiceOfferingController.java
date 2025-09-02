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
                model.addAttribute("gastronomy", service.getAllServices().stream()
                                .filter(service -> service.getCategory().equals("Comida"))
                                .toList());
                model.addAttribute("platosFuertes", service.getAllServices().stream()
                                .filter(item -> item.getDescription().contains("Plato Principal"))
                                .limit(8)
                                .toList());
                model.addAttribute("postres", service.getAllServices().stream()
                                .filter(item -> item.getDescription().contains("Postre"))
                                .limit(8)
                                .toList());
                model.addAttribute("bebidas", service.getAllServices().stream()
                                .filter(item -> item.getDescription().contains("Bebida"))
                                .limit(8)
                                .toList());
                return "gastronomyPage";
        }

        @GetMapping("/tours")
        public String showTours(Model model) {

                model.addAttribute("tours", service.getAllServices().stream()
                                .filter(service -> service.getCategory().equals("Tour"))
                                .toList());
                model.addAttribute("toursCulturales", service.getAllServices().stream()
                                .filter(item -> item.getDescription().contains("Cultural"))
                                .limit(8)
                                .toList());
                model.addAttribute("toursNaturaleza", service.getAllServices().stream()
                                .filter(item -> item.getDescription().contains("Naturaleza"))
                                .limit(8)
                                .toList());
                model.addAttribute("otrosTours", service.getAllServices().stream()
                                .filter(item -> !item.getDescription().contains("Cultural")
                                                && !item.getDescription().contains("Naturaleza"))
                                .limit(8)
                                .toList());
                return "toursPage";
        }

        @GetMapping("/amenities")
        public String showComodidades(Model model) {

                model.addAttribute("amenities", service.getAllServices().stream()
                                .filter(service -> service.getCategory().equals("Hotel"))
                                .toList());
                model.addAttribute("bienestar", service.getAllServices().stream()
                                .filter(item -> item.getDescription().contains("sanación") ||
                                                item.getDescription().contains("Terapias") ||
                                                item.getDescription().contains("fitness") ||
                                                item.getDescription().contains("Spa") ||
                                                item.getName().contains("Spa") ||
                                                item.getName().contains("Gimnasio"))
                                .limit(8)
                                .toList());
                model.addAttribute("hospedaje", service.getAllServices().stream()
                                .filter(item -> item.getDescription().contains("Habitaciones") ||
                                                item.getDescription().contains("Hospedaje") ||
                                                item.getDescription().contains("Suite") ||
                                                item.getDescription().contains("Cabañas") ||
                                                item.getName().contains("Suite") ||
                                                item.getName().contains("Cabañas"))
                                .limit(8)
                                .toList());
                model.addAttribute("servicios", service.getAllServices().stream()
                                .filter(item -> !item.getDescription().contains("sanación") &&
                                                !item.getDescription().contains("Terapias") &&
                                                !item.getDescription().contains("fitness") &&
                                                !item.getDescription().contains("Spa") &&
                                                !item.getName().contains("Spa") &&
                                                !item.getName().contains("Gimnasio") &&
                                                !item.getDescription().contains("Habitaciones") &&
                                                !item.getDescription().contains("Hospedaje") &&
                                                !item.getDescription().contains("Suite") &&
                                                !item.getDescription().contains("Cabañas") &&
                                                !item.getName().contains("Suite") &&
                                                !item.getName().contains("Cabañas"))
                                .limit(8)
                                .toList());
                return "amenitiesPage";
        }

}
