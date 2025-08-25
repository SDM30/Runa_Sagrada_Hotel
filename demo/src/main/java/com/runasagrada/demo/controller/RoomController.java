package com.runasagrada.demo.controller;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    Logger logger = Logger.getLogger(RoomController.class.getName());

 
    @GetMapping("/staff")
    public String showRoomsStaff(Model model) {
        model.addAttribute("newroom", new Room());
        model.addAttribute("rooms", roomService.findAll());
        return "hotelesPage";
    }


    @PostMapping("/staff/add")
    public String registerRoom(@ModelAttribute("newroom") Room newRoom,
                               RedirectAttributes redirectAttributes) {
        try {
            roomService.create(newRoom);
            redirectAttributes.addFlashAttribute("successMessage",
                    "La habitación fue registrada correctamente.");
        } catch (DataIntegrityViolationException ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No se pudo registrar la habitación. Verifique los datos.");
        }
        return "redirect:/room/staff";
    }


    @GetMapping("/staff/update/{id}")
    @ResponseBody
    public Room getRoom(@PathVariable Long id) {
        logger.info("Room id: " + id);
        return roomService.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada con id " + id));
    }

    @PostMapping("/staff/update")
    public String updateRoom(@ModelAttribute("newroom") Room updatedRoom,
                             RedirectAttributes redirectAttributes) {
        try {
            roomService.update(updatedRoom.getId(), updatedRoom);
            redirectAttributes.addFlashAttribute("successMessage",
                    "La habitación fue actualizada correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No se pudo actualizar la habitación. Verifique los datos.");
        }
        return "redirect:/room/staff";
    }

 
    @GetMapping("/staff/delete/{id}")
    public String deleteRoom(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        try {
            roomService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "La habitación fue eliminada correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No se pudo eliminar la habitación.");
        }
        return "redirect:/room/staff";
    }

    @PostMapping("/staff/search")
    public String searchRooms(@RequestParam(required = false) String name,
                              @RequestParam(required = false) Integer minCapacity,
                              @RequestParam(required = false) Integer maxCapacity,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        List<Room> rooms = roomService.search(name, minCapacity, maxCapacity);

        if (rooms.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "No se encontraron habitaciones con los criterios ingresados.");
            return "redirect:/room/staff";
        }

        model.addAttribute("newroom", new Room()); // Para que el modal de agregar siga funcionando
        model.addAttribute("rooms", rooms);
        return "hotelesPage";
    }
}
