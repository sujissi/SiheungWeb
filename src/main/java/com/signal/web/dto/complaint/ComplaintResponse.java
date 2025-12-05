package com.signal.web.dto.complaint;

import com.signal.web.domain.Complaint;
import lombok.Getter;

@Getter
public class ComplaintResponse {
    private Long id;
    private String title;

    public ComplaintResponse(Complaint complaint) {
        this.id = complaint.getId();
        this.title = complaint.getTitle();
    }
}
