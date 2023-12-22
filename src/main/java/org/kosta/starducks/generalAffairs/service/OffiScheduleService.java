package org.kosta.starducks.generalAffairs.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.OffiSchedule;
import org.kosta.starducks.generalAffairs.repository.OffiScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OffiScheduleService {

    private final OffiScheduleRepository scheduleRepository;

    /**
     * 전체 전사 스케쥴 조회
     * @return
     */
    public List<OffiSchedule> getAll() {

        return scheduleRepository.findAll();
    }

    /**
     * 전사 스케쥴 개별 조회
     *
     */
    public OffiSchedule getSchedule(Long scheNo) {

        return scheduleRepository.findById(scheNo).orElse(null);
    }
}
