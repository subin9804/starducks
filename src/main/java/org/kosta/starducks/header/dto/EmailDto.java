package org.kosta.starducks.header.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailDto {
    private String address;
    private String title;
    private String content;

}
