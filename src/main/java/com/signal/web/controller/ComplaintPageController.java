package com.signal.web.controller;

import com.signal.web.service.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.signal.web.domain.Complaint;
import java.util.List;

@Controller
@RequestMapping("/complaints")
public class ComplaintPageController {

    private final ComplaintService service;

    public ComplaintPageController(ComplaintService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("top5", service.getTop5());
        model.addAttribute("latest", service.findAll());
        return "complaints/home";
    }

    @GetMapping("/list")
    public String listPage(Model model,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false) String status,
                           @RequestParam(required = false, defaultValue = "latest") String sort) {

        List<Complaint> complaints;

        if (category != null && !category.isEmpty() && status != null && !status.isEmpty()) {
            complaints = service.getByCategoryAndStatus(category, status);
        } else if (category != null && !category.isEmpty()) {
            complaints = service.getByCategory(category);
        } else if (status != null && !status.isEmpty()) {
            complaints = service.getByStatus(status);
        } else if (sort.equals("likes")) {
            complaints = service.getAllPopular();
        } else {
            complaints = service.findAll();
        }

        model.addAttribute("complaints", complaints);
        return "complaints/list";
    }

    @GetMapping("/new")
    public String createForm() {
        return "complaints/new";
    }

    @PostMapping("/new")
    public String createSubmit(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String category,
            @RequestParam String location) {

        service.create(title, content, category, location);
        return "redirect:/complaints/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("complaint", service.findById(id));
        return "complaints/detail";
    }
}