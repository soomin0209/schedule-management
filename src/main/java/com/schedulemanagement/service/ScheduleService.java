package com.schedulemanagement.service;

import com.schedulemanagement.dto.*;
import com.schedulemanagement.entity.Comment;
import com.schedulemanagement.entity.Schedule;
import com.schedulemanagement.repository.CommentRepository;
import com.schedulemanagement.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    // 일정 생성
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getWriter(),
                request.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getWriter(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // 선택 일정 조회
    @Transactional(readOnly = true)
    public GetScheduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );

        // 댓글 작성일을 기준으로 오름차순 정렬
        List<Comment> comments = commentRepository.findByScheduleIdOrderByCreatedAtAsc(scheduleId);
        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getWriter(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtos.add(dto);
        }

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                dtos
        );
    }

    // 전체 일정 조회
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll(String writer) {
        List<Schedule> schedules;

        if (writer == null) {   // 작성자명 없으면 전체 조회
            schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        } else {    // 작성자명 있으면 필터링
            schedules = scheduleRepository.findByWriterOrderByModifiedAtDesc(writer);
        }

        List<GetScheduleResponse> dtos = new ArrayList<>();
        for(Schedule schedule : schedules) {
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getWriter(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt(),
                    new ArrayList<>()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    // 일정 수정
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정입니다.")
        );
        schedule.update(request.getTitle(), request.getWriter());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // 일정 삭제
    @Transactional
    public void delete(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);

        // 존재하지 않으면
        if (!existence) {
            throw new IllegalStateException("없는 일정입니다.");
        }
        // 존재하면
        scheduleRepository.deleteById(scheduleId);
    }
}
