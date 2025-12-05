package com.signal.web.service;

import com.signal.web.domain.Complaint;
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

    public Complaint create(String title) {
        Complaint c = new Complaint();
        c.setTitle(title);
        return repository.save(c);
    }

    public List<Complaint> findAll(){
        return repository.findAll();
    }

    public Complaint findById(Long id){
        return repository.findById(id).orElseThrow(()-> new IllegalArgumentException("못 찾음: " + id));
    }

    @Transactional
    public Complaint update(Long id, String title){
        Complaint complaint = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("못 찾음: "+ id));

        complaint.setTitle(title);
        return complaint;
    }

    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new IllegalArgumentException("못 찾음: "+ id);
        }
        repository.deleteById(id);
    }
}
