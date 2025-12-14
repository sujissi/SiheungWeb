package com.signal.web.service;

import com.signal.web.domain.Comment;
import com.signal.web.domain.Complaint;
import com.signal.web.repository.CommentRepository;
import com.signal.web.repository.ComplaintRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ComplaintRepository complaintRepository;

    public CommentService(CommentRepository commentRepository, ComplaintRepository complaintRepository) {
        this.commentRepository = commentRepository;
        this.complaintRepository = complaintRepository;
    }

    // 댓글 목록 가져오기
    public List<Comment> getComments(Long complaintId) {
        return commentRepository.findAllByComplaintIdOrderByIdDesc(complaintId);
    }

    // 댓글 저장하기
    @Transactional
    public void create(Long complaintId, String author, String password, String content) {
        // 댓글을 달 부모 게시글 찾기
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = new Comment();
        comment.setComplaint(complaint); // 게시글 연결
        comment.setAuthor(author);
        comment.setPassword(password);
        comment.setContent(content);

        commentRepository.save(comment);
    }
}