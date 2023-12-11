package org.kosta.starducks.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "DOC_FORM")
public class DocForm {
    @Id
    private String formCode;

    private String formName;
    private String formNameEn;
}
