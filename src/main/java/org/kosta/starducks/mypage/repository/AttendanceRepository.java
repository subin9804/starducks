package org.kosta.starducks.mypage.repository;

import org.kosta.starducks.mypage.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository  extends JpaRepository<Attendance, Long> {
    @Query("SELECT a.workDate, a.startTime, a.endTime, a.isVacation FROM Attendance a WHERE a.emp.empId = :empId")
    List<Object[]> findDailyAttendanceByEmpId(@Param("empId") Long empId);
    Optional<Attendance> findByWorkDateAndEmp_EmpId(LocalDate workDay, Long empId);
    List<Attendance> findByWorkDateBetweenAndEmp_EmpId(LocalDate firstDay, LocalDate lastDay, Long empId);

    Boolean existsAttendanceByEmp_EmpIdAndWorkDate(Long empId, LocalDate workDate);
}