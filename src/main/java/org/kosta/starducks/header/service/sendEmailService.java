package org.kosta.starducks.header.service;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.kosta.starducks.header.dto.EmailDto;
import org.kosta.starducks.header.dto.RecievedEmailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Service
@AllArgsConstructor
public class sendEmailService {

    private final JavaMailSender javaMailSender;

    public void sendMessage(EmailDto emailDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setFrom("starducks2023@gmail.com");
        helper.setTo(emailDto.getAddress());
        helper.setSubject(emailDto.getTitle());
        helper.setText(emailDto.getContent(), true);

        javaMailSender.send(message);
    }


    public List<RecievedEmailDto> fetchInboxEmails() throws MessagingException {
        String host = "imap.gmail.com";
        String username = "10000@starducks.monster";
        String password = "qgfk uxcn sdea vuwx";

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true");

        Session emailSession = Session.getInstance(properties);
        Store store = emailSession.getStore("imaps");
        store.connect(host, username, password);

        Folder inbox = store.getFolder("INBOX");

        try {
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            List<RecievedEmailDto> messageList = new ArrayList<>();
            for (Message message : messages) {
                RecievedEmailDto mailDto = new RecievedEmailDto();
                mailDto.setFrom(getFromAddresses(message));
                mailDto.setSubject(message.getSubject());
                mailDto.setSentDate(message.getSentDate());
                mailDto.setMessageContent(getMessageContent(message));
                mailDto.setAttachFiles(getAttachmentFiles(message));

                messageList.add(mailDto);
            }

            return messageList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 메일 폴더 사용이 끝났으면 닫기
            if (inbox != null && inbox.isOpen()) {
                inbox.close(false);
            }
            // Store도 닫기
            if (store != null && store.isConnected()) {
                store.close();
            }
        }
    }

    private String getAttachmentFiles(Message message) {
        String s = null;
        return s;
    }

    private String getFromAddresses(Message message) throws MessagingException {
        Address[] fromAddresses = message.getFrom();
        StringBuilder from = new StringBuilder();
        for (Address address : fromAddresses) {
            from.append(address.toString()).append(", ");
        }
        if (from.length() > 1) {
            from.delete(from.length() - 2, from.length());
        }
        return from.toString();
    }

    private String getMessageContent(Message message) throws MessagingException, IOException, IOException {
        Object content = message.getContent();
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof Multipart) {
            Multipart multiPart = (Multipart) content;
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < multiPart.getCount(); i++) {
                BodyPart bodyPart = multiPart.getBodyPart(i);
                if (bodyPart.getDisposition() != null && bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
                    // Skip attachments
                    continue;
                } else {
                    text.append(bodyPart.getContent());
                }
            }
            return text.toString();
        }
        return "";
    }

//
//    private static final String from = "1000@starducks.monster"; // 보내는 사람의 이메일 주소
//    private static final String password = "qgfk uxcn sdea vuwx"; // 보내는 사람의 이메일 계정 비밀번호
//    private static final String host = "smtp.gmail.com"; // 구글 메일 서버 호스트 이름
//
//
//    public void sendMessage(EmailDto emailDto){
//
//    // SMTP 프로토콜 설정
//    Properties props = new Properties();
//        props.setProperty("mail.smtp.host", host);
//        props.setProperty("mail.smtp.port", "587");
//        props.setProperty("mail.smtp.auth", "true");
//        props.setProperty("mail.smtp.starttls.enable", "true");
//
//    // 보내는 사람 계정 정보 설정
//    Session session = Session.getInstance(props, new Authenticator() {
//        protected PasswordAuthentication getPasswordAuthentication() {
//            return new PasswordAuthentication(from, password);
//        }
//    });
//
//    // 메일 내용 작성
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(from);
//        message.setTo(emailDto.getAddress());
//        message.setSubject(emailDto.getTitle());
//        message.setText(emailDto.getContent());
//        //이메일 보내기
//        javaMailSender.send(message);
//}
}
