package org.kosta.starducks.header.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RSEmailDto {


    private String people;
    private String subject; //제목
    private Date sentDate; //수신일
    private String messageContent; //내용
    private String attachFiles; //첨부파일

}
