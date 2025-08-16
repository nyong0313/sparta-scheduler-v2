package org.example.schedulerv2.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.comment.repository.CommentRepository;
import org.example.schedulerv2.comment.service.dto.CommentCountDto;
import org.example.schedulerv2.schedule.controller.dto.ScheduleRequestDto;
import org.example.schedulerv2.schedule.controller.dto.UpdateScheduleRequestDto;
import org.example.schedulerv2.schedule.entity.Schedule;
import org.example.schedulerv2.schedule.repository.ScheduleRepository;
import org.example.schedulerv2.schedule.service.dto.SchedulePageResponseDto;
import org.example.schedulerv2.schedule.service.dto.ScheduleResponseDto;
import org.example.schedulerv2.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto, User user) {
        Schedule schedule = Schedule.of(scheduleRequestDto.getTitle(), scheduleRequestDto.getContent(), user);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleResponseDto.from(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getSchedules() {
        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id: " + id));

        return ScheduleResponseDto.from(schedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, User user, UpdateScheduleRequestDto updateScheduleRequestDto) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id: " + id));
        if (!existingSchedule.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        existingSchedule.updateSchedule(updateScheduleRequestDto.getTitle(), updateScheduleRequestDto.getContents());
        return ScheduleResponseDto.from(scheduleRepository.save(existingSchedule));
    }

    @Transactional
    public void deleteScheduleById(Long id, User user) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id: " + id));
        if (!existingSchedule.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        scheduleRepository.delete(existingSchedule);
    }

    @Transactional(readOnly = true)
    public Page<SchedulePageResponseDto> findAllPage(int page, int size) {
        int adjustedPage = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(adjustedPage, size, Sort.by("updatedAt").descending());
        Page<Schedule> schedulePage = scheduleRepository.findAll(pageable);
        // 2. 일정 ID 리스트 추출
        List<Long> scheduleIds = schedulePage.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());
        // 3. 별도 쿼리로 댓글 수 조회
        List<CommentCountDto> countResults = commentRepository.countByScheduleIds(scheduleIds);
        Map<Long, Long> commentCountMap = countResults.stream()
                .collect(Collectors.toMap(CommentCountDto::getScheduleId, CommentCountDto::getCount));
        // 4. 각 Schedule을 SchedulePageResponseDto로 변환 (댓글 수는 Long을 int로 변환)
        return schedulePage.map(schedule -> new SchedulePageResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUsername(),
                commentCountMap.getOrDefault(schedule.getId(), 0L).intValue(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        ));
    }
}
