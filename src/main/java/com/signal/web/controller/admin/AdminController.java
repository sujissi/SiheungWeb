package com.signal.web.controller.admin;

import com.signal.web.service.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ComplaintService service;

    public AdminController(ComplaintService service) {
        this.service = service;
    }

    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("popular", service.getAllPopular());
        model.addAttribute("urgent", service.getUrgentList());
        return "admin/home";
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        var complaint = service.findById(id);
        complaint.setStatus(status);
        return "redirect:/admin";
    }
}