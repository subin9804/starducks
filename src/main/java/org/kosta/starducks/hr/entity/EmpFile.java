package org.kosta.starducks.hr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data
@AllArgsConstructor @NoArgsConstructor
public class EmpFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 파일아이디

    private Long empId; // 해당 직원 아이디

    private String type; // 프로필사진(profile) or 도장사진(stamp)

    @Column(length = 150)
    private String fileName;

    @Column(length = 300)
    private String filePath;

    @Transient
    private String fileUrl;

}
