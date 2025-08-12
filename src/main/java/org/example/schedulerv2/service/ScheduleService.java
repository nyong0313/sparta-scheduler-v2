package org.example.schedulerv2.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.controller.dto.ScheduleRequestDto;
import org.example.schedulerv2.entity.Schedule;
import org.example.schedulerv2.repository.ScheduleRepository;
import org.example.schedulerv2.service.dto.ScheduleResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(
                scheduleRequestDto.getWriterName(),
                scheduleRequestDto.getTitle(),
                scheduleRequestDto.getContents()
        );

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
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id: " + id));

        existingSchedule.updateSchedule(scheduleRequestDto.getTitle(), scheduleRequestDto.getContents());
        return ScheduleResponseDto.from(scheduleRepository.save(existingSchedule));
    }

    @Transactional
    public void deleteScheduleById(Long id) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id: " + id));
        scheduleRepository.delete(existingSchedule);
    }
}
