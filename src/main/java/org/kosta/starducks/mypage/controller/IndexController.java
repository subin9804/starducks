package org.kosta.starducks.mypage.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.document.entity.Document;
import org.kosta.starducks.document.service.DocumentService;
import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.service.ForumPostService;
import org.kosta.starducks.header.dto.RSEmailDto;
import org.kosta.starducks.header.service.SendEmailService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpFileService;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.mypage.entity.Attendance;
import org.kosta.starducks.mypage.entity.Schedule;
import org.kosta.starducks.mypage.service.AttendanceService;
import org.kosta.starducks.mypage.service.ScheduleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @Value("${weather-key}")
    private String apiKey;

    private final AttendanceService attendService;
    private final EmpService empService;
    private final ForumPostService forumService;
    private final SendEmailService emailService;
    private final EmpFileService fileService;
    private final ScheduleService scheduleService;
    private final DocumentService documentService;


    // 6개의 위젯 구현
    @GetMapping("/")
    public String index(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long empId = Long.parseLong(userDetails.getUsername());
//        Long empId = userDetails != null ? userDetails.getEmployee().getEmpId() : 1L;
        // 1. 근태관리
        Employee emp = empService.getEmp(empId);
        model.addAttribute("employee", emp);

        String profile = fileService.getFileUrl(emp.getEmpId(), "profile");
        model.addAttribute("profile", profile);

        Attendance todayAttend = attendService.getAttendanceForToday(empId);
        model.addAttribute("attendance", todayAttend);

        int absentDays = attendService.getAbsentDays(empId);
        int workDays = attendService.getWorkDays(empId);
        int lateDays = attendService.getLateDays(empId);

        model.addAttribute("absentDays", absentDays);
        model.addAttribute("lateDays", lateDays);
        model.addAttribute("workDays", workDays);

        // 2. 일정캘린더


        // 3. 전사게시판
        // 공지
        List<ForumPost> topNotices = forumService.getTopNotice(); // 최신 공지 2개 조회
        // 일반 글
        Sort sort = Sort.by (Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(0, 12, sort);
        Page<ForumPost> posts = forumService.postList(pageable);

        model.addAttribute("topNotices", topNotices); // 공지사항 데이터 추가
        model.addAttribute("posts", posts);


        // 4. 메일함



        // 5. 전자결재함
        Sort forumSort = Sort.by (
                Sort.Order.desc("docDate"),
                Sort.Order.desc("urgent")
        );
        Pageable forumPageable = PageRequest.of(0, 12, forumSort);
        Page<Document> documents = documentService.receiveDocuments(empId, forumPageable);
        Long total = documents.getTotalElements();

        model.addAttribute("documents", documents);
        model.addAttribute("total", total);

        // 6. 날씨 위젯
        model.addAttribute("apiKey", apiKey);

        return "mypage/index";
    }

    /**
     * 일정을 내보내는 api
     * @return
     */
    @GetMapping("/mypage/api/show")
    @ResponseBody
    public ResponseEntity<List<Schedule>> showSchedule(Principal principal) {

        Long empId = 1L;
        // 유저 정보 받아오기
        if(principal != null) {
            empId = Long.valueOf(principal.getName());
        }

        List<Schedule> schedules = scheduleService.findByEmployeeEmpId(empId);
        System.out.println("=============================" + schedules);

        return ResponseEntity.ok(schedules);
    }

    /**
     * 메일을 가져오는 api
     * @return
     */
    @GetMapping("/mypage/api/mail")
    @ResponseBody
    public Page<RSEmailDto> showMail (Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<RSEmailDto> mails = null;
        try {
            mails = emailService.fetchInboxEmails(pageable);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        model.addAttribute("mails", mails);

        return mails;
    }


}
