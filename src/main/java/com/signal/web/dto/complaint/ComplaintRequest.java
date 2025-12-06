package com.signal.web.dto.complaint;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ComplaintRequest {
    private String title;
    private String content;
    private String category;
    private String location;
}