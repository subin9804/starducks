package org.kosta.starducks.mypage.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kosta.starducks.mypage.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository repository;

    @Test
    void findAllSchedule() {
        List<Schedule> all = repository.findAll();

        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("해당 년월 스케줄 불러오기")
    void findByMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse("2023-12-01 00:00:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2023-12-31 00:00:00", formatter);
        List<Schedule> sches = repository.findByStartAndEnd(start, end);

        assertThat(sches.size()).isEqualTo(3);
    }
}