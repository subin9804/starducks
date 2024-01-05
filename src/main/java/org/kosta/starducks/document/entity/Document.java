package org.kosta.starducks.document.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.kosta.starducks.commons.notify.dto.NotifyMessage;
import org.kosta.starducks.commons.notify.entity.Notify;
import org.kosta.starducks.commons.notify.service.NotifyInfo;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DOCUMENT")
public class Document implements NotifyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

    private String docTitle;
    private String docContent;
    private LocalDateTime docDate;
    private LocalDateTime docUpdateDate;
    private LocalDateTime apvDeadline;

    //발주서 납품기한일
    private LocalDate orderDeadline;
    //수신처
    @ManyToOne
    @JoinColumn(name ="vendor_id")
    private Vendor vendor;


    @Enumerated(EnumType.STRING)
    private DocStatus docStatus;

    @ColumnDefault(value = "false")
    private boolean urgent;

    @ColumnDefault(value = "false")
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_code", nullable = false)
    private DocForm docForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_writer_id", nullable = false)
    private Employee docWriter;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<Approval> approvals = new ArrayList<>();

    private String refEmpIds;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 알림 받을 사람들
    @Override
    public List<Employee> getReceivers() {
        List<Employee> receivers = new ArrayList<>();

        if(docStatus == DocStatus.PENDING_DOC) {
            receivers.add(approvals.get(0).getApvEmp());
        } else if(docStatus == DocStatus.IN_PROGRESS) {


            System.out.println("값이 없을까? " + this.approvals.get(1).getApvEmp().getEmpName());
            receivers.add(approvals.get(1).getApvEmp());
        }

        return receivers;
    }

    // 이동할 url
    @Transactional
    @Override
    public String getGoUrl() {
        return "/document/receiveDoc/" + (docForm.getFormNameEn() != null ? docForm.getFormNameEn() : "실패") + "/" + docId;
    }

    @Override
    public String getMsg() {
        return docWriter.getEmpName() + "님으로부터 " + NotifyMessage.DOCUMENT_NEW_REQUEST.getMessage();
    }

    // 알림 type
    @Override
    public Notify.NotificationType getNotificationType() {
        return Notify.NotificationType.DOCUMENT;
    }


//    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
//    private List<RefEmployee> refEmployee = new ArrayList<>();
//
//    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
//    private List<AttachedFile> attachedFile = new ArrayList<>();
}
