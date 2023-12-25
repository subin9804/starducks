package org.kosta.starducks.mypage.service;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.mypage.entity.Attendance;
import org.kosta.starducks.mypage.repository.AttendanceRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmpService empService;

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
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡgetAttendanceForToday empIdㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+empId);
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
    }

    /**
     * 출근일 반환 함수
     */
    public int getWorkDays(Long empId) {
        List<Attendance> attendanceForMonth = getAttendanceForMonth(empId);

//        int absentDays =  getAbsentDays(empId);
        int workDays = (int)attendanceForMonth.stream().count();

        return workDays;
    }

    /**
     * 지각일 반환 함수
     */
    public int getLateDays(Long empId) {
        List<Attendance> attendanceForMonth = getAttendanceForMonth(empId);

        int lateDays = 0;

        for(Attendance attend : attendanceForMonth) {
            if(attend.getStartTime() == null) {
                continue;
            }

            LocalDate standardDate = attend.getWorkDate();
            LocalTime standardTime = LocalTime.of(9, 00);
            LocalDateTime lateStandard = LocalDateTime.of(standardDate, standardTime);

            if (attend.getStartTime().isAfter(lateStandard)) {
                lateDays++;
            }
        }

        return lateDays;
    }

    /**
     * 결석일 반환 함수
     */
    public int getAbsentDays(Long empId) {
        List<Attendance> attendanceForMonth = getAttendanceForMonth(empId);
        int absentDays = 0;

        for(Attendance attend : attendanceForMonth) {
            DayOfWeek day = attend.getWorkDate().getDayOfWeek();    // 해당 workDate의 요일

            if(attend.getStartTime() == null) {
                if((day == DayOfWeek.SATURDAY) || (day == DayOfWeek.SUNDAY)) {
                    continue;
                }

                absentDays++;
            }
        }

        return absentDays;
    }

    /**
     * 결근일 경우 자동으로 기록하는 함수
     */
    @Scheduled(cron = "0 0 18 * * ?") // 매일 18시에 실행
    public void recordAbsent() {
        List<Long> allEmpId = empService.getAllEmp()
                .stream().map((emp) -> emp.getEmpId())
                .collect(Collectors.toList());

        LocalDate today = LocalDate.now();
        System.out.println(allEmpId);
        for(Long empId : allEmpId) {
            Boolean exists = attendanceRepository.existsAttendanceByEmp_EmpIdAndWorkDate(empId, today);

            if(!exists) {
                Attendance ac = new Attendance();
                ac.setEmpId(empId);
                ac.setWorkDate(LocalDate.now());

                attendanceRepository.save(ac);
            }
        }
    }
}