package com.schedulemanagement.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final Long id;
    private final String content;
    private final String writer;
    // 응답 비밀번호 제외
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private final Long scheduleId;  // 어느 일정의 댓글인지

    public CreateCommentResponse(Long id, String content, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt, Long scheduleId) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.scheduleId = scheduleId;
    }
}
