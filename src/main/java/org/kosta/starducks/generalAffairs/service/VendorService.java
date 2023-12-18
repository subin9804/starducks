package org.kosta.starducks.generalAffairs.service;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.fina.entity.ContractStatus;
import org.kosta.starducks.fina.entity.VendorBusinessSector;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VendorService {
    /**
     * 총무부 - 품목 관리 영역
     */
    private final VendorRepository vendorRepository;

    public List<String> getAllVendorNames(){
        List<String> vendorNames = vendorRepository.findAllVendorNames();
    return vendorNames;}

    public Vendor getVendorByName(String vendorName) {
        return vendorRepository.findVendorByVendorName(vendorName);

    }

    /**
     * 재무부 - 거래처 관리 영역
     * @param vendor
     */

    /**
     * 거래처 추가하기
     * @param vendor
     */
    public void saveVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    /**
     * 거래처 단일 조회
     * @param vendorId
     * @return
     */

    public Vendor findById(int vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }

    /**
     * 거래처 목록 조회
     * @return
     */
    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    /**
     * 거래처 수정
     * @param vendorId
     * @return
     */
    public Vendor getVendorById(int vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }

    public List<VendorBusinessSector> getAllVendorBusinessSectors() {
        return Arrays.asList(VendorBusinessSector.values());
    }

    public List<ContractStatus> getAllContractStatus() {
        return Arrays.asList(ContractStatus.values());
    }

    public void updateVendor(Vendor vendor) {
        Vendor target = vendorRepository.findById(vendor.getVendorId()).orElse(null);
        if(target != null) {
            vendorRepository.save(vendor);
        }
    }

}
