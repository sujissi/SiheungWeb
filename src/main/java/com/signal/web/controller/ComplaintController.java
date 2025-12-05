package com.signal.web.controller;

import com.signal.web.dto.complaint.ComplaintRequest;
import com.signal.web.dto.complaint.ComplaintResponse;
import com.signal.web.domain.Complaint;
import com.signal.web.service.ComplaintService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ComplaintController {

    private final ComplaintService service;

    public ComplaintController(ComplaintService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping("/complaints")
    public ComplaintResponse create(@RequestBody ComplaintRequest request) {
        Complaint saved = service.create(request.getTitle());
        return new ComplaintResponse(saved);
    }

    // READ all
    @GetMapping("/complaints")
    public List<ComplaintResponse> findAll() {
        return service.findAll()
                .stream()
                .map(ComplaintResponse::new)
                .collect(Collectors.toList());
    }

    // READ one
    @GetMapping("/complaints/{id}")
    public ComplaintResponse findOne(@PathVariable Long id) {
        Complaint complaint = service.findById(id);
        return new ComplaintResponse(complaint);
    }

    // UPDATE
    @PutMapping("/complaints/{id}")
    public ComplaintResponse update(
            @PathVariable Long id,
            @RequestBody ComplaintRequest request) {

        Complaint updated = service.update(id, request.getTitle());
        return new ComplaintResponse(updated);
    }

    // DELETE
    @DeleteMapping("/complaints/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "삭제 완료: " + id;
    }
}
