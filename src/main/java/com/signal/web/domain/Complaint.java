package com.signal.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Complaint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String category;

    // 내용이 길어질 수 있으므로 TEXT 타입 권장 (기존대로 String만 써도 당장은 됩니다)
    @Column(columnDefinition = "TEXT")
    private String content;

    private String location;

    private String imagePath;

    @Column(nullable = false)
    private int likes = 0;

    private String status = "접수";

    private LocalDateTime createdAt;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}