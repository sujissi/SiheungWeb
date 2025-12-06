package com.signal.web.dto.complaint;

import com.signal.web.domain.Complaint;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ComplaintResponse {
    private Long id;
    private String title;
    private String content;
    private String category;
    private String location;
    private int likes;
    private String status;
    private LocalDateTime createdAt;

    public ComplaintResponse(Complaint complaint) {
        this.id = complaint.getId();
        this.title = complaint.getTitle();
        this.content = complaint.getContent();
        this.category = complaint.getCategory();
        this.location = complaint.getLocation();
        this.likes = complaint.getLikes();
        this.status = complaint.getStatus();
        this.createdAt = complaint.getCreatedAt();
    }
}