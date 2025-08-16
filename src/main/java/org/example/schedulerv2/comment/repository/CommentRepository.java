package org.example.schedulerv2.comment.repository;

import org.example.schedulerv2.comment.entity.Comment;
import org.example.schedulerv2.comment.service.dto.CommentCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserId(Long userId);

    @Query("select new org.example.schedulerv2.comment.service.dto.CommentCountDto(c.schedule.id, count(c)) " +
            "from Comment c " +
            "where c.schedule.id in :scheduleIds " +
            "group by c.schedule.id")
    List<CommentCountDto> countByScheduleIds(List<Long> scheduleIds);
}
