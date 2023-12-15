package org.kosta.starducks.generalAffairs.service;


import lombok.RequiredArgsConstructor;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
