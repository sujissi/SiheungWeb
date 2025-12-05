package com.signal.web.controller;

import com.signal.web.domain.Complaint;
import com.signal.web.service.ComplaintService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ComplaintController {

    private final ComplaintService service;

    public ComplaintController(ComplaintService service) {
        this.service = service;
    }

    @PostMapping("/complaints")
    public Complaint create(@RequestParam String title) {
        return service.create(title);
    }

    @GetMapping("/complaints")
    public List<Complaint> findAll(){
        return service.findAll();
    }

    @GetMapping("/complaints/{id}")
    public Complaint findOne(@PathVariable Long id){
        return service.findById(id);
    }

    @PutMapping("/complaints/{id}")
    public Complaint update(@PathVariable Long id, @RequestParam String title){
        return service.update(id, title);
    }

    @DeleteMapping("/complaints/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "삭제 완료: " + id;
    }
}
