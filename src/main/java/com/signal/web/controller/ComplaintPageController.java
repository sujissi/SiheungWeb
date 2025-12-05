package com.signal.web.controller;

import com.signal.web.service.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/complaints")
public class ComplaintPageController {

    private final ComplaintService service;

    public ComplaintPageController(ComplaintService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String createForm() {
        return "complaints/new";  // templates/complaints/new.html
    }

    @PostMapping("/new")
    public String createFormSubmit(@RequestParam String title) {
        service.create(title);
        return "redirect:/complaints/list";
    }

    @GetMapping("/list")
    public String listPage(Model model) {
        model.addAttribute("complaints", service.findAll());
        return "complaints/list";  // templates/complaints/list.html
    }
}
