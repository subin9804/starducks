package org.kosta.starducks.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kosta.starducks.hr.entity.Employee;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "APPROVAL")
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long apvId;

    private int apvStep;
    private String apvComment;
    private LocalDateTime apvDate;

    @Enumerated(EnumType.STRING)
    private ApvStatus apvStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apv_emp_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = false)
    private Document document;
}