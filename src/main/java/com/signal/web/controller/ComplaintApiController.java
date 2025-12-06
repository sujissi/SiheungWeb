package com.signal.web.controller;

import com.signal.web.dto.complaint.ComplaintRequest;
import com.signal.web.dto.complaint.ComplaintResponse;
import com.signal.web.domain.Complaint;
import com.signal.web.service.ComplaintService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ComplaintApiController {

    private final ComplaintService service;

    public ComplaintApiController(ComplaintService service) {
        this.service = service;
    }

    @PostMapping("/complaints")
    public ComplaintResponse create(@RequestBody ComplaintRequest request) {
        Complaint saved = service.create(request);
        return new ComplaintResponse(saved);
    }

    @GetMapping("/complaints")
    public List<ComplaintResponse> findAll() {
        return service.findAll().stream()
                .map(ComplaintResponse::new)
                .toList();
    }

    @GetMapping("/complaints/{id}")
    public ComplaintResponse findOne(@PathVariable Long id) {
        return new ComplaintResponse(service.findById(id));
    }

    @PutMapping("/complaints/{id}")
    public ComplaintResponse update(
            @PathVariable Long id,
            @RequestBody ComplaintRequest request) {

        return new ComplaintResponse(service.update(id, request));
    }

    @DeleteMapping("/complaints/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "삭제 완료: " + id;
    }

    @GetMapping("/top5")
    public List<ComplaintResponse> top5() {
        return service.getTop5().stream()
                .map(ComplaintResponse::new)
                .toList();
    }

    @GetMapping("/category/{category}")
    public List<ComplaintResponse> findByCategory(@PathVariable String category) {
        return service.getByCategory(category).stream()
                .map(ComplaintResponse::new)
                .toList();
    }

    @GetMapping("/status/{status}")
    public List<ComplaintResponse> findByStatus(@PathVariable String status) {
        return service.getByStatus(status).stream()
                .map(ComplaintResponse::new)
                .toList();
    }

    @GetMapping("/search")
    public List<ComplaintResponse> search(@RequestParam String keyword) {
        return service.search(keyword).stream()
                .map(ComplaintResponse::new)
                .toList();
    }
}