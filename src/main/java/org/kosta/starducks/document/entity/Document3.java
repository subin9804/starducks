//package org.kosta.starducks.document.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.ColumnDefault;
//import org.kosta.starducks.hr.entity.Employee;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@Table(name = "DOCUMENT3")
//public class Document3 {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long docId;
//
//
//    private LocalDateTime docDate;
//    //private LocalDateTime docUpdateDate;
//    //베이스 타임 연결하기
//
//    //납품기한일
//    private LocalDateTime endDate;
//
//    @Enumerated(EnumType.STRING)
//    private DocStatus docStatus;
//
//    @ColumnDefault(value = "false")
//    private boolean urgent;
//
//    @ColumnDefault(value = "false")
//    private boolean deleted;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "form_code", nullable = false)
//    private DocForm docForm;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "emp_id", nullable = false)
//    private Employee doc_writer;
//
//    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
//    private List<Approval> approvals = new ArrayList<>();
//
//
//
////    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
////    private List<RefEmployee> refEmployee = new ArrayList<>();
////
////    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
////    private List<AttachedFile> attachedFile = new ArrayList<>();
//}
