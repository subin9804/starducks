package org.kosta.starducks.generalAffairs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Vendor {

    @Id
    @GeneratedValue
    private int vendorId;

    private String vendorName;
    private String vendorRegistNum;
    private String vendorRepreName;
    private String vendorTelephone;
    private LocalDate vendorStartDate;
    private String vendorAddress;









}
