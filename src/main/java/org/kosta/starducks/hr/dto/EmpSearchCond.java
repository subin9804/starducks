package org.kosta.starducks.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class EmpSearchCond {
    private int page = 1;
    private int limit = 15;

    private String sopt;

    private String text;    // 키워드
    private String status = "running";
}
