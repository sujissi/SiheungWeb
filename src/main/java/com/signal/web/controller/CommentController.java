package com.signal.web.controller;

import com.signal.web.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public String addComment(
            @RequestParam Long complaintId,
            @RequestParam String author,
            @RequestParam String password,
            @RequestParam String content) {

        // 서비스에 저장을 요청
        commentService.create(complaintId, author, password, content);

        // 댓글 작성 후 다시 해당 상세 페이지로 돌아가기 (새로고침)
        return "redirect:/complaints/detail/" + complaintId;
    }
}