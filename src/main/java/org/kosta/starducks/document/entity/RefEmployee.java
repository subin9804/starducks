package org.kosta.starducks.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kosta.starducks.hr.entity.Employee;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "REF_EMPLOYEE")
public class RefEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refEmpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = false)
    private Document document;
}
