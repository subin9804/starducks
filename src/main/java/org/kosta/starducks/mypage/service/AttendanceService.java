package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.mypage.entity.Attendance;
import org.kosta.starducks.mypage.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;


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
            result.add(entry);
        }

        return result;
    }

    public Attendance getAttendanceForToday() {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findByWorkDate(today).orElse(null);
    }

    public List<Attendance> getAllAttendance(){
        return attendanceRepository.findAll();
    }

    public void saveStartTime(Long empId) {
        Attendance attendance = new Attendance();
        attendance.setEmpId(empId);
        attendance.setWorkDate(LocalDate.now());
        attendance.setStartTime(LocalDateTime.now());
        attendanceRepository.save(attendance);
    }

    public void saveEndTime(Long empId) {
        Attendance attendance = new Attendance();
        attendance.setEmpId(empId);
        attendance.setWorkDate(LocalDate.now());
        attendance.setEndTime(LocalDateTime.now());
        attendanceRepository.save(attendance);
    }


//    public LocalDate getWorkDate() {
//        return workDate;
//    }
//
//    public LocalDateTime getStartTime() {
//        return startTime;
//    }
//    public LocalDateTime getEndTime() {
//        return endTime;
//    }
}
