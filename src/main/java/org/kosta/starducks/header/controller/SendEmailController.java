package org.kosta.starducks.header.controller;


import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.kosta.starducks.header.dto.EmailDto;
import org.kosta.starducks.header.dto.RSEmailDto;
import org.kosta.starducks.header.service.sendEmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/email")
@AllArgsConstructor
public class SendEmailController {

    private final sendEmailService emailService;

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
    public String getEmailList(Model model) {
        try {
            List<RSEmailDto> emails = emailService.fetchInboxEmails();
            emails.sort(Comparator.comparing(RSEmailDto::getSentDate).reversed());

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

            Page<RSEmailDto> emails =emailService.fetchSentEmails(PageRequest.of(page, size));



            model.addAttribute("emails", emails);


        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        return "header/sentEmailList";
    }




}
