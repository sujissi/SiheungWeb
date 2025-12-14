package com.signal.web.repository;

import com.signal.web.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByComplaintIdOrderByIdDesc(Long complaintId);
}