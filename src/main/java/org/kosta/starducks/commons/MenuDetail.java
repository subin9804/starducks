package org.kosta.starducks.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor
public class MenuDetail {
    private String code;
    private String name;
    private String url;
}
