package org.kosta.starducks.generalAffairs.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.dto.VendorAndProductDTO;
import org.kosta.starducks.fina.entity.ContractStatus;
import org.kosta.starducks.fina.entity.VendorBusinessSector;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VendorService {
    /**
     * 총무부 - 품목 관리 영역
     */
    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;


    public List<String> getAllVendorNames() {
        List<String> vendorNames = vendorRepository.findAllVendorNames();
        return vendorNames;
    }

    public Vendor getVendorByName(String vendorName) {
        return vendorRepository.findVendorByVendorName(vendorName);

    }

    /**
     * 재무부 - 거래처 관리 영역
     * @param vendor
     */

    /**
     * 거래처 추가하기
     *
     * @param vendor
     */
    public void saveVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    /**
     * 거래처 단일 조회
     *
     * @param vendorId
     * @return
     */

    public Vendor findById(int vendorId) {
        return vendorRepository.findById(vendorId).orElse(null);
    }

    /**
     * 거래처 목록 조회
     *
     * @return
     */
    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }
//    public Page<Vendor> getAllVendors(Pageable pageable) {
//        return vendorRepository.findAll(pageable);
//    }
//
//    public Page<Vendor> vendorSearchList(String searchKeyword, Pageable pageable) {
//        return vendorRepository.findByVendorNameContaining(searchKeyword, pageable);
//    }

    /**
     * 거래처 수정
     *
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
        if (target != null) {
            vendorRepository.save(vendor);
        }
    }

    public void updateVendor(VendorAndProductDTO vendorAndProductDTO) {
//        1. DTO를 엔티티로 변환하기
        Vendor vendorEntity = modelMapper.map(vendorAndProductDTO, Vendor.class);
        log.info("vendorEntity.toString() ==> " + vendorEntity.toString());

//        2. 엔티티를 DB에 저장하기
//        2 - 1. DB에서 기존 데이터 가져오기
        Vendor target = vendorRepository.findById(vendorEntity.getVendorId()).orElse(null);
//        2 - 2. 기존 데이터 값을 갱신하기
        if (target != null) {
            vendorRepository.save(vendorEntity);
        }

    }

    /**
     * 거래처 삭제하기
     * @param vendorId
     * @param rttr
     */
//    public void deleteVendor(int vendorId, RedirectAttributes rttr) {
//        Vendor vendorTarget = vendorRepository.findById(vendorId).orElse(null);
//        if(vendorTarget != null) {
//            vendorRepository.delete(vendorTarget);
//            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
//        }
//    }
}