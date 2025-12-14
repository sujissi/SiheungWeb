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
    public String adminList(Model model, @RequestParam(required = false, defaultValue = "latest") String sort) {
        // 1. 서비스에서 정렬된 목록 가져오기
        model.addAttribute("complaints", complaintService.findAll(sort));

        // 2. 현재 정렬 상태를 HTML에 알려주기
        model.addAttribute("currentSort", sort);

        return "admin/list";
    }

    // 상태 변경 기능
    @PostMapping("/complaints/status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        complaintService.updateStatus(id, status);
        return "redirect:/admin/complaints";
    }

    // 답변 등록 기능
    @PostMapping("/complaints/answer/{id}")
    public String registerAnswer(@PathVariable Long id, @RequestParam String answer) {
        complaintService.registerAnswer(id, answer);
        return "redirect:/admin/complaints";
    }

    // 관리자 권한 강제 삭제
    @PostMapping("/complaints/delete/{id}")
    public String deleteComplaint(@PathVariable Long id) {
        complaintService.delete(id);
        return "redirect:/admin/complaints";
    }
}