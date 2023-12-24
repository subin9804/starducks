package org.kosta.starducks.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ATTACHED_FILE")
@ToString(exclude = "document") // Document 참조 제외
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String fileName;
    private String filePath;

//    경로에 확장자도 같이 저장돼서 확장자 저장 따로 불필요
//    private String fileExtension;
//    private String amdFileName;
//    private String fileStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = false)
    private Document document;
}
