package org.kosta.starducks.generalAffairs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.core.SpringVersion;

import java.util.Date;

@Entity
@Data
public class Vendor {

    @Id
    private int vendorId;

    private String vendorName;
    private String vendorRegistNum;
    private String vendorRepreName;
    private int vendorTelephone;
    private String vendorEmail;
    private Date vendorStartDate;
    private String vendorAddress;









}
