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
        //입력 검증
        if (request.getContent().isBlank()) {
            throw new IllegalStateException("댓글 내용은 필수값입니다.");
        }
        if (request.getContent().length() > 100) {
            throw new IllegalStateException("댓글 내용은 최대 100자 입니다.");
        }
        if (request.getWriter().isBlank()) {
            throw new IllegalStateException("작성자명은 필수값입니다.");
        }
        if (request.getPassword().isBlank()) {
            throw new IllegalStateException("비밀번호는 필수값입니다.");
        }

        // 존재하는 일정인지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        
        // 댓글 개수 확인
        long commentCount = commentRepository.countByScheduleId(scheduleId);
        if (commentCount >= 10) {
            throw new IllegalStateException("하나의 일정에는 댓글을 10개까지만 작성할 수 있습니다.");
        }

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
