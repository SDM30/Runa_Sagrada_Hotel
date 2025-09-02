package com.runasagrada.demo.controller;

import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.service.RoomTypeService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/roomtype/staff")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /*
     * =========================
     * Endpoint AJAX (JSON) que usa tu HTML
     * =========================
     */

    /** Búsqueda de tipos (coincide con tu HTML: /roomtype/staff/search-ajax). */
    @GetMapping("/search-ajax")
    @ResponseBody
    public List<RoomType> searchAjax(@RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity) {
        return roomTypeService.search(name, minCapacity, maxCapacity);
    }

    /*
     * =========================
     * CRUD simple por formularios (opcional)
     * =========================
     */

    @PostMapping("/create")
    public String create(@ModelAttribute RoomType roomType, RedirectAttributes ra) {
        roomTypeService.create(roomType);
        ra.addFlashAttribute("msgSuccess", "Tipo de habitación creado.");
        return "redirect:/rooms/staff/page";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
            @ModelAttribute RoomType roomType,
            RedirectAttributes ra) {
        roomTypeService.update(id, roomType);
        ra.addFlashAttribute("msgSuccess", "Tipo de habitación actualizado.");
        return "redirect:/rooms/staff/page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        roomTypeService.delete(id);
        ra.addFlashAttribute("msgSuccess", "Tipo de habitación eliminado.");
        return "redirect:/rooms/staff/page";
    }
}
