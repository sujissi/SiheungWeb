package com.signal.web.controller;

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

    // 관리자 페이지 메인 (전체 목록)
    @GetMapping("/complaints")
    public String adminList(Model model) {
        model.addAttribute("complaints", complaintService.findAll());
        return "admin/list";
    }

    // 상태 변경 기능
    @PostMapping("/complaints/status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        complaintService.updateStatus(id, status);
        return "redirect:/admin/complaints";
    }

    // 관리자 권한 강제 삭제
    @PostMapping("/complaints/delete/{id}")
    public String deleteComplaint(@PathVariable Long id) {
        complaintService.delete(id);
        return "redirect:/admin/complaints";
    }
}