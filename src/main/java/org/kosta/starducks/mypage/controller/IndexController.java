package org.kosta.starducks.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.kosta.starducks.auth.dto.CustomUserDetails;
import org.kosta.starducks.forum.entity.ForumPost;
import org.kosta.starducks.forum.service.ForumPostService;
import org.kosta.starducks.header.service.SendEmailService;
import org.kosta.starducks.hr.entity.Employee;
import org.kosta.starducks.hr.service.EmpFileService;
import org.kosta.starducks.hr.service.EmpService;
import org.kosta.starducks.mypage.entity.Attendance;
import org.kosta.starducks.mypage.service.AttendanceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class IndexController {

    private final AttendanceService attendService;
    private final EmpService empService;
    private final ForumPostService forumService;
    private final SendEmailService emailService;
    private final EmpFileService fileService;


    // 6개의 위젯 구현
    @GetMapping
    public String index(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Long empId = userDetails != null ? userDetails.getEmployee().getEmpId() : 1L;


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
        List<ForumPost> topNotices = forumService.getTopNotice(); // 최신 공지 2개 조회
        List<ForumPost> posts = forumService.getForumList();

        model.addAttribute("topNotices", topNotices); // 공지사항 데이터 추가
        model.addAttribute("posts", posts);


        // 4. 메일함
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<RSEmailDto> mails = null;
//        try {
//            mails = emailService.fetchSentEmails(pageable);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        model.addAttribute("mails", mails);


        // 5. 전자결재함

        // 6. 날씨 위젯


        return "mypage/index";
    }

    @GetMapping("/api/show")
    @ResponseBody
    public List<Map<String, Object>> showSchedule() {

        return null;
    }
}
