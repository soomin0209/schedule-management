package com.schedulemanagement.repository;

import com.schedulemanagement.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 전체 조회, 수정일 내림차순
    List<Schedule> findAllByOrderByModifiedAtDesc();
    // 필터링 조회, 수정일 내림차순
    List<Schedule> findByWriterOrderByModifiedAtDesc(String writer);
}
