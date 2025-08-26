package com.runasagrada.demo.controller;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityGraph;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private EntityManager entityManager;

    Logger logger = Logger.getLogger(RoomController.class.getName());

    // Root endpoint for /rooms - redirects to staff dashboard
    @GetMapping
    public String showRooms() {
        return "redirect:/rooms/staff";
    }

    @GetMapping("/staff")
    public String showRoomsStaff(Model model) {
        model.addAttribute("newroom", new Room());
        model.addAttribute("newroomtype", new com.runasagrada.demo.entities.RoomType());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("roomTypes", roomService.getAllRoomTypes());
        model.addAttribute("reservationStatuses", Room.ReservationStatus.values());
        model.addAttribute("cleaningStatuses", Room.CleaningStatus.values());
        return "roomsPage";
    }

    @PostMapping("/staff/add")
    public String registerRoom(@ModelAttribute("newroom") Room newRoom,
            @RequestParam("roomType") Long roomTypeId,
            RedirectAttributes redirectAttributes) {
        try {
            // Set the room type by ID
            if (roomTypeId != null) {
                RoomType roomType = new RoomType();
                roomType.setId(roomTypeId);
                newRoom.setRoomType(roomType);
            }
            roomService.create(newRoom);
            redirectAttributes.addFlashAttribute("successMessage", "Room registered successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: Room number already exists on this floor.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating room: " + e.getMessage());
        }
        return "redirect:/rooms/staff";
    }

    @GetMapping("/staff/update/{id}")
    @ResponseBody
    public Room getRoom(@PathVariable Long id) {
        EntityGraph<?> graph = entityManager.createEntityGraph("Room.withRoomType");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", graph);
        Room room = entityManager.find(Room.class, id, properties);

        if (room == null) {
            throw new RuntimeException("Room not found with id " + id);
        }
        return room;
    }

    @PostMapping("/staff/update")
    public String updateRoom(@ModelAttribute("newroom") Room updatedRoom,
            @RequestParam("roomType") Long roomTypeId,
            RedirectAttributes redirectAttributes) {
        try {
            // Set the room type by ID
            if (roomTypeId != null) {
                RoomType roomType = new RoomType();
                roomType.setId(roomTypeId);
                updatedRoom.setRoomType(roomType);
            }
            roomService.update(updatedRoom.getId(), updatedRoom);
            redirectAttributes.addFlashAttribute("successMessage", "Room updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating room: " + e.getMessage());
        }
        return "redirect:/rooms/staff";
    }

    @GetMapping("/staff/delete/{id}")
    public String deleteRoom(@PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            roomService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Room deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting room: " + e.getMessage());
        }
        return "redirect:/rooms/staff";
    }

    @PostMapping("/staff/search")
    public String searchRooms(@RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) Integer floorNumber,
            @RequestParam(name = "resStatus", required = false) Room.ReservationStatus resStatus,
            @RequestParam(name = "cleStatus", required = false) Room.CleaningStatus cleStatus,
            @RequestParam(required = false) Long hotelId, // optional if you later add a field
            @RequestParam(required = false) String themeName,
            Model model) {

        List<Room> rooms = roomService.search(roomNumber, floorNumber, resStatus, cleStatus, hotelId, themeName);

        model.addAttribute("rooms", rooms);
        model.addAttribute("newroom", new Room());
        model.addAttribute("newroomtype", new RoomType());
        model.addAttribute("roomTypes", roomService.getAllRoomTypes());
        model.addAttribute("reservationStatuses", Room.ReservationStatus.values());
        model.addAttribute("cleaningStatuses", Room.CleaningStatus.values());
        model.addAttribute("searchPerformed", true);

        if (rooms.isEmpty()) {
            model.addAttribute("errorMessage", "No se encontraron habitaciones con los criterios ingresados.");
        }
        return "roomsPage";
    }

    // Additional endpoints for specific searches
    @GetMapping("/by-room-number")
    @ResponseBody
    public List<Room> getRoomsByNumber(@RequestParam String roomNumber) {
        return roomService.findByRoomNumber(roomNumber);
    }

    @GetMapping("/by-floor")
    @ResponseBody
    public List<Room> getRoomsByFloor(@RequestParam Integer floorNumber) {
        return roomService.findByFloorNumber(floorNumber);
    }

    @GetMapping("/by-reservation-status")
    @ResponseBody
    public List<Room> getRoomsByReservationStatus(@RequestParam Room.ReservationStatus resStatus) {
        return roomService.findByReservationStatus(resStatus);
    }

    @GetMapping("/by-cleaning-status")
    @ResponseBody
    public List<Room> getRoomsByCleaningStatus(@RequestParam Room.CleaningStatus cleStatus) {
        return roomService.findByCleaningStatus(cleStatus);
    }

    @GetMapping("/by-hotel")
    @ResponseBody
    public List<Room> getRoomsByHotel(@RequestParam Long hotelId) {
        return roomService.findByHotelId(hotelId);
    }

    @GetMapping("/by-theme")
    @ResponseBody
    public List<Room> getRoomsByTheme(@RequestParam String themeName) {
        return roomService.findByThemeName(themeName);
    }
}
