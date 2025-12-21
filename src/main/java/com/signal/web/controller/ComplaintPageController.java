package com.signal.web.controller;

import com.signal.web.service.ComplaintService;
import com.signal.web.service.CommentService; // import 확인
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.signal.web.domain.Complaint;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/complaints")
public class ComplaintPageController {

    private final ComplaintService service;
    private final CommentService commentService;

    public ComplaintPageController(ComplaintService service, CommentService commentService) {
        this.service = service;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("top5", service.getTop5());
        model.addAttribute("latest", service.getLatest5());
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
        } else {
            complaints = service.findAll(sort);
        }

        model.addAttribute("top3", service.getTop5());
        model.addAttribute("complaints", complaints);

        model.addAttribute("category", category);
        model.addAttribute("status", status);
        model.addAttribute("sort", sort);

        return "complaints/list";
    }


    @GetMapping("/new")
    public String createForm(@RequestParam(required = false) String cat, Model model) {
        model.addAttribute("savedCat", cat);
        return "complaints/new";
    }

    @PostMapping("/new")
    public String createSubmit(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String category,
            @RequestParam String location,
            @RequestParam(required = false) MultipartFile files,
            Principal principal) {

        service.create(title, content, category, location, files, principal.getName());

        return "redirect:/complaints/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("complaint", service.findById(id));
        model.addAttribute("comments", commentService.getComments(id));
        model.addAttribute("now", LocalDateTime.now());
        return "complaints/detail";
    }

    @PostMapping("/like/{id}")
    public String like(@PathVariable Long id) {
        service.increaseLikes(id);
        return "redirect:/complaints/detail/" + id + "#like-section";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);

        return "redirect:/complaints/list";
    }
}