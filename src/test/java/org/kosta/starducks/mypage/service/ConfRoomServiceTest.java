package org.kosta.starducks.mypage.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.repository.EmpRepository;
import org.kosta.starducks.mypage.entity.ConfRoom;
import org.kosta.starducks.mypage.entity.ConfRoomEN;
import org.kosta.starducks.mypage.repository.ConfRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConfRoomServiceTest {

    @Autowired
    private ConfRepository repository;
    @Autowired
    private EmpRepository empRepository;
    @Autowired
    private ConfRoomService service;

    @BeforeEach
    @Transactional
    void addBooks() {
        Employee emp = Employee.builder()
                .empId(33L)
                .email("asdf@asdn.com")
                .status(false)
                .addr("qwf")
                .position(Position.ROLE_EMPLOYEE)
                .build();
        empRepository.saveAndFlush(emp);

        List<ConfRoom> confRooms = new ArrayList<ConfRoom>();
        for(int i = 0; i < 5; i++) {
            confRooms.add(new ConfRoom(
                    Long.valueOf(i),
                    ConfRoomEN.ROOM3,
                    1L,
                    "모여라!" + i,
                    "재무부",
                    "회의의 제목 혹은 주제를 적습니다.",
                    "회의의 개요를 적습니다.",
                    LocalDate.parse("2000-01-1" + i),
                    LocalTime.parse("05:11:13"),
                    LocalTime.parse("11:11:2"+i),
                    "booked",
                    "red",
                    LocalDateTime.now())

            );

            repository.saveAllAndFlush(confRooms);
        }

    }

    @Test
    void getDayList() {
        List<ConfRoom> conf = repository.findByRunningDay(LocalDate.parse("2000-01-11"));

        assertThat(conf.size()).isEqualTo(1);
    }

    @Test
    void booking() {
    }

    @Test
    void edit() {
    }
}