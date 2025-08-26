package com.runasagrada.demo.controller;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.service.RoomService;
import com.runasagrada.demo.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/roomtype")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private RoomService roomService;

    Logger logger = Logger.getLogger(RoomTypeController.class.getName());

    @GetMapping("/staff")
    public String showRoomTypesStaff(Model model) {
        model.addAttribute("newroomtype", new RoomType());
        model.addAttribute("roomTypes", roomTypeService.findAll());
        return "redirect:/rooms/staff";
    }

    @PostMapping("/staff/add")
    public String registerRoomType(@ModelAttribute("newroomtype") RoomType newRoomType,
            RedirectAttributes redirectAttributes) {
        try {
            roomTypeService.create(newRoomType);
            redirectAttributes.addFlashAttribute("successMessage",
                    "El tipo de habitación fue registrado correctamente.");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No se pudo registrar el tipo de habitación. Verifique los datos.");
        }
        return "redirect:/rooms/staff";
    }

    @GetMapping("/staff/update/{id}")
    @ResponseBody
    public RoomType getRoomType(@PathVariable Long id) {
        logger.info("RoomType id: " + id);
        return roomTypeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de habitación no encontrado con id " + id));
    }

    @PostMapping("/staff/update")
    public String updateRoomType(@ModelAttribute("newroomtype") RoomType updatedRoomType,
            RedirectAttributes redirectAttributes) {
        try {
            roomTypeService.update(updatedRoomType.getId(), updatedRoomType);
            redirectAttributes.addFlashAttribute("successMessage",
                    "El tipo de habitación fue actualizado correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No se pudo actualizar el tipo de habitación. Verifique los datos.");
        }
        return "redirect:/rooms/staff";
    }

    @GetMapping("/staff/delete/{id}")
    public String deleteRoomType(@PathVariable Long id, RedirectAttributes ra) {
        try {
            roomTypeService.delete(id);
            ra.addFlashAttribute("successMessage", "El tipo de habitación fue eliminado correctamente.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "No se pudo eliminar: " + ex.getMessage());
        }
        return "redirect:/rooms/staff";
    }

    @PostMapping("/staff/search")
    public String searchRoomTypes(@RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            Model model) {
        List<RoomType> roomTypes = roomTypeService.search(name, minCapacity, maxCapacity);
        model.addAttribute("roomTypes", roomTypes);

        // Add all other necessary attributes for the page to function correctly
        model.addAttribute("newroom", new Room());
        model.addAttribute("newroomtype", new RoomType());
        model.addAttribute("rooms", roomService.findAll()); // Assuming you want to show all rooms
        model.addAttribute("reservationStatuses", Room.ReservationStatus.values());
        model.addAttribute("cleaningStatuses", Room.CleaningStatus.values());
        model.addAttribute("searchPerformed", true);

        if (roomTypes.isEmpty()) {
            model.addAttribute("errorMessage", "No se encontraron tipos de habitación con los criterios ingresados.");
        }

        return "roomsPage";
    }
}
