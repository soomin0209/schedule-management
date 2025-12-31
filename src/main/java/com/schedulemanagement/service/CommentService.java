package com.schedulemanagement.service;

import com.schedulemanagement.dto.CreateCommentRequest;
import com.schedulemanagement.dto.CreateCommentResponse;
import com.schedulemanagement.entity.Comment;
import com.schedulemanagement.entity.Schedule;
import com.schedulemanagement.repository.CommentRepository;
import com.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // 댓글 생성
    @Transactional
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request) {
        // 존재하는 일정인지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        Comment comment = new Comment(
                request.getContent(),
                request.getWriter(),
                request.getPassword(),
                schedule
        );
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getWriter(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt(),
                savedComment.getSchedule().getId()
        );
    }
}
