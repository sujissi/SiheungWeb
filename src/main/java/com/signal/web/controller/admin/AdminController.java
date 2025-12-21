package com.signal.web.controller.admin;

import com.signal.web.service.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ComplaintService complaintService;

    public AdminController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping("/complaints")
    public String adminList(Model model,
                            @RequestParam(required = false, defaultValue = "latest") String sort) {

        model.addAttribute("complaints", complaintService.findAll(sort));
        model.addAttribute("currentSort", sort);

        return "admin/list";
    }

    @PostMapping("/complaints/status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               @RequestParam(required = false, defaultValue = "latest") String sort) {

        complaintService.updateStatus(id, status);
        return "redirect:/admin/complaints?sort=" + sort;
    }

    @PostMapping("/complaints/answer/{id}")
    public String registerAnswer(@PathVariable Long id,
                                 @RequestParam String answer,
                                 @RequestParam(required = false, defaultValue = "latest") String sort) {

        complaintService.registerAnswer(id, answer);
        return "redirect:/admin/complaints?sort=" + sort;
    }

    @PostMapping("/complaints/delete/{id}")
    public String deleteComplaint(@PathVariable Long id,
                                  @RequestParam(required = false, defaultValue = "latest") String sort) {

        complaintService.delete(id);
        return "redirect:/admin/complaints?sort=" + sort;
    }
}
