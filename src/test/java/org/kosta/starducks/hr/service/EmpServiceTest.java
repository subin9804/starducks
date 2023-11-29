package org.kosta.starducks.hr.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kosta.starducks.hr.entity.EmpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmpServiceTest {

    @Autowired
    private EmpService service;

    @Test
    @DisplayName("직원전체조회")
    void getAllEmp() {
        List<EmpEntity> emps = service.getAllEmp();

        assertThat(emps.size()).isEqualTo(0);
    }

    @Test
    void getEmp() {
    }

    @Test
    void saveEmp() {
    }

    @Test
    void delEmp() {
    }
}