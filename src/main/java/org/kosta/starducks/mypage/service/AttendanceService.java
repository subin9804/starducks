package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.mypage.entity.Attendance;
import org.kosta.starducks.mypage.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;

    /**
     * 일별 출근 정보 Json List 반환 함수
     */
    public List<Map<String, Object>> getDailyAttendance(Long empId) {
        // AttendanceRepository를 통해 일별 출근 정보를 조회
        List<Object[]> attendanceList = attendanceRepository.findDailyAttendanceByEmpId(empId);

        // JSON 리스트로 변환
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] attendance : attendanceList) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("workDate", attendance[0]);
            entry.put("startTime", attendance[1]);
            entry.put("endTime", attendance[2]);
            entry.put("isVacation", attendance[3]);
            result.add(entry);
        }
        return result;
    }

    /**
     * 오늘의 출근 정보 조회 함수
     */
    public Attendance getAttendanceForToday(Long empId) {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findByWorkDateAndEmp_EmpId(today, empId).orElse(null);
    }

    /**
     * 이번 달의 출근 정보 조회 함수
     */
    public List<Attendance> getAttendanceForMonth(Long empId) {
        LocalDate firstDay = YearMonth.now().atDay(1);
        LocalDate lastDay = YearMonth.now().atEndOfMonth();
        return attendanceRepository.findByWorkDateBetweenAndEmp_EmpId(firstDay, lastDay, empId);
    }


    public List<Attendance> getAllAttendance(){
        return attendanceRepository.findAll();
    }

    /**
     * 출근 기록 저장 함수
     */
    public void saveStartTime(Long empId) {
        Attendance attendance = new Attendance();
        attendance.setEmpId(empId);
        attendance.setWorkDate(LocalDate.now());
        attendance.setStartTime(LocalDateTime.now());
        attendanceRepository.save(attendance);
    }

    /**
     * 퇴근 기록 저장 함수
     */
    public void saveEndTime(Long empId) {
        // 출근 기록과 같은 work_id, 재기록 불가, 시도 시 이미 기록 완료했다는 안내
        LocalDate today = LocalDate.now();
        Attendance existAttendance = attendanceRepository.findByWorkDateAndEmp_EmpId(today, empId).orElse(null);

        if (existAttendance != null && existAttendance.getEndTime() == null) {
            // 이미 해당 날짜에 대한 정보가 있으면 기존의 work_id를 사용하여 저장
            existAttendance.setEndTime(LocalDateTime.now());
            attendanceRepository.save(existAttendance);
        }
        // 오늘의 근태 기록을 이미 완료했다는 모달 안내창(sweetAlert) 연결
    }
}