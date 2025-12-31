package com.schedulemanagement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String writer;
    private String password;

    // Schedule과의 관계 설정
    @ManyToOne
    @JoinColumn (name = "schedule_id")
    private Schedule schedule;

    public Comment(String content, String writer, String password, Schedule schedule) {
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.schedule = schedule;
    }
}
