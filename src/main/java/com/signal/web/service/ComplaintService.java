package com.signal.web.service;

import com.signal.web.domain.Complaint;
import com.signal.web.dto.complaint.ComplaintRequest;
import com.signal.web.repository.ComplaintRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    private final ComplaintRepository repository;

    public ComplaintService(ComplaintRepository repository) {
        this.repository = repository;
    }

    public Complaint create(String title, String content, String category, String location) {
        Complaint c = new Complaint();
        c.setTitle(title);
        c.setContent(content);
        c.setCategory(category);
        c.setLocation(location);
        return repository.save(c);
    }

    public Complaint create(ComplaintRequest request) {
        Complaint c = new Complaint();
        c.setTitle(request.getTitle());
        c.setContent(request.getContent());
        c.setCategory(request.getCategory());
        c.setLocation(request.getLocation());
        return repository.save(c);
    }

    public List<Complaint> findAll(){
        return repository.findAll();
    }

    public Complaint findById(Long id){
        return repository.findById(id).orElseThrow(()-> new IllegalArgumentException("못 찾음: " + id));
    }

    @Transactional
    public Complaint update(Long id, ComplaintRequest request){
        Complaint complaint = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("못 찾음: "+ id));

        complaint.setTitle(request.getTitle());
        complaint.setContent(request.getContent());
        complaint.setCategory(request.getCategory());
        complaint.setLocation(request.getLocation());

        return complaint;
    }

    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new IllegalArgumentException("못 찾음: "+ id);
        }
        repository.deleteById(id);
    }

    public List<Complaint> getTop5() {
        return repository.findTop5ByOrderByLikesDesc();
    }

    public List<Complaint> getByCategory(String category) {
        return repository.findByCategoryOrderByIdDesc(category);
    }

    public List<Complaint> getByStatus(String status) {
        return repository.findByStatusOrderByIdDesc(status);
    }

    public List<Complaint> search(String keyword) {
        return repository.findByTitleContaining(keyword);
    }

    public List<Complaint> getAllPopular() {
        return repository.findAllByOrderByLikesDesc();
    }

    public List<Complaint> getByCategoryAndStatus(String category, String status) {
        return repository.findByCategoryAndStatus(category, status);
    }

    public List<Complaint> getUrgentList() {
        return repository.findUrgentComplaints();
    }
}