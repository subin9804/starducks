package org.kosta.starducks.header.service;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.kosta.starducks.header.dto.EmailDto;
import org.kosta.starducks.header.dto.RSEmailDto;
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


    //받은 메일함 받아오는 메서드

    public List<RSEmailDto> fetchInboxEmails() throws MessagingException {
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
            List<RSEmailDto> messageList = new ArrayList<>();
            for (Message message : messages) {
                RSEmailDto mailDto = new RSEmailDto();
                mailDto.setPeople(getFromAddresses(message));
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



    //보낸 메일함 받아오는 메서드
    public List<RSEmailDto> fetchSentEmails() throws MessagingException {
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

        Folder sent = store.getFolder("[Gmail]/Sent Mail");

        try {
            sent.open(Folder.READ_ONLY);

            Message[] messages = sent.getMessages();
            List<RSEmailDto> sentEmailList = new ArrayList<>();
            for (Message message : messages) {
                RSEmailDto sentEmailDto = new RSEmailDto();
                sentEmailDto.setPeople(getToAddresses(message));
                sentEmailDto.setSubject(message.getSubject());
                sentEmailDto.setSentDate(message.getSentDate());
                sentEmailDto.setMessageContent(getMessageContent(message));
                sentEmailDto.setAttachFiles(getAttachmentFiles(message));

                sentEmailList.add(sentEmailDto);
            }



            return sentEmailList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 메일 폴더 사용이 끝났으면 닫기
            if (sent != null && sent.isOpen()) {
                sent.close(false);
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

    private String getToAddresses(Message message) throws MessagingException {
        Address[] toAddresses = message.getRecipients(Message.RecipientType.TO);
        StringBuilder to = new StringBuilder();
        for (Address address : toAddresses) {
            to.append(address.toString()).append(", ");
        }
        if (to.length() > 1) {
            to.delete(to.length() - 2, to.length());
        }
        return to.toString();
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

}
