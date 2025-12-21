package com.signal.web.repository;

import com.signal.web.domain.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findTop5ByOrderByLikesDesc();

    List<Complaint> findAllByOrderByIdDesc();

    List<Complaint> findByCategoryOrderByIdDesc(String category);

    List<Complaint> findByStatusOrderByIdDesc(String status);

    List<Complaint> findByTitleContaining(String keyword);

    List<Complaint> findAllByOrderByLikesDesc();

    List<Complaint> findByCategoryAndStatus(String category, String status);

    @Query("SELECT c FROM Complaint c WHERE c.status = 'RECEIVED' ORDER BY c.likes DESC")
    List<Complaint> findUrgentComplaints();

    List<Complaint> findTop5ByOrderByCreatedAtDesc();

    List<Complaint> findAllByOrderByStatusAscCreatedAtDesc();

    List<Complaint> findAllByOrderByCreatedAtDesc();

}