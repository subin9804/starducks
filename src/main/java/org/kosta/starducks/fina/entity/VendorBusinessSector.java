package org.kosta.starducks.fina.entity;

import lombok.Getter;

@Getter
public enum VendorBusinessSector {
    COFFEEBEANSUPPLIERS("원두 공급업체"),
    ROASTINGCOMPANIES("로스팅 회사"),
    PACKAGINGANDDISTRIBUTIONCOMPANIES("포장 및 유통 업체"),
    MACHINERYANDEQUIPMENTSUPPLIERS("기계 및 장비 공급업체"),
    FARMINGANDCULTIVATION("재배 농장"),
    RELATEDSERVICEPROVIDERS("관련 서비스 제공업체");

    private final String description;

    VendorBusinessSector(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


/**
 * 원두 공급업체 (Coffee Bean Suppliers): 커피 원두를 공급하는 업체들로, 원두의 구매, 재고 관리, 품질 관리 등에 ERP 시스템이 필요합니다.
 *
 * 로스팅 회사 (Roasting Companies): 원두를 로스팅하는 업체들로, 생산 과정 관리, 재고 관리, 품질 관리 등에 ERP 시스템을 사용할 수 있습니다.
 *
 * 포장 및 유통 업체 (Packaging and Distribution Companies): 커피 제품의 포장 및 유통을 담당하는 업체들로, 로지스틱스, 재고 관리, 유통망 관리 등에 ERP가 필요합니다.
 *
 * 기계 및 장비 공급업체 (Machinery and Equipment Suppliers): 커피 제조 및 서비스에 사용되는 기계 및 장비를 공급하는 업체들로, 장비 관리, 고객 서비스 관리, 재고 관리 등에 ERP를 사용할 수 있습니다.
 *
 * 재배 농장 (Farming and Cultivation): 커피 재배 농장들로, 작물 관리, 생산 관리, 공급망 관리 등에 ERP 시스템을 도입할 수 있습니다.
 *
 * 관련 서비스 제공업체 (Related Service Providers): 마케팅, 광고, 법률 서비스, 컨설팅 등 커피 산업과 관련된 서비스를 제공하는 업체들도 ERP의 잠재적 사용자입니다.
 */