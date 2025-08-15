package org.example.schedulerv2.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.comment.controller.dto.CommentRequestDto;
import org.example.schedulerv2.comment.entity.Comment;
import org.example.schedulerv2.comment.repository.CommentRepository;
import org.example.schedulerv2.comment.service.dto.CommentResponseDto;
import org.example.schedulerv2.schedule.entity.Schedule;
import org.example.schedulerv2.schedule.repository.ScheduleRepository;
import org.example.schedulerv2.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CommentResponseDto saveComment(CommentRequestDto commentRequestDto, User user, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id: " + scheduleId));

        Comment comment = Comment.of(commentRequestDto.getContent(), user, schedule);
        commentRepository.save(comment);
        return CommentResponseDto.from(comment);
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long id) {
        Comment comment = findCommentById(id);
        return CommentResponseDto.from(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, User user, CommentRequestDto commentRequestDto) {
        Comment comment = findCommentById(id);
        if(!comment.getUser().getId().equals(user.getId()))
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");

        comment.updateComment(commentRequestDto.getContent());
        return CommentResponseDto.from(comment);
    }

    @Transactional
    public void deleteCommentById(Long id) {
        Comment comment = findCommentById(id);
        commentRepository.delete(comment);
    }

    private Comment findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다. id: " + id));
    }
}
