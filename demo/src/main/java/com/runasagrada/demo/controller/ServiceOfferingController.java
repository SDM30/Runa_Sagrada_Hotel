package com.runasagrada.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.runasagrada.demo.entities.ServiceOffering;
import com.runasagrada.demo.service.ServiceOfferingService;
import java.util.List;
import java.util.function.Predicate;

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
                model.addAttribute("gastronomy",
                                filter(s -> "Comida".equals(s.getCategory())));

                // Platos fuertes - buscar por subcategoría
                model.addAttribute("platosFuertes",
                                filter(item -> "Comida".equals(item.getCategory())
                                                && "Plato Principal".equals(item.getSubcategory())));

                // Postres - buscar por subcategoría
                model.addAttribute("postres",
                                filter(item -> "Comida".equals(item.getCategory())
                                                && "Postre".equals(item.getSubcategory())));

                // Bebidas - buscar por subcategoría
                model.addAttribute("bebidas",
                                filter(item -> "Comida".equals(item.getCategory())
                                                && "Bebida".equals(item.getSubcategory())));
                return "gastronomyPage";
        }

        @GetMapping("/tours")
        public String showTours(Model model) {

                // Todos los tours
                model.addAttribute("tours",
                                filter(s -> "Tours".equals(s.getCategory())));

                // Tours culturales - buscar por subcategoría
                model.addAttribute("toursCulturales",
                                filter(item -> "Tours".equals(item.getCategory())
                                                && "Cultural".equals(item.getSubcategory())));

                // Tours de naturaleza - buscar por subcategoría
                model.addAttribute("toursNaturaleza",
                                filter(item -> "Tours".equals(item.getCategory())
                                                && "Naturaleza".equals(item.getSubcategory())));

                // Otros tours - los que no son Cultural ni Naturaleza
                model.addAttribute("otrosTours",
                                filter(item -> "Tours".equals(item.getCategory())
                                                && (item.getSubcategory() == null
                                                                || (!"Cultural".equals(item.getSubcategory())
                                                                                && !"Naturaleza".equals(item
                                                                                                .getSubcategory())))));
                return "toursPage";
        }

        @GetMapping("/amenities")
        public String showComodidades(Model model) {

                // Todos los servicios de hotel
                model.addAttribute("amenities",
                                filter(s -> "Hotel".equals(s.getCategory())));

                // Servicios de bienestar - solo Spa y Gimnasio
                model.addAttribute("bienestar",
                                filter(item -> "Hotel".equals(item.getCategory())
                                                && (item.getName().contains("Spa")
                                                                || item.getName().contains("Gimnasio"))));

                // Servicios de hospedaje - solo Suite y Cabañas
                model.addAttribute("hospedaje",
                                filter(item -> "Hotel".equals(item.getCategory())
                                                && (item.getName().contains("Suite")
                                                                || item.getName().contains("Cabañas"))));

                // Otros servicios - todo lo demás
                model.addAttribute("servicios",
                                filter(item -> "Hotel".equals(item.getCategory())
                                                && !item.getName().contains("Spa")
                                                && !item.getName().contains("Gimnasio")
                                                && !item.getName().contains("Suite")
                                                && !item.getName().contains("Cabañas")));
                return "amenitiesPage";
        }

        // Método privado reutilizable para filtrar servicios
        private List<ServiceOffering> filter(Predicate<ServiceOffering> predicate) {
                return service.getAllServices().stream().filter(predicate).toList();
        }

}
