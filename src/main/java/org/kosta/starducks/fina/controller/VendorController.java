package org.kosta.starducks.fina.controller;

import lombok.extern.slf4j.Slf4j;
import org.kosta.starducks.fina.dto.VendorAndProductDTO;
import org.kosta.starducks.fina.entity.VendorBusinessSector;
import org.kosta.starducks.generalAffairs.entity.Vendor;
import org.kosta.starducks.generalAffairs.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/fina/vendor")
public class VendorController {

    private final ModelMapper modelMapper;
    private final VendorService vendorService;

    @Autowired
    public VendorController(ModelMapper modelMapper, VendorService vendorService) {
        this.modelMapper = modelMapper;
        this.vendorService = vendorService;
    }

    /**
     * 거래처 추가
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String newVendorForm(Model model) {
        log.info("여기는 컨트롤러");
        VendorAndProductDTO vendorAndProductDTO = new VendorAndProductDTO();
        model.addAttribute("vendorAndProductDTO", vendorAndProductDTO);
        model.addAttribute("businessSectors", VendorBusinessSector.values());
        return "fina/vendorAdd";
    }

    /**
     * DTO를 엔티티로 변환
     */
    private Vendor VendorEntity(VendorAndProductDTO vendorAndProductDTO) {
        return modelMapper.map(vendorAndProductDTO, Vendor.class);
    }

    /**
     * 거래처 추가 폼 데이터 받기
     * @param vendorAndProductDTO
     * @return
     */
    @PostMapping("/create")
    public String newVendor(VendorAndProductDTO vendorAndProductDTO) {
        log.info("나오니??????????? vendorAndProductDTO.toString() ==> " + vendorAndProductDTO.toString());
        // 1. DTO를 엔티티로 변환
        Vendor vendor = VendorEntity(vendorAndProductDTO);
        // 2. 리파지터리로 엔티티를 DB에 저장
        vendorService.saveVendor(vendor);
        return "redirect:/fina/vendor/list";
    }

    /**
     * 거래처 목록 조회
     * @return
     */
    @GetMapping()
    public String vendorList() {
        return "vendorList";
    }

}
