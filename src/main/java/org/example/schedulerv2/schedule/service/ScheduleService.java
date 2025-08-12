package org.example.schedulerv2.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.schedulerv2.schedule.controller.dto.DeleteScheduleRequestDto;
import org.example.schedulerv2.schedule.controller.dto.ScheduleRequestDto;
import org.example.schedulerv2.schedule.controller.dto.UpdateScheduleRequestDto;
import org.example.schedulerv2.schedule.entity.Schedule;
import org.example.schedulerv2.schedule.repository.ScheduleRepository;
import org.example.schedulerv2.schedule.service.dto.ScheduleResponseDto;
import org.example.schedulerv2.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto, User user) {
        Schedule schedule = Schedule.of(scheduleRequestDto, user);
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
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto updateScheduleRequestDto) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id: " + id));
        if(!existingSchedule.getUser().getPassword().equals(updateScheduleRequestDto.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        existingSchedule.updateSchedule(updateScheduleRequestDto.getTitle(), updateScheduleRequestDto.getContents());
        return ScheduleResponseDto.from(scheduleRepository.save(existingSchedule));
    }

    @Transactional
    public void deleteScheduleById(Long id, DeleteScheduleRequestDto deleteScheduleRequestDto) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다. id: " + id));
        if(!existingSchedule.getUser().getPassword().equals(deleteScheduleRequestDto.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        scheduleRepository.delete(existingSchedule);
    }
}
