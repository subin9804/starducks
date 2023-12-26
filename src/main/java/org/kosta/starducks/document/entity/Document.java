package org.kosta.starducks.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.kosta.starducks.commons.notify.Notify;
import org.kosta.starducks.commons.notify.NotifyInfo;
import org.kosta.starducks.document.repository.DocFormRepository;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.hr.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;

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
        for(Approval app : approvals) {
            receivers.add(app.getApvEmp());
        }

        return receivers;
    }

    @Override
    public String getGoUrl() {

        return "/document/receiveDoc/" + docForm.getFormNameEn() + "/" + docId;
    }

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
