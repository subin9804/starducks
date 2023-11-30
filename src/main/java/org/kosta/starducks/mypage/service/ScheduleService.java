package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.mypage.repository.ScheduleReposiroty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleReposiroty scheduleReposiroty;


}
