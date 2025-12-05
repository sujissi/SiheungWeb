package com.signal.web.service;

import com.signal.web.domain.Complaint;
import com.signal.web.repository.ComplaintRepository;
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
}
