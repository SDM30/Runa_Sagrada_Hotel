package com.runasagrada.demo.controller;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.service.RoomService;
import com.runasagrada.demo.service.RoomTypeService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/rooms/staff")
public class RoomController {
    // Autowired
    private final RoomService roomService;
    private final RoomTypeService roomTypeService;

    public RoomController(RoomService roomService, RoomTypeService roomTypeService) {
        this.roomService = roomService;
        this.roomTypeService = roomTypeService;
    }

    /**
     * Convierte "" en null en params/form fields para que los filtros opcionales
     * funcionen.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /*
     * =========================
     * Vista
     * =========================
     */

    /** Sirve la página principal de habitaciones (roomsPage.html). */
    @GetMapping({ "", "/", "/page" })
    public String roomsPage(Model model) {
        // combos para selects en la vista
        List<RoomType> roomTypes = roomTypeService.findAll();
        model.addAttribute("roomTypes", roomTypes);
        model.addAttribute("reservationStatuses", Room.ReservationStatus.values());
        model.addAttribute("cleaningStatuses", Room.CleaningStatus.values());

        // para formularios create/update con th:object
        model.addAttribute("roomForm", new Room());
        return "roomsPage";
    }

    /*
     * =========================
     * Endpoints AJAX (JSON)
     * =========================
     */

    /**
     * Búsqueda con filtros opcionales (coincide con tu HTML:
     * /rooms/staff/search-ajax).
     */
    @GetMapping("/search-ajax")
    @ResponseBody
    public List<Room> searchAjax(@RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) Integer floorNumber,
            @RequestParam(required = false) Room.ReservationStatus resStatus,
            @RequestParam(required = false) Room.CleaningStatus cleStatus,
            @RequestParam(required = false) String themeName) {
        return roomService.search(roomNumber, floorNumber, resStatus, cleStatus, themeName);
    }

    /**
     * Carga una habitación para editar (coincide con tu HTML:
     * /rooms/staff/update/{id}).
     */
    @GetMapping("/update/{id}")
    @ResponseBody
    public Room getOneAjax(@PathVariable Long id) {
        return roomService.getById(id);
    }

    /*
     * =========================
     * CRUD via formularios
     * =========================
     */

    /** Crear (form POST). Usa binding de roomType.id desde la vista. */
    @PostMapping("/create")
    public String create(@ModelAttribute("roomForm") Room room,
            RedirectAttributes ra) {
        roomService.create(room);
        ra.addFlashAttribute("msgSuccess", "Habitación creada correctamente.");
        return "redirect:/rooms/staff/page";
    }

    /** Actualizar (form POST). */
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
            @ModelAttribute("roomForm") Room room,
            RedirectAttributes ra) {
        roomService.update(id, room);
        ra.addFlashAttribute("msgSuccess", "Habitación actualizada correctamente.");
        return "redirect:/rooms/staff/page";
    }

    /** Eliminar (form POST para compatibilidad con HTML simple). */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
            RedirectAttributes ra) {
        roomService.delete(id);
        ra.addFlashAttribute("msgSuccess", "Habitación eliminada.");
        return "redirect:/rooms/staff/page";
    }
}
