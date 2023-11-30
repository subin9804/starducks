package org.kosta.starducks.logistic.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Branch{
    //지점정보 entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    private String branchRegistNum;
    private String branchAddress;
    private int branchTelephone;
    private boolean branchClosureStatus;



}
