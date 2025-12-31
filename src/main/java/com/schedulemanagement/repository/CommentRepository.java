package com.schedulemanagement.repository;

import com.schedulemanagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByScheduleId(Long scheduleId);

    List<Comment> findByScheduleIdOrderByCreatedAtAsc(Long scheduleId);
}
