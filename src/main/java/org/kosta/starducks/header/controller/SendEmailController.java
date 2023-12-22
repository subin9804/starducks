package org.kosta.starducks.header.controller;


import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.kosta.starducks.header.dto.EmailDto;
import org.kosta.starducks.header.dto.RSEmailDto;
import org.kosta.starducks.header.service.SendEmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/email")
@AllArgsConstructor
public class SendEmailController {

    private final SendEmailService emailService;

    @GetMapping()
    public String send(){
        return "header/emailForm";
    }
    @PostMapping("/send")
    public String sendMailMailDto (EmailDto emailDto) throws MessagingException {
        emailService.sendMessage(emailDto);
        System.out.println("메일 전송 완료");
        return "header/afterEmail";
    }

    @GetMapping("/list")
    public String getEmailList(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "9") int size) {
        try {

            //타임리프에서 받아온 page와 size값을 넘겨서 서비스의 메서드 사용해서 해당 이메일만 받아온다.
            //처음에는 디폴트 값인 0페이지로 이동한다. -> 화면에서 페이지 목차 누르면 이동한다.
            Page <RSEmailDto> emails = emailService.fetchInboxEmails(PageRequest.of(page, size));
            model.addAttribute("emails", emails);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return "header/emailList";
    }

    @GetMapping("/sentlist")
    public String getSentEmailList(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "9") int size) {
        try {

            //타임리프에서 받아온 page와 size값을 넘겨서 서비스의 메서드 사용해서 해당 이메일만 받아온다.
            //처음에는 디폴트 값인 0페이지로 이동한다. -> 화면에서 페이지 목차 누르면 이동한다.
            Page<RSEmailDto> emails =emailService.fetchSentEmails(PageRequest.of(page, size));
            model.addAttribute("emails", emails);
//
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return "header/sentEmailList";
    }




}
