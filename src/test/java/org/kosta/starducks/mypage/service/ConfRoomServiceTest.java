package org.kosta.starducks.mypage.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.mypage.entity.ConfRoom;
import org.kosta.starducks.mypage.entity.ConfRoomEN;
import org.kosta.starducks.mypage.repository.ConfRepository;
import org.kosta.starducks.roles.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConfRoomServiceTest {

    @Autowired
    private ConfRepository repository;
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
                .position(Position.EMPLOYEE)
                .build();

        List<ConfRoom> confRooms = new ArrayList<ConfRoom>();
        for(int i = 0; i < 5; i++) {
            confRooms.add(new ConfRoom(Long.valueOf(i), ConfRoomEN.ROOM3, emp, "모여라!" + i, LocalDate.parse("2000-01-0" + i), LocalDateTime.parse("11:11:1" + i), LocalDateTime.parse("11:11:2"+i), "runngin", LocalDateTime.now()));

            repository.saveAllAndFlush(confRooms);
        }

    }

    @Test
    void getDayList() {
        List<ConfRoom> conf = service.getDayList(LocalDate.parse("2000-01-01"));

        assertThat(conf.size()).isEqualTo(1);
    }

    @Test
    void booking() {
    }

    @Test
    void edit() {
    }
}