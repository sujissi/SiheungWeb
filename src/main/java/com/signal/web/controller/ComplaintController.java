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
}
